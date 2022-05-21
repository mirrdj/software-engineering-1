package data;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import map.MapClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GameStateClass {
    private static final Logger logger = LoggerFactory.getLogger(GameStateClass.class);
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private PlayerStateClass myPlayer;
    private PlayerStateClass enemyPlayer;
    private MapClass mapClass;

    public GameStateClass() {}
    public GameStateClass(MapClass mapClass, PlayerStateClass myPlayer, PlayerStateClass enemyPlayer) {
        this.mapClass = mapClass;
        this.myPlayer = myPlayer;
        this.enemyPlayer = enemyPlayer;
    }

    public GameStateClass(PlayerStateClass myPlayer, PlayerStateClass enemyPlayer) {
        this.myPlayer = myPlayer;
        this.enemyPlayer = enemyPlayer;
    }

    public PlayerStateClass getMyPlayer() {
        return myPlayer;
    }

    public MapClass getMapClass() {
        return mapClass;
    }


    public void setMapClass(MapClass mapClass) {
        MapClass mapBeforeChange = this.mapClass;
        this.mapClass = mapClass;

        changes.firePropertyChange("map", mapBeforeChange, mapClass);
    }

    public void setMyPlayer(PlayerStateClass updatedPlayer) {
        PlayerStateClass playerBeforeChange = this.myPlayer;
        this.myPlayer = updatedPlayer;

        changes.firePropertyChange("players", playerBeforeChange, updatedPlayer);
    }

    public void addListener(PropertyChangeListener view) {
        changes.addPropertyChangeListener(view);
    }
}
