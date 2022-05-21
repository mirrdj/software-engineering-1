package controller;

import data.GameStateClass;
import network.NetworkConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    private  GameStateClass gameState;
    private NetworkConverter networkConverter;

    @BeforeEach
    void setUpController(){
        networkConverter = Mockito.mock(NetworkConverter.class);
        gameState = new GameStateClass();
        controller = new Controller(networkConverter, gameState);
    }

    @Test
    void registerPlayer() {
    }

    @Test
    void updateGameState() {
    }

    @Test
    void mustAct() {
    }

    @Test
    void bothPlayersRegistered() {
    }

    @Test
    void sendHalfMap() {
    }

    @Test
    void setUpAI() {
    }

    @Test
    void findTreasure() {
    }

    @Test
    void checkBothHalfMapsSent() {
    }

    @Test
    void findEnemyFort() {
    }

    @Test
    void playerLost() {
    }

    @Test
    void playerWon() {
    }

    @Test
    void gameEnded() {
    }
}