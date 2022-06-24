package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.UniqueGameIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import server.exceptions.MapHasWrongSizeException;
import server.exceptions.WrongFortNumberException;

import static org.junit.jupiter.api.Assertions.*;

class RMapHasOneFortTest {
    private MapCreator mapCreator = new MapCreator();
    private RMapHasOneFort rule;

    @BeforeEach
    private void setUp(){
        rule = new RMapHasOneFort();

    }

    @Test
    public void validateHalfMap_halfMapHasNoFort_throwWrongFortNumberException() {
        boolean placeFort = false;
        HalfMap halfMap = mapCreator.createMap( 4, 8, placeFort);
        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(WrongFortNumberException.class, validateSize);
    }

    @Test
    public void validateHalfMap_halfMapHasMoreThanOneFort_throwWrongFortNumberException() {
        boolean placeFort = false;
        HalfMap halfMap = mapCreator.createMap( 4, 8, placeFort);
        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(WrongFortNumberException.class, validateSize);
    }
}