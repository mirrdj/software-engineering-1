package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.exceptions.NoSuchGameException;
import server.game.GameManager;
import server.map.MapClass;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;

import java.util.Set;

public class RGameExists implements IRule {
    private GameManager manager;
    public RGameExists(GameManager manager){
        this.manager = manager;
    }

    private void checkGameExists(UniqueGameIdentifier gameID){
        Set<String> gameIDs = manager.getGames().keySet();
        if(!gameIDs.contains(gameID.getUniqueGameID()))
            throw new NoSuchGameException("Game with given ID does not exist");
    }

    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {
        checkGameExists(gameID);
    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap) {
        checkGameExists(gameID);
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
        checkGameExists(gameID);
    }
}
