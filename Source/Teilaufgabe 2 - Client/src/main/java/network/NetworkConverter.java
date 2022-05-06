package network;

import MessagesBase.MessagesFromClient.EMove;
import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;
import MessagesBase.MessagesFromServer.*;
import data.*;
import map.*;
import map.full_map.*;
import map.half_map.HalfMapClass;
import map.half_map.HalfMapNodeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import path.EnumMove;

import java.util.*;

public class NetworkConverter {
    private static final Logger logger = LoggerFactory.getLogger(NetworkConverter.class);

    private final NetworkMessenger networkMessenger;


    public NetworkConverter(String serverBaseUrl, String gameId) {
        this.networkMessenger = new NetworkMessenger(serverBaseUrl, gameId);
    }


    // convert from network EPlayerPositionState to my own EnumPlayerPositionState
    private EnumPlayerPositionState convertEPlayerPositionState(EPlayerPositionState positionState) {
        switch (positionState){
            case BothPlayerPosition:
                return EnumPlayerPositionState.BOTH_PLAYER_POSITION;
            case EnemyPlayerPosition:
                return EnumPlayerPositionState.ENEMY_PLAYER_POSITION;
            case MyPlayerPosition:
                return EnumPlayerPositionState.MY_POSITION;
            case NoPlayerPresent:
                return EnumPlayerPositionState.NO_PLAYER_PRESENT;
        }

        return null;
    }


    // convert from network ETreasureState to my own EnumTreasureState
    private EnumTreasureState convertETreasureState(ETreasureState treasureState) {
        switch (treasureState){
            case MyTreasureIsPresent:
                return EnumTreasureState.MY_TREASURE_IS_PRESENT;
            case NoOrUnknownTreasureState:
                return EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE;
        }

        return null;
    }


    // convert from my own EnumTerrain to the network ETerrain
    private ETerrain convertEnumTerrain(EnumTerrain terrain) {
        switch (terrain){
            case WATER:
                return ETerrain.Water;
            case GRASS:
                return ETerrain.Grass;
            case MOUNTAIN:
                return ETerrain.Mountain;
        }
        return null;
    }

    // convert from the network ETerrain to my own EnumTerrain
    private EnumTerrain convertETerrain(ETerrain terrain) {
        switch (terrain){
            case Water:
                return EnumTerrain.WATER;
            case Grass:
                return EnumTerrain.GRASS;
            case Mountain:
                return EnumTerrain.MOUNTAIN;
        }

        return null;
    }

    // convert from network EFortState to own EnumFortState
    private EnumFortState convertEFortState(EFortState fortState) {
        switch (fortState){
            case EnemyFortPresent:
                return EnumFortState.ENEMY_FORT_PRESENT;
            case MyFortPresent:
                return EnumFortState.MY_FORT_PRESENT;
            case NoOrUnknownFortState:
                return EnumFortState.NO_OR_UNKNOWN_FORT_STATE;
        }

        return null;
    }

    // convert from the network EPlayerGameState to my own EnumPlayerGameState
    private EnumPlayerGameState convertEPlayerGameState(EPlayerGameState playerGameState) {
        switch(playerGameState) {
            case Lost:
                return EnumPlayerGameState.LOST;
            case Won:
                return EnumPlayerGameState.WON;
            case MustAct:
                return EnumPlayerGameState.MUST_ACT;
            case MustWait:
                return EnumPlayerGameState.MUST_WAIT;
        }
        return null;
    }

    // convert from my own EnumMove to the network EMove
    private EMove covertEnumMove(EnumMove move) {
        switch(move){
            case DOWN:
                return EMove.Down;
            case LEFT:
                return EMove.Left;
            case RIGHT:
                return EMove.Right;
            case UP:
                return EMove.Up;
        }
        return null;
    }

