package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerMove;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.exceptions.MapAlreadySetException;
import server.game.GameClass;
import server.game.GameManager;
import server.map.MapClass;

import java.util.Map;

public class RMapNotSet implements IRule {
    private final GameManager manager;

    public RMapNotSet(GameManager manager){
        this.manager = manager;
    }

    private void checkMapAlreadySet(UniqueGameIdentifier gameID, HalfMap halfMap){
        Map<String, GameClass> games = manager.getGames();
        GameClass gameWithID = games.get(gameID.getUniqueGameID());

        Map<String, MapClass> halfMaps = gameWithID.getMapManager().getMaps();
        if(halfMaps.containsKey(halfMap.getUniquePlayerID())){
            throw new MapAlreadySetException("This player has already sent a map");
        }
    }

    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap) {
        checkMapAlreadySet(gameID, halfMap);
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }

    @Override
    public void validateMove(UniqueGameIdentifier gameID, PlayerMove playerMove) {

    }
}
