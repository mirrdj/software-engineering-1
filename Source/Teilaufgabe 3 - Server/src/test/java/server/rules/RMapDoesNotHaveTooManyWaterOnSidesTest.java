package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.UniqueGameIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import server.exceptions.WaterFieldsOnSidesExceededException;

class RMapDoesNotHaveTooManyWaterOnSidesTest {
    MapCreator mapCreator = new MapCreator();
    RMapDoesNotHaveTooManyWaterOnSides rule;

    @BeforeEach
    private void setUp(){
        rule = new RMapDoesNotHaveTooManyWaterOnSides();
    }

    @Test
    public void validateHalfMap_waterFieldsOnShortSideEqualTo2_throwWaterFieldsOnSidesExceededException() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'W', 'G', 'M', 'W', 'W', 'W', 'G', 'G'},
                {'W', 'G', 'G', 'G', 'G', 'M', 'M', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        HalfMap halfMap = mapCreator.createMap(nodes);

        Executable checkFields = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(WaterFieldsOnSidesExceededException.class, checkFields);
    }

    @Test
    public void validateHalfMap_waterFieldsOnLongSideEqualTo4_throwWaterFieldsOnSidesExceededException() {
        char[][] nodes = {
                {'G', 'G', 'W', 'W', 'W', 'W', 'G', 'G'},
                {'G', 'G', 'M', 'G', 'G', 'M', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'M', 'M', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        HalfMap halfMap = mapCreator.createMap(nodes);

        Executable checkFields = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(WaterFieldsOnSidesExceededException.class, checkFields);
    }
}