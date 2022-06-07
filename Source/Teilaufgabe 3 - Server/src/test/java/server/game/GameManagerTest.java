package server.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import server.exceptions.NoSuchGameException;
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
    @Test
    public void addPlayerToGame_gameDoesNotExist_throwsNoSuchGameException(){
        GameID gameID = new GameID("id123");
        PlayerID playerID = new PlayerID("id0123456789");

        Executable addPlayerToGame = ()-> gameManager.addPlayerToGame(playerID, gameID);
        Assertions.assertThrows(NoSuchGameException.class, addPlayerToGame);
    }
    @Test
    public void addPlayerToGame_gameExists_noException(){
        GameID gameID = new GameID("id123");
        gameManager.addGame(gameID);
        PlayerID playerID = new PlayerID("id0123456789");

        Executable addPlayerToGame = ()-> gameManager.addPlayerToGame(playerID, gameID);
        Assertions.assertDoesNotThrow(addPlayerToGame);
    }
}