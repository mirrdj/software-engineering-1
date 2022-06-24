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
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'}
        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        String playerID = "id0123456789";
        HalfMap halfMap = mapCreator.createHalfMap(nodes, playerID);

        System.out.println(halfMap.toString());

       Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
       Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

    @Test
    void validateHalfMap_highestXValueOfNode8_throwMapWrongSizeException() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        String playerID = "id0123456789";
        HalfMap halfMap = mapCreator.createHalfMap(nodes, playerID);

        System.out.println(halfMap.toString());

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

    @Test
    void validateHalfMap_highestYValueOfNode2_throwMapWrongSizeException() {
        char[][] nodes = {
                {'G', 'G', 'G'},
                {'G', 'W', 'M'},
                {'G', 'W', 'M'},
                {'G', 'W', 'M'},
                {'G', 'W', 'G'},
                {'G', 'G', 'G'},
                {'G', 'G', 'G'},
                {'G', 'G', 'G'}
        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        String playerID = "id0123456789";
        HalfMap halfMap = mapCreator.createHalfMap(nodes, playerID);

        System.out.println(halfMap.toString());

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

    @Test
    void validateHalfMap_highestYValueOfNode4_throwMapWrongSizeException() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M', 'G'},
                {'G', 'W', 'G', 'M', 'G'},
                {'G', 'W', 'G', 'M', 'G'},
                {'G', 'W', 'G', 'M', 'G'},
                {'G', 'W', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G'},
        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        String playerID = "id0123456789";
        HalfMap halfMap = mapCreator.createHalfMap(nodes, playerID);

        System.out.println(halfMap.toString());

        Executable validateSize = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(MapHasWrongSizeException.class, validateSize);
    }

}