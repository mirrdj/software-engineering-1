package server.rules;

import server.exceptions.NoSuchGameException;
import server.game.GameManager;
import server.map.MapClass;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;

public class RGameExists implements IRule {
    private GameManager manager;
    public RGameExists(GameManager manager){
        this.manager = manager;
    }

    private void checkGameExists(GameID gameID){
        if(manager.getGames().get(gameID.getID()) == null)
            throw new NoSuchGameException("Game with given ID does not exist");
    }

    @Override
    public void validateRegisterPlayer(GameID gameID) {
        checkGameExists(gameID);
    }

    @Override
    public void validateHalfMap(GameID gameID, MapClass map) {
        checkGameExists(gameID);
    }

    @Override
    public void validateGetState(GameID gameID, PlayerID playerID) {
        checkGameExists(gameID);
    }
}
