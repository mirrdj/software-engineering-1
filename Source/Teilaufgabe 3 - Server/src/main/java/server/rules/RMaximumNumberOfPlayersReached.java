package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerMove;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.exceptions.MaximumOfPlayersAlreadyRegisteredExeception;
import server.exceptions.NoSuchPlayerException;
import server.game.GameClass;
import server.game.GameManager;
import server.player.Player;

import java.util.Map;

public class RMaximumNumberOfPlayersReached implements IRule {
    private final GameManager manager;

    public RMaximumNumberOfPlayersReached(GameManager manager) {
        this.manager = manager;
    }

    private void checkPlayerNumber(UniqueGameIdentifier gameID) {
        int maximumAllowed = 2;

        Map<String, GameClass> games = manager.getGames();
        GameClass gameWithID = games.get(gameID.getUniqueGameID());

        int playersNumber = gameWithID.getPlayerManager().getPlayersNumber();
        if(playersNumber == maximumAllowed)
            throw new MaximumOfPlayersAlreadyRegisteredExeception();
    }

    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {
        checkPlayerNumber(gameID);
    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap) {

    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }

    @Override
    public void validateMove(UniqueGameIdentifier gameID, PlayerMove playerMove) {

    }
}
