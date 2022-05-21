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
    private MapClass mapClass;
    private Set<PlayerStateClass> players;

    public GameStateClass() {}
    public GameStateClass(MapClass mapClass, Set<PlayerStateClass> players) {
        this.mapClass = mapClass;
        this.players = players;
    }

    public GameStateClass(Set<PlayerStateClass> players) {
        this.players = players;
    }

    public MapClass getMapClass() {
        return mapClass;
    }
    public Set<PlayerStateClass> getPlayers() {
        return players;
    }

    public PlayerStateClass getPlayerWithID(String uniqueID) {
        Optional<PlayerStateClass> player = players
                .stream()
                .filter(ps -> ps.equals(new PlayerStateClass(uniqueID)))
                .findFirst();

        return player.orElse(null);
    }

    public boolean bothPlayersRegistered(){
        return players.size() == 2;
    }

    public void setMapClass(MapClass mapClass) {
        MapClass mapBeforeChange = this.mapClass;
        this.mapClass = mapClass;

        changes.firePropertyChange("map", mapBeforeChange, mapClass);
    }

    public void setPlayers(Set<PlayerStateClass> updatedPlayers) {
        Set<PlayerStateClass> playersBeforeChange = this.players;
        this.players = updatedPlayers;

        changes.firePropertyChange("players", playersBeforeChange, updatedPlayers);
    }

    public void addListener(PropertyChangeListener view) {
        changes.addPropertyChangeListener(view);
    }
}
