package server.game;

import server.map.MapClass;
import server.player.Player;
import server.player.PlayerInformation;
import server.uniqueID.GameID;
import server.exceptions.GameAlreadyExistsException;
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

    public void addPlayerToGame(GameID gameID, PlayerID playerID, PlayerInformation playerInformation) {
        GameClass gameWithID = games.get(gameID.getID());
        Player player = new Player(playerID, playerInformation);

        gameWithID.registerPlayer(player);
    }
    public void addHalfMapToGame(GameID gameID, String playerID, MapClass halfMap){
        GameClass gameWithID = games.get(gameID.getID());
        gameWithID.addHalfMap(playerID, halfMap);
    }
    public void playerLost(String gameID, String playerID){
        GameClass gameWithID = games.get(gameID);
        gameWithID.playerLost(playerID);
    }
}
