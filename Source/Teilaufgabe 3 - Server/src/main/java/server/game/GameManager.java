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
    private Map<String, MapManager> mapManagers;
    private Map<String, GameClass> games;

    public GameManager(){
        games = new HashMap<>();
        mapManagers = new HashMap<>();
    }

    public Map<String, GameClass> getGames() {
        return games;
    }

    public void addGame(GameID gameID){
        boolean gameExists = games
                .keySet()
                .stream()
                .anyMatch(g -> g.equals(gameID));

        if(gameExists)
            throw new GameAlreadyExistsException("This game id is already registered");

        GameClass game = new GameClass(gameID);
        games.put(gameID.getID(), game);
    };

    public void addPlayerToGame(PlayerID playerID, GameID gameID) {
        GameClass gameWithID = games.get(gameID.getID());
        Player player = new Player(playerID);

        gameWithID.registerPlayer(player);
    }
    public void addHalfMapToGame(MapClass halfMap, String playerID, GameID gameID){
        GameClass gameWithID = games.get(gameID.getID());

        //TODO: change this so that I dont get the playManager just to check that the player exists
        Player playerWithID = gameWithID.getPlayerWithID(playerID);

        mapManagers.putIfAbsent(gameID.toString(), new MapManager());
        mapManagers.get(gameID.toString()).addHalfMap(playerID, halfMap);
    }
    public void playerLost(GameID gameID, String playerID){
        GameClass gameWithID = games.get(gameID.getID());
        Player playerWithID = gameWithID.getPlayerWithID(playerID);

    }
    // applyMoveToGame
    // setFullMaps
}
