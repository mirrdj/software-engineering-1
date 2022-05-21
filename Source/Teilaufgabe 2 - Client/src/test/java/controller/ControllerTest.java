package controller;

import data.EnumPlayerGameState;
import data.GameStateClass;
import data.PlayerStateClass;
import exceptions.WrongIdentificationDetailsException;
import helper.MapCreator;
import map.MapClass;
import network.NetworkConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class ControllerTest {
    private Controller controller;
    private NetworkConverter networkConverter;
    private GameStateClass gameState;

    @BeforeEach
    private void setUp(){
        gameState = Mockito.mock(GameStateClass.class);
        networkConverter = Mockito.mock(NetworkConverter.class);
        controller = new Controller(networkConverter, gameState);
    }

    @Test
    public void registerPlayer_invalidUaccount_throwWrongIdentificationDetails() {
        String firstName = "Miruna-Diana";
        String lastName = "Jarda";
        String uaccount = "      ";

        Mockito.when(networkConverter.postPlayerRegistration(firstName, lastName, uaccount))
                .thenReturn("uniqueID");

        Executable register = () -> controller.registerPlayer(firstName, lastName, uaccount);
        Assertions.assertThrows(WrongIdentificationDetailsException.class, register);
    }

    @Test
    public void checkBothHalfMapsSent_bothSent_returnTrue(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
        };

        PlayerStateClass player1 = new PlayerStateClass("uniqueID1", EnumPlayerGameState.LOST, false);
        PlayerStateClass player2 = new PlayerStateClass("uniqueID2", EnumPlayerGameState.LOST, false);

        MapCreator mapCreator = new MapCreator();
        MapClass map = mapCreator.createMapHalfMapStyle(nodes);

        GameStateClass temp;
        Mockito.when(networkConverter.getGameState()).thenReturn(new GameStateClass(player1, player2));
        temp = networkConverter.getGameState();

        Mockito.when(gameState.getMapClass()).thenReturn(map);
        Assertions.assertTrue(controller.checkBothHalfMapsSent());
    }


    @Test
    public void checkBothHalfMapsSent_notBothSent_returnFalse(){
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'W', 'W', 'G', 'G'}
        };

        PlayerStateClass player1 = new PlayerStateClass("uniqueID1", EnumPlayerGameState.LOST, false);
        PlayerStateClass player2 = new PlayerStateClass("uniqueID2", EnumPlayerGameState.LOST, false);

        MapCreator mapCreator = new MapCreator();
        MapClass map = mapCreator.createMapHalfMapStyle(nodes);

        GameStateClass temp;
        Mockito.when(networkConverter.getGameState()).thenReturn(new GameStateClass(player1, player2));
        temp = networkConverter.getGameState();

        Mockito.when(gameState.getMapClass()).thenReturn(map);
        Assertions.assertFalse(controller.checkBothHalfMapsSent());
    }

    @Test
    public void treasureCollected_notCollected_returnFalse(){
        PlayerStateClass player1 = new PlayerStateClass("uniqueID1", EnumPlayerGameState.LOST, false);
        PlayerStateClass player2 = new PlayerStateClass("uniqueID2", EnumPlayerGameState.LOST, false);

        GameStateClass temp;
        Mockito.when(networkConverter.getGameState()).thenReturn(new GameStateClass(player1, player2));
        temp = networkConverter.getGameState();

        Mockito.when(gameState.getMyPlayer()).thenReturn(player1);
        Assertions.assertFalse(controller.treasureCollected());
    }

    @Test
    public void treasureCollected_collected_returnTrue(){
        PlayerStateClass player1 = new PlayerStateClass("uniqueID1", EnumPlayerGameState.LOST, true);
        PlayerStateClass player2 = new PlayerStateClass("uniqueID2", EnumPlayerGameState.LOST, false);

        GameStateClass temp;
        Mockito.when(networkConverter.getGameState()).thenReturn(new GameStateClass(player1, player2));
        temp = networkConverter.getGameState();

        Mockito.when(gameState.getMyPlayer()).thenReturn(player1);
        Assertions.assertTrue(controller.treasureCollected());
    }

    @Test
    public void gameEnded_playerLost_returnTrue(){
        PlayerStateClass player = new PlayerStateClass("uniqueID", EnumPlayerGameState.LOST, false);
        Mockito.when(gameState.getMyPlayer()).thenReturn(player);
        PlayerStateClass ownPlayerState = gameState.getMyPlayer();

        Assertions.assertTrue(ownPlayerState.hasLost());
    }

    @Test
    public void gameEnded_playerWon_returnTrue(){
        PlayerStateClass player = new PlayerStateClass("uniqueID", EnumPlayerGameState.WON, false);
        Mockito.when(gameState.getMyPlayer()).thenReturn(player);
        PlayerStateClass ownPlayerState = gameState.getMyPlayer();

        Assertions.assertTrue(ownPlayerState.hasWon());
    }

}