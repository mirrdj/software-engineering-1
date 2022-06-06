package server.game;

import server.exceptions.IDHasWrongLengthException;

import java.util.Objects;


public class GameID {
    private final String gameID;

    public GameID(String gameID) {
        int neededLength = 5;
        if(gameID.length() != neededLength)
            throw new IDHasWrongLengthException();

        this.gameID = gameID;
    }

    public String getGameID() {
        return gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameID)) return false;
        GameID gameID1 = (GameID) o;
        return gameID.equals(gameID1.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID);
    }
}
