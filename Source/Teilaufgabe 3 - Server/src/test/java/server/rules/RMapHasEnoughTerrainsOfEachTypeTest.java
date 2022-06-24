package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.UniqueGameIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import server.exceptions.WrongFieldNumberException;

class RMapHasEnoughTerrainsOfEachTypeTest {
    private RMapHasEnoughTerrainsOfEachType rule;
    private MapCreator mapCreator = new MapCreator();
    @BeforeEach
    private void setUp(){
        rule = new RMapHasEnoughTerrainsOfEachType();
    }
    @Test
    public void validateHalfMap_notEnoughWaterFields_throwWrongNumberOfFieldsException() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},

        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        HalfMap halfMap = mapCreator.createMap(nodes);

        Executable checkFields = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(WrongFieldNumberException.class, checkFields);
    }

    @Test
    public void validateHalfMap_notEnoughMountainFields_throwWrongNumberOfFieldsException() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'W', 'W', 'W', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        HalfMap halfMap = mapCreator.createMap(nodes);

        Executable checkFields = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(WrongFieldNumberException.class, checkFields);
    }

    @Test
    public void validateHalfMap_notEnoughGrassFields_throwWrongNumberOfFieldsException() {
        char[][] nodes = {
                {'M', 'M', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'M', 'M', 'W', 'W', 'W', 'W', 'G', 'G'},
                {'M', 'M', 'M', 'M', 'G', 'G', 'G', 'G'},
                {'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M'},
        };

        UniqueGameIdentifier gameID = new UniqueGameIdentifier("game1");
        HalfMap halfMap = mapCreator.createMap(nodes);

        Executable checkFields = () -> rule.validateHalfMap(gameID, halfMap);
        Assertions.assertThrows(WrongFieldNumberException.class, checkFields);
    }
}