package server.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import server.UniqueID.GameID;
import server.exceptions.IDHasWrongLengthException;

class GameIDTest {
    @ParameterizedTest
    @CsvSource({"id1y", "id34c8"})
    void GameID_wrongLength_throwException(String id){
        Executable generateGameID = () -> new GameID(id);
        Assertions.assertThrows(IDHasWrongLengthException.class, generateGameID);
    }

    @Test
    void equals_equalIDs_equalsReturnTrue(){
        GameID id1 = new GameID("ID123");
        GameID id2 = new GameID("ID123");

        boolean idsEqual = id1.equals(id2);
        Assertions.assertTrue(idsEqual);
    }
}