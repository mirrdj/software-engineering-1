package server.game;

import server.map.MapClass;
import server.move.EnumMove;
import server.player.Player;
import server.player.PlayerInformation;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class GameManager {
    private final Map<String, GameClass> games = new LinkedHashMap<>();

    public Map<String, GameClass> getGames() {
        return games;
    }

    public Optional<GameClass> getGameWithID(String gameID){
        return Optional.of(games.get(gameID));
    }

    public void addGame(GameID gameID){
        if(games.size() == 999) {
            String firstGameID = games.keySet().iterator().next();
            games.remove(firstGameID);
        }

        GameClass game = new GameClass(gameID);
        games.put(gameID.getID(), game);
    }

    public void addPlayerToGame(GameID gameID, PlayerID playerID, PlayerInformation playerInformation) {
        GameClass gameWithID = games.get(gameID.getID());
        Player player = new Player(playerID, playerInformation);

        gameWithID.registerPlayer(player);
    }
    public void addHalfMapToGame(GameID gameID, String playerID, MapClass halfMap){
        GameClass gameWithID = games.get(gameID.getID());
        gameWithID.addHalfMap(playerID, halfMap);
    }

    public void addMoveToGame(GameID gameID, String playerID, EnumMove move){
        GameClass gameWithID = games.get(gameID.getID());
        gameWithID.addMove(playerID, move);
    }

    public void playerLost(String gameID, String playerID){
        GameClass gameWithID = games.get(gameID);
        gameWithID.playerLost(playerID);
    }
}
