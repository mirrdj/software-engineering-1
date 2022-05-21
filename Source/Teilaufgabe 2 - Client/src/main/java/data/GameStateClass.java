package data;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import map.MapClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GameStateClass {
    private static final Logger logger = LoggerFactory.getLogger(GameStateClass.class);
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private List<PlayerStateClass> players;
    private PlayerStateClass myPlayer;
    private PlayerStateClass enemyPlayer;
    private MapClass mapClass;

    public GameStateClass() {}
    public GameStateClass(MapClass mapClass, List<PlayerStateClass> players) {
        this.mapClass = mapClass;
        this.players = players;
    }

    public GameStateClass(List<PlayerStateClass> players) {
        this.players = players;
    }

    public MapClass getMapClass() {
        return mapClass;
    }
    public List<PlayerStateClass> getPlayers() {
        return players;
    }

    public PlayerStateClass getPlayerWithID(String uniqueID) {
        if(players.get(0).getUniquePlayerID().equals(uniqueID))
            return players.get(0);

        if(players.get(1).getUniquePlayerID().equals(uniqueID))
            return players.get(1);

        return null;
    }


    public void setMapClass(MapClass mapClass) {
        MapClass mapBeforeChange = this.mapClass;
        this.mapClass = mapClass;

        changes.firePropertyChange("map", mapBeforeChange, mapClass);
    }

    public void setPlayers(List<PlayerStateClass> updatedPlayers) {
        List<PlayerStateClass> playersBeforeChange = this.players;
        this.players = updatedPlayers;

        changes.firePropertyChange("players", playersBeforeChange, updatedPlayers);
    }

    public void addListener(PropertyChangeListener view) {
        changes.addPropertyChangeListener(view);
    }
}
