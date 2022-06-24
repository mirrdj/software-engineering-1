package server.rules;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import server.exceptions.MapHasWrongSizeException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RHalfMapHasCorrectSizeTest {
    private MapCreator mapCreator = new MapCreator();
    private RHalfMapHasCorrectSize rule;

    @BeforeEach
    private void setUpBeforeClass(){
        rule = new RHalfMapHasCorrectSize();
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