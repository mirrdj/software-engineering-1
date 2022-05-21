package controller;

import map.HalfMapValidator;
import data.GameStateClass;
import data.PlayerStateClass;
import data.EnumPlayerGameState;
import map.MapClass;
import map.MapGenerator;
import map.Position;
import network.NetworkConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import path.AIInterface;
import path.AISimpleClass;
import path.EnumMove;

import java.util.Queue;

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final GameStateClass gameState;
    private final NetworkConverter networkConverter;

    private AIInterface aiObject;
    private String ownPlayerID;

    boolean treasureFound = false;
    boolean treasureCollected = false;
    boolean fortFound = false;

    public Controller(NetworkConverter networkConverter, GameStateClass gameState) {
        this.networkConverter = networkConverter;
        this.gameState = gameState;
    }

    private void updatePlayerState() {
        GameStateClass temp = networkConverter.getGameState();

        if(temp.getMyPlayer() != null)
            this.gameState.setMyPlayer(temp.getMyPlayer());
    }

    private void updateMap() {
        GameStateClass temp = networkConverter.getGameState();

        if(temp.getMapClass() != null)
            this.gameState.setMapClass(temp.getMapClass());
    }

    private boolean mustWait() {
        updatePlayerState();

        PlayerStateClass ownPlayerState = gameState.getMyPlayer();
        logger.debug("In hasToWait(), the state is " + ownPlayerState.getPlayerGS());

        return ownPlayerState.getPlayerGS() == EnumPlayerGameState.MUST_WAIT;
    }

    public void registerPlayer() {
        logger.info("Registering the player");
        ownPlayerID = networkConverter.postPlayerRegistration();
        logger.debug("ownPlayerID is " + ownPlayerID);
    }

    private MapClass generateHalfMap(){
        logger.info("Generate half map");
        MapGenerator generator = new MapGenerator(5, 5);
        MapClass halfMap = generator.generateHalfMap();

        HalfMapValidator halfMapValidator = new HalfMapValidator();

        boolean validMap = halfMapValidator.mapIsValid(halfMap);
        while (!validMap) {
            logger.info("Half map invalid");
            logger.info("Generate new half map");
            halfMap = generator.generateHalfMap();
            logger.info("There is a new half map");
            validMap = halfMapValidator.mapIsValid(halfMap);
            logger.info("Generated new half map - now gotta check it");
        }
        logger.info("Half map is valid");

        return halfMap;
    }

    public boolean checkBothHalfMapsSent() {
        updateMap();
        MapClass fullMap = gameState.getMapClass();

        return (fullMap.getHeight() == 8 && fullMap.getWidth() == 8) ||
                (fullMap.getHeight() == 4 && fullMap.getWidth() == 16);
    }

    public void sendHalfMap() throws Exception{
        MapClass halfMap = generateHalfMap();

        boolean wait;
        do {
            wait = mustWait();
            logger.debug("wait for my turn");
            Thread.sleep(400);
        } while (wait);

        logger.info("My turn to send the half map");
        networkConverter.postHalfMap(halfMap);
        logger.info("Half map sent");
    }


    public void setUpAI() {
        aiObject = new AISimpleClass(gameState.getMapClass());
    }

    private boolean treasureCollected() {
        updatePlayerState();

        PlayerStateClass ownPlayerState = gameState.getMyPlayer();
        if(ownPlayerState.hasCollectedTreasure()) {
            logger.debug("Treasure found");
            return true;
        }

        return false;
    }

    public void performAction() throws InterruptedException {
        Position myPosition = gameState.getMapClass().getMyPosition();
        Queue<EnumMove> moves = aiObject.generateAction(
                treasureFound,
                treasureCollected,
                fortFound,
                myPosition);

        while (!moves.isEmpty()){
            if(treasureCollected)
                logger.debug("Treasure already collected");

            boolean wait;
            do {
                wait = mustWait();
                logger.debug("wait for my turn");
                Thread.sleep(400);
            } while (wait);

            if(gameEnded())
                break;

            networkConverter.postMove(moves.poll());
            updateMap();
            updatePlayerState();

            // If treasure just collected, do not continue to target if not already reached
            if(!treasureCollected && treasureCollected()){
               treasureCollected = treasureCollected();
               break;
            }
        }
   }

    public boolean gameEnded() {
        PlayerStateClass ownPlayerState = gameState.getMyPlayer();

        if(ownPlayerState.hasWon()){
            logger.info("Player won");
            updateMap();
            return true;
        }

        if(ownPlayerState.hasLost()){
            logger.info("Player lost");
            updateMap();
            return true;
        }

        return false;
    }



}
