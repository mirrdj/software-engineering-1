package server.game;

import server.exceptions.IDHasWrongLengthException;


public class GameID {
    final int neededLength = 5;
    private String gameID;

    public GameID(String gameID) {
        if(gameID.length() != neededLength)
            throw new IDHasWrongLengthException();

        this.gameID = gameID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameID)) return false;
        GameID gameID1 = (GameID) o;
        return gameID.equals(gameID1.gameID);
    }
}
