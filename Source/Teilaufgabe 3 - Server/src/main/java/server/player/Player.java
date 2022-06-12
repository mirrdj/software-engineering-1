package server.player;

import server.uniqueID.PlayerID;

public class Player {
    private String playerID;
    public Player(PlayerID playerID) {
        this.playerID = playerID.getID();
    }
    public String getPlayerID() {
        return playerID;
    }
}
