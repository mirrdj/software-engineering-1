package server.game;

import server.map.MapManager;

import java.util.Map;
import java.util.Set;

public class GameManager {
    private Map<GameID, MapManager> gameManagers;
    private Set<GameClass> games;

    public void addGame(GameID gameID){};
    public GameClass getGameWithID(){
        return null;
    }
    // getGameWithID
    // addHalfMapToGame
    // applyMoveToGame
    // setFullMaps
}
