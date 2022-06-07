package server.network;

import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;

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

    // convert from my own PlayerID to UniquePlayerIdentifier
    public UniquePlayerIdentifier convertPlayerID(PlayerID playerID){
        String id = playerID.getID();
        UniquePlayerIdentifier playerIdentifier = new UniquePlayerIdentifier(id);

        return playerIdentifier;
    }

    // convert from UniquePlayerIdentifier to my own PlayerID
    public PlayerID convertUniquePlayerIdentifier(UniquePlayerIdentifier playerIdentifier){
        String id = playerIdentifier.getUniquePlayerID();
        PlayerID playerID = new PlayerID(id);

        return playerID;
    }
}
