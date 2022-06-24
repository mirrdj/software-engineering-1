package server.game;

import server.exceptions.*;
import server.map.MapManager;
import server.uniqueID.GameID;
import server.map.MapClass;
import server.player.Player;
import server.player.PlayerManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GameClass {
    private final GameID gameID;
    private int round;
    private PlayerManager playerManager;
    private MapManager mapManager;
    private Optional<MapClass> fullMap;

    public GameClass(GameID gameID) {
        this.gameID = gameID;
        this.playerManager = new PlayerManager();
        this.mapManager = new MapManager();
    }

    public Optional<MapClass> getFullMap() {
        return fullMap;
    }

    public void setFullMap(MapClass map){
        fullMap = Optional.of(map);
    }

    public MapManager getMapManager() {
        return mapManager;
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
    public void addHalfMap(String playerID, MapClass halfMap){
        mapManager.addHalfMap(playerID, halfMap);
        playerManager.updateTurn();
    }
}
