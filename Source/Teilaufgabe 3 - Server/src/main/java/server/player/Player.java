package server.player;

import server.uniqueID.PlayerID;

public class Player {
    String playerID;
    public Player(PlayerID playerID) {
        this.playerID = playerID.getID();
    }
    public String getPlayerID() {
        return playerID;
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}
