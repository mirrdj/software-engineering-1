package view;

import controller.Controller;
import data.GameStateClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameStateVisualization implements PropertyChangeListener {
    private static final Logger logger = LoggerFactory.getLogger(GameStateClass.class);
    private Controller controller;

    public GameStateVisualization(GameStateClass gameState, Controller controller) {
        this.controller = controller;
        gameState.addListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event){
        Object newValue = event.getNewValue();

        switch (event.getPropertyName()){
            case "players":
                System.out.println(newValue.toString());
                break;
            case "map":
                System.out.println(newValue.toString());
                break;
        }
    }

}
