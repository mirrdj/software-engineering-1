package server.game;

import server.exceptions.*;
import server.uniqueID.GameID;
import server.map.MapClass;
import server.player.Player;
import server.player.PlayerManager;

import java.util.HashSet;
import java.util.Set;

public class GameClass {
    private final GameID gameID;
    private int round;
    private Set<PlayerManager> players;
    private MapClass map = null;

    public GameClass(GameID gameID) {
        this.gameID = gameID;
        this.players = new HashSet<>();
    }
    public GameID getGameID() {
        return gameID;
    }
    public int getRound() {
        return round;
    }
    public void updateRound(){ round += 1;}
    public void registerPlayer(Player player){
        int maximumPlayerNumber = 2;

        if(players.size() == maximumPlayerNumber)
            throw new TwoPlayersAlreadyRegisteredExeception();

        PlayerManager playerManager = new PlayerManager(player.getPlayerID());
        players.add(playerManager);
    }
    //TODO: check this - add a test to see if it returns the right player
    //TODO: do I really need the player.isEmpty()?
    public PlayerManager getPlayerWithID(String ID){
        if(players.isEmpty())
            throw new NoPlayerRegisteredException();

        PlayerManager playerWithID = players
                .stream()
                .filter(p -> p.getPlayerID().equals(ID))
                .findFirst()
                .orElse(null);

        if(playerWithID == null)
            throw new NoSuchPlayerException("No such player registered");

        return playerWithID;
    }
    public Set<PlayerManager> getPlayers(){return players;}
    public void setFullMap(MapClass map){
        if(this.map != null)
            throw new MapAlreadySetException("Full map has already been set");

        this.map = map;
    }
}
