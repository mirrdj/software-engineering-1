package server.network;

import MessagesBase.UniqueGameIdentifier;
import server.game.GameID;

public class NetworkConverter {
    // convert from my own GameID to UniqueGameIdentifier
    public UniqueGameIdentifier convertGameID(GameID gameID){
        String id = gameID.getGameID();
        UniqueGameIdentifier gameIdentifier = new UniqueGameIdentifier(id);

        return gameIdentifier;
    }
}
