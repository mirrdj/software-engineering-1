package server.player;

import server.exceptions.NoPlayerRegisteredException;
import server.exceptions.NoSuchPlayerException;
import server.exceptions.TwoPlayersAlreadyRegisteredExeception;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerManager {
    private Map<String, Player> players = new HashMap<>();

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player){
        int maximumPlayerNumber = 2;
        if(players.size() == maximumPlayerNumber)
            throw new TwoPlayersAlreadyRegisteredExeception();

        players.put(player.getPlayerID(), player);
    }

    public Player getPlayerWithID(String playerID) {
        if(players.isEmpty())
            throw new NoPlayerRegisteredException();

        Player player = players.get(playerID);

        return player;
    }
}
