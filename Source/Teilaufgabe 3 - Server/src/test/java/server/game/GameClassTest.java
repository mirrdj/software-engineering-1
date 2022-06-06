package server.game;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import server.exceptions.MapAlreadySet;
import server.exceptions.NoPlayerRegistered;
import server.exceptions.TwoPlayersAlreadyRegisteredExeception;
import server.map.MapClass;
import server.player.Player;

import static org.junit.jupiter.api.Assertions.*;

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
        game.registerPlayer(new Player());
        game.registerPlayer(new Player());

        Executable registerPlayer = () -> game.registerPlayer(new Player());
        Assertions.assertThrows(TwoPlayersAlreadyRegisteredExeception.class, registerPlayer);
    }
    @Test
    public void getPlayerWithID_idOfPlayerNotRegistered_throwException(){
        Executable getPlayer = () -> game.getPlayerWithID("id");
        Assertions.assertThrows(NoPlayerRegistered.class, getPlayer);
    }

    @Test
    public void setFullMap_fullMapAlreadySet_throwException() {
        game.setFullMap(new MapClass());
        Executable setMap = () -> game.setFullMap(new MapClass());

        Assertions.assertThrows(MapAlreadySet.class, setMap);
    }
}