package controller;

import MessagesBase.MessagesFromServer.FullMap;
import data.GameStateClass;
import data.PlayerStateClass;
import helper.MapCreator;
import map.MapClass;
import network.NetworkConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    private NetworkConverter networkConverter;
    private GameStateClass gameState;

    @BeforeEach
    void setUp(){
        gameState = Mockito.mock(GameStateClass.class);
        networkConverter = Mockito.mock(NetworkConverter.class);
        controller = new Controller(networkConverter, gameState);
    }

    @Test
    void registerPlayer() {
    }

    @Test
    void checkBothHalfMapsSent_fullMap_returnTrue() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
        };

        MapCreator mapCreator = new MapCreator();
        MapClass map = mapCreator.createMapHalfMapStyle(nodes);

        Mockito.when(gameState.getMapClass()).thenReturn(map);
        Assertions.assertTrue(controller.checkBothHalfMapsSent());
    }

    @Test
    void checkBothHalfMapsSent_fullMap_returnFalse() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
        };

        MapCreator mapCreator = new MapCreator();
        MapClass map = mapCreator.createMapHalfMapStyle(nodes);

        Mockito.when(gameState.getMapClass()).thenReturn(map);
        Assertions.assertTrue(controller.checkBothHalfMapsSent());
    }

    @Test
    void sendHalfMap() {
    }

    @Test
    void setUpAI() {
    }

    @Test
    void performAction() {

    }

    @Test
    void gameEnded() {
        Mockito.when(networkConverter
                .getGameState()
                .getPlayerWithID("uniquePlayerID")
                .hasWon())
                .thenReturn(true);

        Assertions.assertTrue(controller.gameEnded());
    }
}