package server.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void addHalfMapToGame_gameDoesNotExist_throwsNoSuchGameException() {
        GameID gameID1 = Mockito.mock(GameID.class);
        GameID gameID2 = Mockito.mock(GameID.class);
        PlayerID playerID = Mockito.mock(PlayerID.class);
        MapClass halfMap = Mockito.mock(MapClass.class);

        gameManager.addGame(gameID1);
        gameManager.addPlayerToGame(playerID, gameID1);

        Executable addHalfMapToGame = ()-> gameManager.addHalfMapToGame(halfMap, playerID, gameID2);
        Assertions.assertThrows(NoSuchGameException.class, addHalfMapToGame);
    }

    @Test
    void addHalfMapToGame_playerDoesNotExist_throwsNoSuchPlayerException() {
        GameID gameID = Mockito.mock(GameID.class);
        PlayerID playerID1 = new PlayerID("id0123456789");
        PlayerID playerID2 = new PlayerID("id1123456789");
        MapClass halfMap = Mockito.mock(MapClass.class);

        gameManager.addGame(gameID);
        gameManager.addPlayerToGame(playerID1, gameID);

        Executable addHalfMapToGame = ()-> gameManager.addHalfMapToGame(halfMap, playerID2, gameID);
        Assertions.assertThrows(NoSuchPlayerException.class, addHalfMapToGame);
    }

    @Test
    void addHalfMapToGame_bothPlayerAndGameExist_noThrow() {
        GameID gameID = Mockito.mock(GameID.class);
        PlayerID playerID = new PlayerID("id0123456789");
        MapClass halfMap = Mockito.mock(MapClass.class);

        gameManager.addGame(gameID);
        gameManager.addPlayerToGame(playerID, gameID);

        Executable addHalfMapToGame = ()-> gameManager.addHalfMapToGame(halfMap, playerID, gameID);
        Assertions.assertDoesNotThrow(addHalfMapToGame);
    }
}