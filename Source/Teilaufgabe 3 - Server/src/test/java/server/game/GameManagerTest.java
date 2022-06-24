package server.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import server.exceptions.NoSuchGameException;
import server.exceptions.NoSuchPlayerException;
import server.map.MapClass;
import server.uniqueID.GameID;
import server.exceptions.GameAlreadyExistsException;
import server.uniqueID.PlayerID;

class GameManagerTest {
    GameManager gameManager;
    @BeforeEach
    private void createManager(){
        gameManager = new GameManager();
    }
    @Test
    public void addGame_gameAlreadyExists_throwGameAlreadyExistsException() {
        GameID gameID = new GameID("id123");
        gameManager.addGame(gameID);
        Executable addGame = ()-> gameManager.addGame(gameID);

        Assertions.assertThrows(GameAlreadyExistsException.class, addGame);
    }
}