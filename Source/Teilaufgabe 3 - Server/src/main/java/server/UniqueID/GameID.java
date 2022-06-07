package server.UniqueID;

import server.exceptions.IDHasWrongLengthException;

import java.util.Objects;


public class GameID extends UniqueID{
    public GameID(String gameID) {
        super(gameID);

        int neededLength = 5;
        if(gameID.length() != neededLength)
            throw new IDHasWrongLengthException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameID)) return false;
        GameID gameID1 = (GameID) o;
        return super.getID().equals(gameID1.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getID());
    }
}
