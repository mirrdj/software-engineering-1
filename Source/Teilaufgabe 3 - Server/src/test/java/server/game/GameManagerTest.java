package server.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import server.UniqueID.GameID;
import server.exceptions.GameAlreadyExistsException;

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
    // test that the new game has an ID different from the other games
    // test that only two players can be registered
}