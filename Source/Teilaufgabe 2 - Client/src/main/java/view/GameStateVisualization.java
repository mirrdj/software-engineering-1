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
        gameState.addPropertyChangeListener(this::propertyChange);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event){
        Object object = event.getSource();
        Object newValue = event.getNewValue();
        if(event.getPropertyName() == "players"){
            System.out.println("In property change players are " + newValue.toString());
        }
        else if(event.getPropertyName() == "fullMap"){
            System.out.println("In property change fullMap is " + newValue.toString());
        }
    }

    public void handleUserButtonPress() throws Exception{
        this.controller.updateGameState();
    }


}
