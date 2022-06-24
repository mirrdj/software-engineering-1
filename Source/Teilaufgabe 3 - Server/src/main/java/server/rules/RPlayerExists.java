package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.exceptions.NoSuchPlayerException;
import server.game.GameClass;
import server.game.GameManager;
import server.player.Player;
import java.util.Map;

public class RPlayerExists implements IRule {
    private GameManager manager;

    public RPlayerExists(GameManager manager) {
        this.manager = manager;
    }

    private void checkPlayerExists(UniqueGameIdentifier gameID, String playerID) {
        Map<String, GameClass> games = manager.getGames();
        GameClass gameWithID = games.get(gameID.getUniqueGameID());

        Map<String, Player> players = gameWithID.getPlayerManager().getPlayers(playerID);
        if(players.get(playerID) == null)
            throw new NoSuchPlayerException("Player with given ID does not exist");
    }

    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap map) {
        checkPlayerExists(gameID, map.getUniquePlayerID());
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
        checkPlayerExists(gameID, playerID.getUniquePlayerID());
    }
}
