package server.game;

import server.exceptions.GameAlreadyExistsException;
import server.map.MapManager;

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
    public GameClass getGameWithID(){
        return null;
    }
    // getGameWithID
    // addHalfMapToGame
    // applyMoveToGame
    // setFullMaps
}
