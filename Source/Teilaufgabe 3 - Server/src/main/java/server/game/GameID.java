package server.game;

import java.util.Objects;

public class GameID {
    private String gameID;

    public GameID(String gameID) {
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
        return false;
    }
}
