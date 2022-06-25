package server.game;

import server.map.MapManager;
import server.move.EnumMove;
import server.uniqueID.GameID;
import server.map.MapClass;
import server.player.Player;
import server.player.PlayerManager;
import java.util.Optional;

public class GameClass {
    private final GameID gameID;
    private int round = 0;
    private PlayerManager playerManager;
    private MapManager mapManager;

    public GameClass(GameID gameID) {
        this.gameID = gameID;
        this.playerManager = new PlayerManager();
        this.mapManager = new MapManager();
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
    public GameID getGameID() {
        return gameID;
    }
    public void updateRound(){ round += 1;}
    public void registerPlayer(Player player){
        playerManager.addPlayer(player);
    }

    public void addHalfMap(String playerID, MapClass halfMap){
        mapManager.addHalfMap(playerID, halfMap);

        playerManager.updateTurn();
        updateRound();
    }

    public void addMove(String playerID, EnumMove move){
        playerManager.updateTurn();
        updateRound();
    }

    public void playerLost(String playerID){
        playerManager.playerLost(playerID);
    }
}
