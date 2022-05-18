package controller;

import map.HalfMapValidator;
import path.AIClass;
import data.GameStateClass;
import data.PlayerStateClass;
import data.EnumPlayerGameState;
import map.MapClass;
import map.MapGenerator;
import network.NetworkConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import path.EnumMove;

import java.util.List;

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final GameStateClass gameState; // final so that the MVC reference aint lost
    private final NetworkConverter networkConverter;

    private final AIClass aiObject = new AIClass();
    private String ownPlayerID;

    public Controller(NetworkConverter networkConverter, GameStateClass gameState) {
        this.networkConverter = networkConverter;
        this.gameState = gameState;
    }


    public void registerPlayer() throws Exception{
        logger.info("Registering the player");
        ownPlayerID = networkConverter.postPlayerRegistration();
        logger.debug("ownPlayerID is " + ownPlayerID);
    }

    public void updateGameState() throws Exception{
        GameStateClass temp = networkConverter.getGameState(ownPlayerID);

        if(temp.getMapClass() != null)
            this.gameState.setMapClass(temp.getMapClass());
        if(temp.getPlayers() != null)
            this.gameState.setPlayers(temp.getPlayers());
    }

    public boolean mustAct() throws Exception {
        updateGameState();

        PlayerStateClass ownPlayerState = gameState.getPlayerWithID(ownPlayerID);
        logger.debug("In hasToWait(), the state is " + ownPlayerState.getPlayerGS());

        return ownPlayerState.getPlayerGS() == EnumPlayerGameState.MUST_ACT;
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
    public boolean bothPlayersRegistered() throws Exception{
        updateGameState();
        return gameState.bothPlayersRegistered();
    }

    public void sendHalfMap() throws Exception{
        MapClass halfMap = generateHalfMap();

        boolean act;
        do {
            act = mustAct();
            logger.debug("wait for my turn");
            Thread.sleep(400);
        } while (!act);

        logger.info("My turn to send the half map");
        networkConverter.postHalfMap(halfMap, ownPlayerID);
        logger.info("Half map sent");
    }

    public void setUpAI() {
        MapClass mapClass = gameState.getMapClass();
        aiObject.setUp(mapClass);
    }

    public boolean findTreasure() throws Exception {
        setUpAI();
        updateGameState();

        MapClass mapClass = gameState.getMapClass();
        List<EnumMove> moves = aiObject.findTreasure(mapClass);

        int index = 0;
        do {
            boolean act;
            do {
                act = mustAct();
				logger.debug("wait for my turn");
                Thread.sleep(400);
            } while (!act);

            if(gameEnded())
                return false;

            networkConverter.postMove(ownPlayerID, moves.get(index));
			index++;
			logger.debug(mapClass.toString());

            if(treasureCollected())
                return true;


        } while (index < moves.size());

        return false;
    }

    private boolean treasureCollected() throws Exception {
        updateGameState();

        PlayerStateClass ownPlayerState = gameState.getPlayerWithID(ownPlayerID);
        if(ownPlayerState.hasCollectedTreasure()) {
            logger.debug("Treasure found");
            return true;
        }

        return false;
    }

    public boolean checkBothHalfMapsSent() throws Exception {
        GameStateClass gameState = networkConverter.getGameState(ownPlayerID);
        MapClass fullMap = gameState.getMapClass();

        return fullMap.getNodes().size() == 64;
    }



    private void collectTreasure() throws Exception{
        MapClass mapClass = gameState.getMapClass();
        List<EnumMove> moves = aiObject.collectTreasure(mapClass);
        int index = 0;
        do {
            boolean act;
            do {
                act = mustAct();
                logger.debug("wait for my turn");
                Thread.sleep(400);
            } while (!act);

            if(gameEnded())
                return;

            networkConverter.postMove(ownPlayerID, moves.get(index));
            index++;

        } while (index < moves.size());
    }

    public boolean findEnemyFort() throws Exception {
        logger.info("Find enemy fort");
        updateGameState();
        MapClass mapClass = gameState.getMapClass();

        List<EnumMove> moves = aiObject.findFort(mapClass);
        int index = 0;
        do {
            boolean act;
            do {
                act = mustAct();
                logger.debug("wait for my turn");
                Thread.sleep(400);
            } while (!act);

            if(gameEnded())
                return true;

            networkConverter.postMove(ownPlayerID, moves.get(index));
            index++;

        } while (index < moves.size());

        return false;
    }


    public boolean playerLost() throws Exception {
        updateGameState();
        PlayerStateClass ownPlayerState = gameState.getPlayerWithID(ownPlayerID);

        return ownPlayerState.getPlayerGS() == EnumPlayerGameState.LOST;
    }

    public boolean playerWon() throws Exception {
        updateGameState();
        PlayerStateClass ownPlayerState = gameState.getPlayerWithID(ownPlayerID);

        return ownPlayerState.getPlayerGS() == EnumPlayerGameState.WON;
    }

    public boolean gameEnded() throws Exception {
        if(playerWon()){
            logger.info("Player won");
            return true;
        }

        if(playerLost()){
            logger.info("Player lost");
            return true;
        }

        return false;
    }



}
