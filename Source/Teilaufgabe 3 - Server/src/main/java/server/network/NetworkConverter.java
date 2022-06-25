package server.network;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.MessagesFromServer.*;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.game.GameClass;
import server.map.*;
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

    // convert from  my own EnumFortState to network EFortState
    private EFortState convertEnumFortState(EnumFortState fortState) {
        switch (fortState){
            case ENEMY_FORT_PRESENT:
                return EFortState.EnemyFortPresent;
            case MY_FORT_PRESENT:
                return EFortState.MyFortPresent;
            case NO_OR_UNKNOWN_FORT_STATE:
                return EFortState.NoOrUnknownFortState;
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

    // convert from my own EnumPlayerPositionState to network EPlayerPositionState
    private EPlayerPositionState convertEnumPlayerPositionState(EnumPlayerPositionState positionState) {
        switch (positionState){
            case BOTH_PLAYER_POSITION:
                return EPlayerPositionState.BothPlayerPosition;
            case ENEMY_PLAYER_POSITION:
                return EPlayerPositionState.EnemyPlayerPosition;
            case MY_POSITION:
                return EPlayerPositionState.MyPlayerPosition;
            case NO_PLAYER_PRESENT:
                return EPlayerPositionState.NoPlayerPresent;
        }

        return null;
    }

    // convert from my own EnumTreasureState to network ETreasureState
    private ETreasureState convertEnumTreasureState(EnumTreasureState treasureState) {
        switch (treasureState){
            case MY_TREASURE_IS_PRESENT:
                return ETreasureState.MyTreasureIsPresent;
            case NO_OR_UNKNOWN_TREASURE_STATE:
                return ETreasureState.NoOrUnknownTreasureState;
        }

        return null;
    }


    // convert my own MapNodeClass to network mapNode
    private FullMapNode convertMapNodeClass(MapNodeClass mapNode){
        int x = mapNode.getX();
        int y = mapNode.getY();

        EFortState fort = convertEnumFortState(mapNode.getFort());
        ETerrain terrain = convertEnumTerrain(mapNode.getTerrain());
        EPlayerPositionState playerPos = convertEnumPlayerPositionState(mapNode.getPlayerPosition());
        ETreasureState treasure = convertEnumTreasureState(mapNode.getTreasure());

        return new FullMapNode(terrain, playerPos, treasure, fort, x, y);
    }

    // convert my own MapClass to the network FullMap
    private FullMap convertMapClass(MapClass map){
        List<MapNodeClass> mapNodes = new ArrayList<>(map.getNodes());
        List<FullMapNode> fullMapNodes = new ArrayList<>();

        for(MapNodeClass eachNode : mapNodes)
            fullMapNodes.add(convertMapNodeClass(eachNode));

        return new FullMap(fullMapNodes);
    }

    // create the network GameState with the information provided by my classes
    public GameState convertGameClass(GameClass game, String playerID) {
        Map<String, Player>  players = game
                .getPlayerManager()
                .getPlayers(playerID);

        Collection<PlayerState> playerStates = new ArrayList<>();
        for(Player eachPlayer : players.values()){
            PlayerState playerState = convertPlayer(eachPlayer);
            playerStates.add(playerState);
        }

        Optional<FullMap> fullMap = Optional.empty();
        if(game.getFullMap().isPresent()){
            FullMap convertedMap = convertMapClass(game.getFullMap().get());
            fullMap = Optional.of(convertedMap);
        }

        //TODO add own GameStateID not like this lol
        GameState gs = new GameState();
        return new GameState(fullMap, playerStates, gs.getGameStateId());
    }
}
