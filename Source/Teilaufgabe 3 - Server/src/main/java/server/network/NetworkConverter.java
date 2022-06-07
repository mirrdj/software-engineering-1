package server.network;

import MessagesBase.UniqueGameIdentifier;
import server.UniqueID.GameID;

public class NetworkConverter {
    // convert from my own GameID to UniqueGameIdentifier
    public UniqueGameIdentifier convertGameID(GameID gameID){
        String id = gameID.getID();
        UniqueGameIdentifier gameIdentifier = new UniqueGameIdentifier(id);

        return gameIdentifier;
    }
}
