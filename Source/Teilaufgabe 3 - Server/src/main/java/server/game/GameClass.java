package server.game;

import server.map.MapManager;
import server.uniqueID.GameID;
import server.map.MapClass;
import server.player.Player;
import server.player.PlayerManager;
import java.util.Optional;

public class GameClass {
    private final GameID gameID;
    private int round;
    private PlayerManager playerManager;
    private MapManager mapManager;
    private Optional<MapClass> fullMap = Optional.empty();

    public GameClass(GameID gameID) {
        this.gameID = gameID;
        this.playerManager = new PlayerManager();
        this.mapManager = new MapManager();
    }

    //TODO check all these optionals
    public Optional<MapClass> getFullMap() {
        return fullMap;
    }

    public void setFullMap(Optional<MapClass> fullMap) {
        this.fullMap = fullMap;
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

        fullMap = mapManager.getFullMap();

        playerManager.updateTurn();
    }

    public void playerLost(String playerID){
        playerManager.playerLost(playerID);
    }
}
