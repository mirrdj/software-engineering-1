package server.uniqueID;
import server.exceptions.WrongIDFormatException;


public class GameID extends AbstractUniqueID {
    public GameID(String gameID) {
        super(gameID);

        int neededLength = 5;
        if(gameID.length() != neededLength)
            throw new WrongIDFormatException("ID has wrong length");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameID)) return false;
        GameID gameID1 = (GameID) o;
        return super.getID().equals(gameID1.getID());
    }
}
