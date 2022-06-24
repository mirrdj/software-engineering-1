package server.game;

import server.map.MapClass;
import server.player.Player;
import server.player.PlayerInformation;
import server.player.PlayerManager;
import server.uniqueID.GameID;
import server.exceptions.GameAlreadyExistsException;
import server.map.MapManager;
import server.uniqueID.PlayerID;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameManager {
    private Map<String, GameClass> games = new HashMap<>();

    public Map<String, GameClass> getGames() {
        return games;
    }

    public Optional<GameClass> getGameWithID(String gameID){
        return Optional.of(games.get(gameID));
    }

    public void addGame(GameID gameID){
        //TODO check why according to IntelliJ this is always false
        boolean gameExists = games
                .keySet()
                .stream()
                .anyMatch(eachGameId -> eachGameId.equals(gameID));

        if(gameExists)
            throw new GameAlreadyExistsException("This game id is already registered");

        GameClass game = new GameClass(gameID);
        games.put(gameID.getID(), game);
    };

    public void addPlayerToGame(PlayerID playerID, PlayerInformation playerInformation, GameID gameID) {
        GameClass gameWithID = games.get(gameID.getID());
        Player player = new Player(playerID, playerInformation);

        gameWithID.registerPlayer(player);
    }
    public void addHalfMapToGame(MapClass halfMap, String playerID, GameID gameID){
        GameClass gameWithID = games.get(gameID.getID());
        gameWithID.addHalfMap(playerID, halfMap);
    }
    public void playerLost(GameID gameID, String playerID){
        GameClass gameWithID = games.get(gameID.getID());
        Player playerWithID = gameWithID.getPlayerWithID(playerID);

    }
}
