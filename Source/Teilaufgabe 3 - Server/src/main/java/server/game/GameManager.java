package server.game;

import server.exceptions.NoSuchGameException;
import server.map.MapClass;
import server.player.Player;
import server.player.PlayerManager;
import server.uniqueID.GameID;
import server.exceptions.GameAlreadyExistsException;
import server.map.MapManager;
import server.uniqueID.PlayerID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameManager {
    private Map<GameID, MapManager> mapManagers;
    private Map<GameID, GameClass> games;

    public GameManager(){
        games = new HashMap<>();
        mapManagers = new HashMap<>();
    }

    public void addGame(GameID gameID){
        boolean gameExists = games
                .keySet()
                .stream()
                .anyMatch(g -> g.equals(gameID));

        if(gameExists)
            throw new GameAlreadyExistsException("This game id is already registered");

        GameClass game = new GameClass(gameID);
        games.put(gameID, game);
    };

    private GameClass getGameWithID(GameID gameID) {
        GameClass gameWithID = games
                .values()
                .stream()
                .filter(g -> g.getGameID().equals(gameID))
                .findFirst()
                .orElse(null);

        if(gameWithID == null)
            throw new NoSuchGameException("Game with given ID does not exist");

        return gameWithID;
    }
    public void addPlayerToGame(PlayerID playerID, GameID gameID) {
        GameClass gameWithID = getGameWithID(gameID);
        Player player = new Player(playerID);

        gameWithID.registerPlayer(player);
    }
    public void addHalfMapToGame(MapClass halfMap, String playerID, GameID gameID){
        GameClass gameWithID = getGameWithID(gameID);

        //TODO: change this so that I dont get the playManager just to check that the player exists
        Player playerWithID = gameWithID.getPlayerWithID(playerID);

        mapManagers.putIfAbsent(gameID, new MapManager());
        mapManagers.get(gameID).addHalfMap(playerID, halfMap);
    }
    // applyMoveToGame
    // setFullMaps
}
