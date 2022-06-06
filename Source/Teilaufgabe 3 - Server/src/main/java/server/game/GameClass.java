package server.game;

import server.map.MapClass;
import server.map.MapManager;
import server.player.Player;
import server.player.PlayerManager;

import java.util.Set;

public class GameClass {
    private final GameID gameID;
    private int round;
    private Set<PlayerManager> players;
    private MapClass map;

    public GameClass(GameID gameID) {
        this.gameID = gameID;
    }
    public GameID getGameID() {
        return gameID;
    }
    public int getRound() {
        return round;
    }
    public void updateRound(){}
    public void registerPlayer(Player player){}
    public PlayerManager getPlayerWithID(String ID){return null;}
    public Set<PlayerManager> getPlayers(){return null;};
    public void setFullMap(MapClass map){}
}
