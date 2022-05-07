package controller;

import map.half_map.HalfMapValidator;
import path.AIClass;
import data.GameStateClass;
import data.PlayerStateClass;
import map.EnumTerrain;
import map.Position;
import map.full_map.EnumLayout;
import data.EnumPlayerGameState;
import map.MapClass;
import map.half_map.MapGenerator;
import network.NetworkConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import path.DijkstraCalculator;
import path.EnumMove;
import path.MoveCalculator;
import path.TargetChooser;
import path.adjacency_list.AdjancecyList;
import path.adjacency_list.AdjancecyListGenerator;

import java.util.HashMap;
import java.util.List;

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private NetworkConverter networkConverter;
    private GameStateClass gameState;
    private AIClass aiObject = new AIClass();
    private String ownPlayerID;
    private MapClass fullMap; // TODO: remove this
    private PlayerStateClass ownPlayerState; // TODO: remove this
    private EnumGamePhase gamePhase;
    private EnumLayout mapLayout; // TODO: remove this

    public Controller(NetworkConverter networkConverter, GameStateClass gameState) {
        this.networkConverter = networkConverter;
        this.gameState = gameState;
    }

    public Controller(NetworkConverter networkConverter) {
        this.networkConverter = networkConverter;
    }
    public void registerPlayer() throws Exception{
        logger.info("Registering the player");
        ownPlayerID = networkConverter.postPlayerRegistration();
        logger.debug("ownPlayerID is " + ownPlayerID);
    }

    public void updateGameState() throws Exception{
        GameStateClass temp = networkConverter.getGameState(ownPlayerID);
        if(temp.getFullMap() != null)
            this.gameState.setFullMap(temp.getFullMap());
        if(temp.getPlayers() != null)
            this.gameState.setPlayers(temp.getPlayers());
    }

    public boolean bothPlayersRegistered() throws Exception{
        gameState = networkConverter.getGameState(ownPlayerID);
        return gameState.bothPlayersRegistered();
    }

    public boolean mustAct() throws Exception {
        gameState = networkConverter.getGameState(ownPlayerID);
        ownPlayerState = gameState.getPlayerWithID(ownPlayerID);

        logger.debug("In hasToWait(), the state is " + ownPlayerState.getState());

        return ownPlayerState.getState() == EnumPlayerGameState.MUST_ACT;
    }

//    public boolean hasToWait() throws Exception {
//        gameState = networkConverter.getGameState(ownPlayerID);
//        ownPlayerState = gameState.getPlayerWithID(ownPlayerID);
//
//        logger.debug("In hasToWait(), the state is " + ownPlayerState.getState());
//
//        return ownPlayerState.getState() == EnumPlayerGameState.MUST_WAIT;
//    }

    private MapClass generateHalfMap() throws Exception {
        MapGenerator generator = new MapGenerator(4, 3);
        return generator.generateHalfMap();
   }

    public void sendHalfMap() throws Exception{
        boolean act;
        logger.info("Generate half map");
        MapClass halfMap = generateHalfMap();
        HalfMapValidator halfMapValidator = new HalfMapValidator();
        while (!halfMapValidator.mapIsValid(halfMap)) {
            halfMap = generateHalfMap();
        }
        logger.info("Half map is valid");

        do {
            act = mustAct();
            logger.debug("wait for my turn");
            Thread.sleep(400);
        } while (!act);

        logger.info("My turn to send the half map");


        networkConverter.postHalfMap(halfMap, ownPlayerID);
        logger.info("Half map sent");
    }

    // I guess instead of checking this and if both players are registered
    // I can just check if it is this player's turn
    public boolean checkBothHalfMapsSent() throws Exception {
        gameState = networkConverter.getGameState(ownPlayerID);
        fullMap = gameState.getFullMap();

        return fullMap.getNodes().size() == 64;
    }

    public void displayInformation() {
        mapLayout = fullMap.getLayout();
        logger.debug("Height of the map " + fullMap.getHeight());
        logger.debug("Width of the map " + fullMap.getWidth());
        logger.debug("Map layout is " + mapLayout.toString());
        logger.debug("Game phase is " + gamePhase);
        logger.debug(fullMap.toString());
    }

    public void setUpAI() {
        gamePhase = EnumGamePhase.FIND_TREASURE;
        fullMap = gameState.getFullMap();
        mapLayout = fullMap.getLayout();

        HashMap<Position, EnumTerrain> terrain = fullMap.getTerrainNodes();
        Position initialPosition = fullMap.getMyPosition();
        List<Position> positionList = fullMap.getPositionList();

        // TODO: try to remove adjanceyList from Dijkstra
        // Set DijkstraCalculator for aiObject
        AdjancecyList adjancecyList = new AdjancecyListGenerator(fullMap).createAdjancecyList();
        DijkstraCalculator dijkstraCalculator = new DijkstraCalculator(adjancecyList, positionList);
        aiObject.setDijkstraCalculator(dijkstraCalculator);
        logger.info("DijkstraCalculator for aiObject set");

        // Set TargetChooser for aiObject
        TargetChooser targetChooser = new TargetChooser(terrain, initialPosition, mapLayout);
        targetChooser.setGamePhase(EnumGamePhase.FIND_TREASURE);
        aiObject.setTargetChooser(targetChooser);
        logger.info("TargetChooser for aiObject set");

        // Set MoveCalculator for aiObject
        MoveCalculator moveCalculator = new MoveCalculator(terrain);
        aiObject.setMoveCalculator(moveCalculator);
        logger.info("MoveCalculator for aiObject set");

    }

    public boolean findTreasure() throws Exception {
        gameState = networkConverter.getGameState(ownPlayerID);
        fullMap = gameState.getFullMap();
        List<EnumMove> moves = aiObject.findTreasure(fullMap);

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
			logger.debug(fullMap.toString());

            if(treasureCollected())
                return true;


        } while (index < moves.size());

        return false;
    }

    private boolean treasureCollected() throws Exception {
        gameState = networkConverter.getGameState(ownPlayerID);
        fullMap = gameState.getFullMap();
        gamePhase = EnumGamePhase.GO_TO_TREASURE;
        ownPlayerState = gameState.getPlayerWithID(ownPlayerID);

        if(ownPlayerState.hasCollectedTreasure()) {
            logger.debug("Treasure found");
            return true;
        }

        return false;
    }


    private void collectTreasure() throws Exception{
        List<EnumMove> moves = aiObject.collectTreasure(fullMap);
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
        gamePhase = EnumGamePhase.FIND_ENEMY_FORTRESS;
    }

    public boolean findEnemyFortress() throws Exception {
        gameState = networkConverter.getGameState(ownPlayerID);
        fullMap = gameState.getFullMap();
        List<EnumMove> moves = aiObject.findFortress(fullMap);

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
        gameState = networkConverter.getGameState(ownPlayerID);
        ownPlayerState = gameState.getPlayerWithID(ownPlayerID);

        return ownPlayerState.getState() == EnumPlayerGameState.LOST;
    }

    public boolean playerWon() throws Exception {
        gameState = networkConverter.getGameState(ownPlayerID);
        ownPlayerState = gameState.getPlayerWithID(ownPlayerID);

        return ownPlayerState.getState() == EnumPlayerGameState.WON;
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
//        return playerWon() || playerLost();
    }



}
