package server.uniqueID;

import server.exceptions.IDHasWrongLengthException;

public class PlayerID extends AbstractUniqueID {
    public PlayerID(String playerID) {
        super(playerID);

        int neededLength = 12;
        if(playerID.length() != neededLength)
            throw new IDHasWrongLengthException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerID)) return false;
        PlayerID playerIDID1 = (PlayerID) o;
        return super.getID().equals(playerIDID1.getID());
    }
}
