package server.rules;

import server.exceptions.NoSuchGameException;
import server.exceptions.NoSuchPlayerException;
import server.game.GameClass;
import server.game.GameManager;
import server.map.MapClass;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;

public class RPlayerExists implements IRule {
    private GameManager manager;

    public RPlayerExists(GameManager manager) {
        this.manager = manager;
    }

    private void checkPlayerExists(GameID gameID, String playerID) {
        GameClass gameWithID = manager.getGames().get(gameID.getID());
        if(gameWithID.getPlayerManager().getPlayers().get(playerID) == null)
            throw new NoSuchPlayerException("Player with given ID does not exist");
    }

    @Override
    public void validateRegisterPlayer(GameID gameID) {

    }

    @Override
    public void validateHalfMap(GameID gameID, MapClass map, String playerID) {
        checkPlayerExists(gameID, playerID);
    }

    @Override
    public void validateGetState(GameID gameID, PlayerID playerID) {
        checkPlayerExists(gameID, playerID.getID());
    }

}
