package server.player;

import server.exceptions.NoPlayerRegisteredException;
import server.exceptions.NoSuchPlayerException;
import server.exceptions.TwoPlayersAlreadyRegisteredExeception;

import java.util.HashSet;
import java.util.Set;

public class PlayerManager {
    private Set<Player> players = new HashSet<>();
    public void addPlayer(Player player){
        int maximumPlayerNumber = 2;
        if(players.size() == maximumPlayerNumber)
            throw new TwoPlayersAlreadyRegisteredExeception();

        players.add(player);
    }
    public Player getPlayerWithID(String playerID) {
        if(players.isEmpty())
            throw new NoPlayerRegisteredException();

        Player player = players
                .stream()
                .filter(p -> p.getPlayerID().equals(playerID))
                .findFirst()
                .orElse(null);

        if(player == null)
            throw new NoSuchPlayerException("No such player registered");

        return player;
    }
}
