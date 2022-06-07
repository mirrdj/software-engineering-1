package server.player;

public class PlayerManager {
    private String playerID;
    public PlayerManager(String playerID){
        this.playerID = playerID;
    }

    public String getPlayerID() {
        return playerID;
    }
}
