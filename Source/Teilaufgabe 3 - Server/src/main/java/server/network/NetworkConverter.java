package server.network;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.MessagesFromServer.EPlayerGameState;
import MessagesBase.MessagesFromServer.GameState;
import MessagesBase.MessagesFromServer.PlayerState;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.game.GameClass;
import server.map.MapClass;
import server.map.MapNodeClass;
import server.map.EnumTerrain;
import server.player.EnumPlayerGameState;
import server.player.Player;
import server.player.PlayerInformation;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;

import java.util.*;

public class NetworkConverter {
    // convert from my own GameID to UniqueGameIdentifier
    public UniqueGameIdentifier convertGameID(GameID gameID){
        String id = gameID.getID();
        return new UniqueGameIdentifier(id);
    }

    // convert from UniqueGameIdentifier to my own GameID
    public GameID convertUniqueGameIdentifier(UniqueGameIdentifier gameIdentifier){
        String id = gameIdentifier.getUniqueGameID();
        return new GameID(id);
    }

    // convert from my own PlayerID to UniquePlayerIdentifier
    public UniquePlayerIdentifier convertPlayerID(PlayerID playerID){
        String id = playerID.getID();
        return new UniquePlayerIdentifier(id);
    }

    // convert from UniqueGameIdentifier to my own GameID
    public PlayerID convertUniquePlayerIdentifier(UniquePlayerIdentifier playerIdentifier){
        String id = playerIdentifier.getUniquePlayerID();
        return new PlayerID(id);
    }

    // convert from the network ETerrain to my own EnumTerrain
    private EnumTerrain convertETerrain(ETerrain terrain) {
        return switch (terrain) {
            case Water -> EnumTerrain.WATER;
            case Grass -> EnumTerrain.GRASS;
            case Mountain -> EnumTerrain.MOUNTAIN;
        };

    }

    // convert from the network HalfMapNode class to my own MapNodeClass
    private MapNodeClass convertHalfMapNode(HalfMapNode halfMapNode) {
        int x = halfMapNode.getX();
        int y = halfMapNode.getY();

        boolean fort = halfMapNode.isFortPresent();
        EnumTerrain terrain = convertETerrain(halfMapNode.getTerrain());

        return new MapNodeClass(x, y, terrain, fort);
    }

    public MapClass convertHalfMap(HalfMap halfMap) {
        List<HalfMapNode> networkNodes = new ArrayList<>(halfMap.getMapNodes());
        List<MapNodeClass> classNodes = new ArrayList<>();

        for(HalfMapNode fmNode : networkNodes)
            classNodes.add(convertHalfMapNode(fmNode));

        return new MapClass(classNodes);
    }

    // convert from my own EnumPlayerGameState to the network EPlayerGameState
    private EPlayerGameState convertEnumPlayerGameState(EnumPlayerGameState playerGameState) {
        return switch (playerGameState) {
            case LOST -> EPlayerGameState.Lost;
            case WON -> EPlayerGameState.Won;
            case MUST_ACT -> EPlayerGameState.MustAct;
            case MUST_WAIT -> EPlayerGameState.MustWait;
        };
    }

    // convert the network PlayerRegistration to my PlayerInformation
    public PlayerInformation convertPlayerRegistration(PlayerRegistration playerRegistration){
        String firstName = playerRegistration.getStudentFirstName();
        String lastName = playerRegistration.getStudentLastName();
        String uaccount = playerRegistration.getStudentUAccount();

        return new PlayerInformation(firstName, lastName, uaccount);
    }

    // convert my Player to network PlayerState
    private PlayerState convertPlayer(Player player) {
        String firstName = player.getFirstName();
        String lastName = player.getLastName();
        String uaccount = player.getUaccount();
        UniquePlayerIdentifier playerID = new UniquePlayerIdentifier(player.getPlayerID());
        EPlayerGameState playerGameState = convertEnumPlayerGameState(player.getPlayerGameState());
        boolean treasureCollected = player.hasCollectedTreasure();

        return new PlayerState(firstName, lastName, uaccount, playerGameState, playerID, treasureCollected);
    }


    // create the network GameState with the information provided by my classes
    public GameState convertGameClass(GameClass game, String playerID) {
        Optional<MapClass> fullMap = game.getFullMap();

        Map<String, Player>  players = game
                .getPlayerManager()
                .getPlayers(playerID);

        Collection<PlayerState> playerStates = new ArrayList<>();
        for(Player eachPlayer : players.values()){
            PlayerState playerState = convertPlayer(eachPlayer);
            playerStates.add(playerState);
        }

        //
        // PlayerState convertedPlayer = convertPlayer(eachPlayer, treasureCollected);
        //playerStates.add();

        /*
        if(fullMap.isPresent()) {
            MapClass mapClass = convertFullMap(fullMap.get());
            return new GameStateClass(mapClass, myPlayer, enemyPlayer);
        }*/

        // GameState(Optional<FullMap> map, Collection<PlayerState> players, String gameStateID)
        //return new GameState(fullMap, players, String gameStateID);
        GameState gs = new GameState();
        return new GameState(playerStates, gs.getGameStateId());
    }
}
