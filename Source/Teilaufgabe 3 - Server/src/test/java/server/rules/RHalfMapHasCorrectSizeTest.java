package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.UniqueGameIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import server.exceptions.MapHasWrongSizeException;

class RHalfMapHasCorrectSizeTest {
    private MapCreator mapCreator = new MapCreator();
    private RMapHasCorrectSize rule;

    @BeforeEach
    private void setUpBeforeClass(){
        rule = new RMapHasCorrectSize();
    }

    @Test
    void validateHalfMap_highestXValueOfNode6_throwMapWrongSizeException() {
       HalfMap halfMap = mapCreator.createMap(0, 4, 0, 7);
       UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");

       Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
       Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

    @Test
    void validateHalfMap_highestXValueOfNode8_throwMapWrongSizeException() {
        HalfMap halfMap = mapCreator.createMap(0, 4, 1, 9);
        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

    @Test
    void validateHalfMap_highestYValueOfNode2_throwMapWrongSizeException() {
        HalfMap halfMap = mapCreator.createMap(0, 3, 0, 8);
        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

    @Test
    void validateHalfMap_highestYValueOfNode4_throwMapWrongSizeException() {
        HalfMap halfMap = mapCreator.createMap(1, 5, 0, 7);
        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

    @Test
    void validateHalfMap_wrongSize_throwMapWrongSizeException() {
        HalfMap halfMap = mapCreator.createMap(0, 4, 0, 7);
        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

}