    // convert from my own HalfMapNodeClass to network HalfMapNode
    private HalfMapNode convertHalfMapNodeClass(HalfMapNodeClass halfMapNodeClass) {
        int x = halfMapNodeClass.getX();
        int y = halfMapNodeClass.getY();
        boolean fortPresent = halfMapNodeClass.isFortPresent();
        ETerrain terrain = convertEnumTerrain(halfMapNodeClass.getTerrain());

        assert terrain != null;
        return new HalfMapNode(x, y, fortPresent, terrain);
    }

    // convert from my own HalfMapClass to network HalfMap
    private HalfMap convertHalfMapClass(HalfMapClass halfMapClass, String uniquePlayerID) {
        logger.debug("unique player id is " + uniquePlayerID);
        List<HalfMapNodeClass> classNodes = new ArrayList<>(halfMapClass.getNodes());
        List<HalfMapNode> networkNodes = new ArrayList<>();

        for(HalfMapNodeClass hmNode : classNodes){
            networkNodes.add(convertHalfMapNodeClass(hmNode));
        }

        return new HalfMap(uniquePlayerID, networkNodes);
    }


    // convert from the network FullMapNode class to my own FullMapNodeClass
    private FullMapNodeClass convertFullMapNode(FullMapNode fullMapNode) {
        int x = fullMapNode.getX();
        int y = fullMapNode.getY();

        EnumFortState fort = convertEFortState(fullMapNode.getFortState());
        EnumTerrain terrain = convertETerrain(fullMapNode.getTerrain());
        EnumPlayerPositionState playerPos = convertEPlayerPositionState(fullMapNode.getPlayerPositionState());
        EnumTreasureState treasure = convertETreasureState(fullMapNode.getTreasureState());

        return new FullMapNodeClass(terrain, playerPos, treasure, fort, x, y);
    }


    // convert the network FullMap class to my own FullMapClass
    private FullMapClass convertFullMap(FullMap fullMap) {
        List<FullMapNode> networkNodes = new ArrayList<>(fullMap.getMapNodes());
        List<FullMapNodeClass> classNodes = new ArrayList<>();

        for(FullMapNode fmNode : networkNodes)
            classNodes.add(convertFullMapNode(fmNode));

        return new FullMapClass(classNodes);
    }

    // convert the network PlayerState class to my own PlayerStateClass
    private PlayerStateClass convertPlayerState(PlayerState playerState) {
        String uniquePlayerID = playerState.getUniquePlayerID();
        EnumPlayerGameState playerGameState = convertEPlayerGameState(playerState.getState());
        boolean treasureCollected = playerState.hasCollectedTreasure();

        return new PlayerStateClass(uniquePlayerID, playerGameState, treasureCollected);
    }

    // convert the network GameState class to my own GameStateClass
    private GameStateClass convertGameState(GameState gameState) {
        String gameStateID = gameState.getGameStateId();
        Optional<FullMap> fullMap = gameState.getMap();

        Set<PlayerStateClass> players = new HashSet<>();
        for(PlayerState p : gameState.getPlayers()){
            PlayerStateClass playerStateClass = convertPlayerState(p);
            players.add(playerStateClass);
        }

        if(fullMap.isPresent()) {
            FullMapClass fullMapClass = convertFullMap(fullMap.get());
            return new GameStateClass(gameStateID, fullMapClass, players);
        }

        return new GameStateClass(gameStateID, players);
    }

    public String postPlayerRegistration() throws Exception {
        logger.info("Registering the player");
        return networkMessenger.postPlayerRegistration();
    }

    public void postHalfMap(HalfMapClass halfMapClass, String uniquePlayerID) throws Exception {
        logger.info("Sending the map");
        HalfMap halfMap = convertHalfMapClass(halfMapClass, uniquePlayerID);
        logger.info(halfMap.toString());
        networkMessenger.postHalfMap(halfMap);
    }

    public void postMove(String uniquePlayerID, EnumMove move) throws Exception {
        EMove eMove = covertEnumMove(move);
        networkMessenger.postMove(uniquePlayerID, eMove);
    }

    public GameStateClass getGameState(String uniquePlayerID) throws Exception {
        GameState gameState = networkMessenger.getGameState(uniquePlayerID);
        return convertGameState(gameState);
    }

}
