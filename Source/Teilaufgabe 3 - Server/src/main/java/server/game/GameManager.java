package server.game;

import server.exceptions.NoSuchGameException;
import server.uniqueID.GameID;
import server.exceptions.GameAlreadyExistsException;
import server.map.MapManager;
import server.uniqueID.PlayerID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameManager {
    private Map<GameID, MapManager> gameManagers;
    private Set<GameClass> games;

    public GameManager(){
        games = new HashSet<>();
        gameManagers = new HashMap<>();
    }

    public void addGame(GameID gameID){
        boolean gameExists = games
                .stream()
                .anyMatch(g -> g.getGameID().equals(gameID));

        if(gameExists)
            throw new GameAlreadyExistsException("This game id is already registered");

        GameClass game = new GameClass(gameID);
        games.add(game);
    };

    private GameClass getGameWithID(GameID gameID){
        GameClass gameWithID = games
                .stream()
                .filter(g -> g.getGameID().equals(gameID))
                .findFirst()
                .orElse(null);

        if(gameWithID == null)
            throw new NoSuchGameException("Game with given ID does not exist");

        return gameWithID;
    }
    public void addPlayerToGame(PlayerID playerID, GameID gameID){
        GameClass gameWithID = getGameWithID(gameID);
    }
    // getGameWithID
    // addHalfMapToGame
    // applyMoveToGame
    // setFullMaps
}
