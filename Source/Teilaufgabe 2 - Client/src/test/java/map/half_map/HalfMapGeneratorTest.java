package map.half_map;

import exceptions.WrongNumberOfGrassFieldsException;
import exceptions.WrongNumberOfMountainFieldsException;
import exceptions.WrongNumberOfWaterFieldsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HalfMapGeneratorTest {
    // TODO: check this one, not so sure about it
    @ParameterizedTest
    @CsvSource({ "6, 5", "7, 4", "5, 4", "4, 3" })
    void generateHalfMap_differentNumberOfWaterMountain_wantedEqualPlaced(int wantedWater, int wantedMountain) {
        HalfMapGenerator halfMapGenerator = new HalfMapGenerator(wantedWater, wantedMountain);
        HalfMapClass map = halfMapGenerator.generateHalfMap();

        int placedWater = map.getWaterNumber();
        int placedMountain = map.getMountainNumber();

        Assertions.assertEquals(wantedWater, placedWater);
        Assertions.assertEquals(wantedMountain, placedMountain);
    }

    @ParameterizedTest
    @CsvSource({ "1, 3", "2, 3", "3, 3", "0, 3", "15, 3", "20, 3"})
    void generateHalfMap_wrongNumberWaterFields_throwWrongNumberOfWaterFieldsException(int wantedWater, int wantedMountain) {
        Executable generateMap = () -> new HalfMapGenerator(wantedWater, wantedMountain);
        Assertions.assertThrows(WrongNumberOfWaterFieldsException.class, generateMap);
    }

    @ParameterizedTest
    @CsvSource({ "4, 0", "4, 1", "4, 2", "4, 14" })
    void generateHalfMap_wrongNumberMountainFields_throwWrongNumberOfMountainFieldsException(int wantedWater, int wantedMountain) {
        Executable generateMap = () -> new HalfMapGenerator(wantedWater, wantedMountain);
        Assertions.assertThrows(WrongNumberOfMountainFieldsException.class, generateMap);
    }

    @ParameterizedTest
    @CsvSource({ "9, 9", "10, 8", "10, 10", "14, 8", "8, 13" })
    void generateHalfMap_wrongNumberGrassFields_throwWrongNumberOfGrassFieldsException(int wantedWater, int wantedMountain) {
        Executable generateMap = () -> new HalfMapGenerator(wantedWater, wantedMountain);
        Assertions.assertThrows(WrongNumberOfGrassFieldsException.class, generateMap);
    }


}