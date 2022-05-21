package network;

import MessagesBase.MessagesFromClient.EMove;
import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;
import MessagesBase.MessagesFromServer.*;
import data.EnumPlayerGameState;
import data.GameStateClass;
import data.PlayerStateClass;
import map.*;
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
        return switch (positionState) {
            case BothPlayerPosition -> EnumPlayerPositionState.BOTH_PLAYER_POSITION;
            case EnemyPlayerPosition -> EnumPlayerPositionState.ENEMY_PLAYER_POSITION;
            case MyPlayerPosition -> EnumPlayerPositionState.MY_POSITION;
            case NoPlayerPresent -> EnumPlayerPositionState.NO_PLAYER_PRESENT;
        };

    }


    // convert from network ETreasureState to my own EnumTreasureState
    private EnumTreasureState convertETreasureState(ETreasureState treasureState) {
        return switch (treasureState) {
            case MyTreasureIsPresent -> EnumTreasureState.MY_TREASURE_IS_PRESENT;
            case NoOrUnknownTreasureState -> EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE;
        };

    }


    // convert from my own EnumTerrain to the network ETerrain
    private ETerrain convertEnumTerrain(EnumTerrain terrain) {
        return switch (terrain) {
            case WATER -> ETerrain.Water;
            case GRASS -> ETerrain.Grass;
            case MOUNTAIN -> ETerrain.Mountain;
        };
    }

    // convert from the network ETerrain to my own EnumTerrain
    private EnumTerrain convertETerrain(ETerrain terrain) {
        return switch (terrain) {
            case Water -> EnumTerrain.WATER;
            case Grass -> EnumTerrain.GRASS;
            case Mountain -> EnumTerrain.MOUNTAIN;
        };

    }

    // convert from network EFortState to own EnumFortState
    private EnumFortState convertEFortState(EFortState fortState) {
        return switch (fortState) {
            case EnemyFortPresent -> EnumFortState.ENEMY_FORT_PRESENT;
            case MyFortPresent -> EnumFortState.MY_FORT_PRESENT;
            case NoOrUnknownFortState -> EnumFortState.NO_OR_UNKNOWN_FORT_STATE;
        };

    }

    // convert from the network EPlayerGameState to my own EnumPlayerGameState
    private EnumPlayerGameState convertEPlayerGameState(EPlayerGameState playerGameState) {
        return switch (playerGameState) {
            case Lost -> EnumPlayerGameState.LOST;
            case Won -> EnumPlayerGameState.WON;
            case MustAct -> EnumPlayerGameState.MUST_ACT;
            case MustWait -> EnumPlayerGameState.MUST_WAIT;
        };
    }

    // convert from my own EnumMove to the network EMove
    private EMove covertEnumMove(EnumMove move) {
        return switch (move) {
            case DOWN -> EMove.Down;
            case LEFT -> EMove.Left;
            case RIGHT -> EMove.Right;
            case UP -> EMove.Up;
        };
    }

    // convert from my own MapNodeClass to network HalfMapNode
    private HalfMapNode convertMapNodeClass(MapNodeClass mapNodeClass) {
        int x = mapNodeClass.getX();
        int y = mapNodeClass.getY();
        boolean fortPresent = mapNodeClass.isFortPresent();
        ETerrain terrain = convertEnumTerrain(mapNodeClass.getTerrain());

        assert terrain != null;
        return new HalfMapNode(x, y, fortPresent, terrain);
    }

    // convert from my own MapClass to network HalfMap
    private HalfMap convertMapClass(MapClass halfMapClass, String uniquePlayerID) {
        logger.debug("unique player id is " + uniquePlayerID);
        List<MapNodeClass> classNodes = new ArrayList<>(halfMapClass.getNodes());
        List<HalfMapNode> networkNodes = new ArrayList<>();

        for(MapNodeClass hmNode : classNodes){
            networkNodes.add(convertMapNodeClass(hmNode));
        }

        return new HalfMap(uniquePlayerID, networkNodes);
    }


    // convert from the network FullMapNode class to my own MapNodeClass
    private MapNodeClass convertFullMapNode(FullMapNode fullMapNode) {
        int x = fullMapNode.getX();
        int y = fullMapNode.getY();

        EnumFortState fort = convertEFortState(fullMapNode.getFortState());
        EnumTerrain terrain = convertETerrain(fullMapNode.getTerrain());
        EnumPlayerPositionState playerPos = convertEPlayerPositionState(fullMapNode.getPlayerPositionState());
        EnumTreasureState treasure = convertETreasureState(fullMapNode.getTreasureState());

        return new MapNodeClass(x, y, terrain, fort, playerPos, treasure);
    }


    // convert the network FullMap class to my own MapClass
    private MapClass convertFullMap(FullMap fullMap) {
        List<FullMapNode> networkNodes = new ArrayList<>(fullMap.getMapNodes());
        List<MapNodeClass> classNodes = new ArrayList<>();

        for(FullMapNode fmNode : networkNodes)
            classNodes.add(convertFullMapNode(fmNode));

        return new MapClass(classNodes);
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
        Optional<FullMap> fullMap = gameState.getMap();

        Set<PlayerStateClass> players = new HashSet<>();
        for(PlayerState p : gameState.getPlayers()){
            PlayerStateClass playerStateClass = convertPlayerState(p);
            players.add(playerStateClass);
        }

        if(fullMap.isPresent()) {
            MapClass mapClass = convertFullMap(fullMap.get());
            return new GameStateClass(mapClass, players);
        }

        return new GameStateClass(players);
    }

    public String postPlayerRegistration() {
        logger.info("Registering the player");
        return networkMessenger.postPlayerRegistration();
    }

    public void postHalfMap(MapClass halfMapClass, String uniquePlayerID) {
        logger.info("Sending the map");
        HalfMap halfMap = convertMapClass(halfMapClass, uniquePlayerID);
        logger.info(halfMap.toString());
        networkMessenger.postHalfMap(halfMap);
    }

    public void postMove(EnumMove move) {
        EMove eMove = covertEnumMove(move);
        networkMessenger.postMove(eMove);
    }

    public GameStateClass getGameState()  {
        GameState gameState = networkMessenger.getGameState();
        return convertGameState(gameState);
    }

}
