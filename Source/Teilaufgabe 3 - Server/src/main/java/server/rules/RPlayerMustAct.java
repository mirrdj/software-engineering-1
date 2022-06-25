package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerMove;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.exceptions.NoSuchPlayerException;
import server.exceptions.PlayerMisbehavedException;
import server.game.GameClass;
import server.game.GameManager;
import server.player.EnumPlayerGameState;
import server.player.Player;

import java.util.Map;

public class RPlayerMustAct implements IRule {
    private final GameManager manager;

    private void checkPlayerMustAct(UniqueGameIdentifier gameID, String playerID){
        Map<String, GameClass> games = manager.getGames();
        GameClass gameWithID = games.get(gameID.getUniqueGameID());

        Map<String, Player> players = gameWithID.getPlayerManager().getPlayers(playerID);

        EnumPlayerGameState playerState;
        if(players.get(playerID) != null){
            playerState = players.get(playerID).getPlayerGameState();

            if(playerState != EnumPlayerGameState.MUST_ACT)
                throw new PlayerMisbehavedException("Player was not allowed to perform action but it did");
        }
        else
            throw new NoSuchPlayerException("Player with given ID does not exist");
    }

    public RPlayerMustAct(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap) {
        checkPlayerMustAct(gameID, halfMap.getUniquePlayerID());
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }

    @Override
    public void validateMove(UniqueGameIdentifier gameID, PlayerMove playerMove) {
        checkPlayerMustAct(gameID, playerMove.getUniquePlayerID());
    }
}
