package server.network;

import MessagesBase.UniqueGameIdentifier;
import server.uniqueID.GameID;

public class NetworkConverter {
    // convert from my own GameID to UniqueGameIdentifier
    public UniqueGameIdentifier convertGameID(GameID gameID){
        String id = gameID.getID();
        UniqueGameIdentifier gameIdentifier = new UniqueGameIdentifier(id);

        return gameIdentifier;
    }

    // convert from UniqueGameIdentifier to my own GameID
    public GameID convertUniqueGameIdentifier(UniqueGameIdentifier gameIdentifier){
        String id = gameIdentifier.getUniqueGameID();
        GameID gameID = new GameID(id);

        return gameID;
    }
}
