package server.rules;

import MessagesBase.MessagesFromClient.PlayerRegistration;
import server.map.MapClass;
import server.player.Player;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;

public interface IRule {
    public void validateRegisterPlayer(GameID gameID);
    public void validateHalfMap(GameID gameID, MapClass map, String playerID);
    public void validateGetState(GameID gameID, PlayerID playerID);
}
