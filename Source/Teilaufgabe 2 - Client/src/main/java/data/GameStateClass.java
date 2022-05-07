package data;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import map.MapClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

public class GameStateClass {
    private static final Logger logger = LoggerFactory.getLogger(GameStateClass.class);
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private String gameStateID;
    private MapClass fullMap;
    private Set<PlayerStateClass> players;
    private EnumPlayerGameState enumPlayerGameState;

    public GameStateClass() {}

    public GameStateClass(String gameStateID) {
        this.gameStateID = gameStateID;
    }

    public GameStateClass(String gameStateID, MapClass fullMap, Set<PlayerStateClass> players) {
        this.gameStateID = gameStateID;
        this.fullMap = fullMap;
        this.players = players;
    }

    public GameStateClass(String gameStateID, Set<PlayerStateClass> players) {
        this.gameStateID = gameStateID;
        this.players = players;
    }

    public MapClass getFullMap() {
        return fullMap;
    }
    public Set<PlayerStateClass> getPlayers() {
        return players;
    }

    public PlayerStateClass getPlayerWithID(String uniqueID) {
        Optional<PlayerStateClass> player = players
                .stream()
                .filter(ps -> ps.equals(new PlayerStateClass(uniqueID)))
                .findFirst();

        if(player.isEmpty()) {
            logger.error("No player found");
            return null;
        }
        else
            return player.get();
    }

    public boolean bothPlayersRegistered(){
        return players.size() == 2;
    }

    public void setFullMap(MapClass fullMap) {
        MapClass mapBeforeChange = this.fullMap;
        this.fullMap = fullMap;

        changes.firePropertyChange("fullMap", mapBeforeChange, fullMap);
    }

    public void setPlayers(Set<PlayerStateClass> players) {
        Set<PlayerStateClass> playersBeforeChange = this.players;
        this.players = players;

        changes.firePropertyChange("players", playersBeforeChange, players);
    }

    @Override
    public String toString() {
        String string = "";
        string += fullMap.toString();
        string += '\n';
        string += players.toString();
        string += '\n';
        string += "Game status: ";

        if(enumPlayerGameState.equals(EnumPlayerGameState.WON))
            string += "won";
        else if (enumPlayerGameState.equals(EnumPlayerGameState.LOST))
            string += "lost";

        return string;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }
}
