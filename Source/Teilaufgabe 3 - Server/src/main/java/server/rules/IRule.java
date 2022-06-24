package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;


public interface IRule {
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration);
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap map);
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID);
}
