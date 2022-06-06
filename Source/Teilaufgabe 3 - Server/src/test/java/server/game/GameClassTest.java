package server.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameClassTest {
    private GameClass game;
    @BeforeEach
    private void createGame(){
        GameID id = new GameID("id123");
        game = new GameClass(id);
    }
    @Test
    public void getRound_updateRoundNeverCalled_roundEqualZero(){}
    @Test
    public void updateRound_updateOnce_roundEqualInitValuePlusOne() {}

    @Test
    public void registerPlayer_twoPlayersAlreadyRegistered_throwException(){}

    @Test
    public void getPlayerWithID_idOfPlayerNotRegistered_throwException(){}

    @Test
    public void setFullMap_fullMapAlreadySet_throwException() {}
}