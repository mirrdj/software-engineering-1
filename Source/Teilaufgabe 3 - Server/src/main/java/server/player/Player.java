package server.player;

public class Player {
    String playerID;

    public Player() {
    }

    public Player(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}