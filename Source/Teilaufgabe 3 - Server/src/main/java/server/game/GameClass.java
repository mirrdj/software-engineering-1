package server.game;

import server.map.MapManager;
import server.move.EnumMove;
import server.uniqueID.GameID;
import server.map.MapClass;
import server.player.Player;
import server.player.PlayerManager;
import java.util.Optional;
import java.util.UUID;

public class GameClass {
    private final GameID gameID;
    private int round = 0;
    private final PlayerManager playerManager = new PlayerManager();
    private final MapManager mapManager = new MapManager();
    private String modificationID;

    public GameClass(GameID gameID) {
        this.gameID = gameID;
        modificationID = UUID.randomUUID().toString();
    }

    public Optional<MapClass> getFullMap(String requesterID) {
        int firstNRounds = 20;

        if(round < firstNRounds)
            return mapManager.getFullMap(requesterID, true);
        else
            return mapManager.getFullMap(requesterID, false);
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public void updateModificationID(){
        modificationID = UUID.randomUUID().toString();
    }
    public String getModificationID() {
        return modificationID;
    }

    public GameID getGameID() {
        return gameID;
    }
    public void updateRound(){ round += 1;}
    public void registerPlayer(Player player){
        playerManager.addPlayer(player);
        updateModificationID();
    }

    public void addHalfMap(String playerID, MapClass halfMap){
        mapManager.addHalfMap(playerID, halfMap);

        playerManager.updateTurn();
        updateModificationID();
        updateRound();
    }

    public void addMove(String playerID, EnumMove move){
        playerManager.updateTurn();
        updateModificationID();
        updateRound();
    }

    public void playerLost(String playerID){
        playerManager.playerLost(playerID);
        updateModificationID();
    }
}
