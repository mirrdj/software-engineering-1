package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerMove;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;

public class RMapHasAnIsland implements IRule {
    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

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
