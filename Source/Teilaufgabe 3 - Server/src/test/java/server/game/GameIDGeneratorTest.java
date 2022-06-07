package server.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import server.uniqueID.GameID;

import static org.junit.jupiter.api.Assertions.*;

class GameIDGeneratorTest {
    public GameIDGenerator idGenerator;
    @BeforeEach
    private void initGeneratedIDs(){
        idGenerator = new GameIDGenerator();
    }
    @RepeatedTest(10)
    public void generateID_generateMultipleRandomIDs_eachIDdifferent(){
        GameID id1 = idGenerator.generateID();
        GameID id2 = idGenerator.generateID();

        assertNotEquals(id1, id2);
    }
    @Test
    public void generateID_checkIDlength_lengthEqual5(){
        GameID id = idGenerator.generateID();
        int expected = 5;
        int actual = id.getID().length();

        assertEquals(expected, actual);
    }
}