package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerMove;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;


public interface IRule {
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration);
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap);
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID);
    public void validateMove(UniqueGameIdentifier gameID, PlayerMove playerMove);
}
