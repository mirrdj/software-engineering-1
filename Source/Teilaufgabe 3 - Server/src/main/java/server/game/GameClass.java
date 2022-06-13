package server.game;

import server.exceptions.*;
import server.uniqueID.GameID;
import server.map.MapClass;
import server.player.Player;
import server.player.PlayerManager;

import java.util.HashSet;
import java.util.Set;

public class GameClass {
    private final GameID gameID;
    private int round;
    private PlayerManager playerManager;
    private MapClass map = null;

    public GameClass(GameID gameID) {
        this.gameID = gameID;
        this.playerManager = new PlayerManager();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GameID getGameID() {
        return gameID;
    }
    public int getRound() {
        return round;
    }
    public void updateRound(){ round += 1;}
    public void registerPlayer(Player player){
        playerManager.addPlayer(player);
    }
    public Player getPlayerWithID(String playerID){
        return playerManager.getPlayerWithID(playerID);
    }
    public void setFullMap(MapClass map){
        if(this.map != null)
            throw new MapAlreadySetException("Full map has already been set");

        this.map = map;
    }
}
