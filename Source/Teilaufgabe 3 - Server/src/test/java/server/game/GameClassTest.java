package server.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import server.uniqueID.GameID;
import server.exceptions.NoPlayerRegisteredException;
import server.exceptions.MaximumOfPlayersAlreadyRegisteredExeception;
import server.player.Player;

class GameClassTest {
    private GameClass game;
    @BeforeEach
    private void createGame(){
        GameID id = new GameID("id123");
        game = new GameClass(id);
    }
    @Test
    public void getRound_updateRoundCalledOnce_roundEqualToOne(){
        game.updateRound();
        int expected = 1;
        int actual = game.getRound();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void updateRound_updateOnce_roundEqualInitValuePlusOne() {
        int before = game.getRound();
        int expectedAfter = before + 1;

        game.updateRound();
        int actualAfter = game.getRound();

        Assertions.assertEquals(expectedAfter, actualAfter);
    }

    @Test
    public void registerPlayer_twoPlayersAlreadyRegistered_throwException(){
        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);
        Player player3 = Mockito.mock(Player.class);

        game.registerPlayer(player1);
        game.registerPlayer(player2);

        Executable registerPlayer = () -> game.registerPlayer(player3);
        Assertions.assertThrows(MaximumOfPlayersAlreadyRegisteredExeception.class, registerPlayer);
    }
    @Test
    public void getPlayerWithID_idOfPlayerNotRegistered_throwException(){
        Executable getPlayer = () -> game.getPlayerWithID("id0123456789");
        Assertions.assertThrows(NoPlayerRegisteredException.class, getPlayer);
    }
}