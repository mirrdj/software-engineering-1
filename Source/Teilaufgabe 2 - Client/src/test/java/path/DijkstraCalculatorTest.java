package path;

import exceptions.PlacedOnWrongFieldException;
import helper.MapCreator;
import map.EnumTerrain;
import map.MapClass;
import map.MapNodeClass;
import map.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class DijkstraCalculatorTest {
    private DijkstraCalculator calculator = null;
    private MapCreator mapCreator = new MapCreator();

    @Test
    public void dijkstraAlgorithm_startFromWaterField_throwPlacedOnWrongFieldException() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(1, 1);
        Executable calcDijkstra = () -> calculator.dijkstraAlgorithm(from);
        Assertions.assertThrows(PlacedOnWrongFieldException.class, calcDijkstra);
    }

    @Test
    public void dijkstraAlgorithm_fromGrassToMountain_distanceEqual3() {
        char[][] nodes = {
                {'G', 'M', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(0, 0);
        Position to = new Position(1, 0);
        int actualDistance = calculator
                .dijkstraAlgorithm(from)
                .getDistanceMap()
                .get(to);

        Assertions.assertEquals(3, actualDistance);
    }

    @Test
    public void dijkstraAlgorithm_fromGrassToGrass_distanceEqual2() {
        char[][] nodes = {
                {'G', 'M', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(0, 0);
        Position to = new Position(0, 1);
        int actualDistance = calculator
                .dijkstraAlgorithm(from)
                .getDistanceMap()
                .get(to);

        Assertions.assertEquals(2, actualDistance);
    }

    @Test
    public void dijkstraAlgorithm_fromGrassToWater_distanceEqual5001() {
        char[][] nodes = {
                {'G', 'M', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(0, 1);
        Position to = new Position(1, 1);
        int actualDistance = calculator
                .dijkstraAlgorithm(from)
                .getDistanceMap()
                .get(to);

        Assertions.assertEquals(5001, actualDistance);
    }

    @Test
    public void dijkstraAlgorithm_fromMountainToMountain_distanceEqual4() {
        char[][] nodes = {
                {'G', 'M', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(3, 0);
        Position to = new Position(3, 1);
        int actualDistance = calculator
                .dijkstraAlgorithm(from)
                .getDistanceMap()
                .get(to);

        Assertions.assertEquals(4, actualDistance);
    }

    @Test
    public void dijkstraAlgorithm_fromUpperLeftToLowerRight_distanceEqual13() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };

        int[][] distances = {
                {0,    2,    4,    6},
                {2, 5002, 5004, 5006},
                {4,    6,    8,   10},
                {7,    9,   11,   13}
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(0, 0);
        Position to = new Position(3, 3);
        int actualDistance = calculator
                .dijkstraAlgorithm(from)
                .getDistanceMap()
                .get(to);

        Assertions.assertEquals(distances[3][3], actualDistance);
    }

    @ParameterizedTest
    @CsvSource({ "0, 0", "0, 3", "2, 2", "3, 0", "3, 2",})
    public void dijkstraAlgorithm_fromUpperLeftToPosition_distanceEqualPrecalculated(int x, int y) {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };

        int[][] distances = {
                {0,    2,    4,    7},
                {2, 5002,    6,    9},
                {4, 5004,    8,   11},
                {6, 5006,   10,   13}
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(0, 0);
        Position to = new Position(x, y);
        int actualDistance = calculator
                .dijkstraAlgorithm(from)
                .getDistanceMap()
                .get(to);

        Assertions.assertEquals(distances[y][x], actualDistance);
    }

    @ParameterizedTest
    @CsvSource({ "0, 1", "0, 2", "0, 3", "1, 1", "1, 0", "2, 1", "3, 0", "3, 1", "0, 0", "2, 0"})
    public void dijkstraAlgorithm_fromLowerLeftToPosition_distanceEqualPrecalculated(int x, int y) {
        char[][] nodes = {
                {'G', 'G', 'M', 'M'},
                {'G', 'W', 'G', 'W'},
                {'W', 'G', 'G', 'G'},
                {'G', 'M', 'G', 'M'}
        };

        int[][] distances = {
                {  18,   16,    13,   17},
                {  20, 5007,    10, 5011},
                {5001,     6,    8,   10},
                {   0,     3,    6,    9}
        };


        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(0, 3);
        Position to = new Position(x, y);
        int actualDistance = calculator
                .dijkstraAlgorithm(from)
                .getDistanceMap()
                .get(to);

        Assertions.assertEquals(distances[y][x], actualDistance);
    }

    @ParameterizedTest
    @CsvSource({ "1, 3, 2, 3", "2, 0, 1, 0", "0, 0, 0, 1", "2, 2, 3, 2", "2, 0, 3, 0"})
    public void dijkstraAlgorithm_fromLowerLeft_returnsCorrectPrevious(int prevX, int prevY, int targetX, int targetY) {
        char[][] nodes = {
                {'G', 'G', 'M', 'M'},
                {'G', 'W', 'G', 'W'},
                {'W', 'G', 'G', 'G'},
                {'G', 'M', 'G', 'M'}
        };


        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(0, 3);
        Position to = new Position(targetX, targetY);
        Position expectedPrevious = new Position(prevX, prevY);

        Position actualPrevious = calculator
                .dijkstraAlgorithm(from)
                .getPreviousMap()
                .get(to);

        Assertions.assertEquals(expectedPrevious, actualPrevious);
    }

    @ParameterizedTest
    @CsvSource({ "2, 3, 3, 3", "2, 2, 2, 3", "1, 0, 2, 0", "0, 2, 0, 3", "2, 2, 3, 2"})
    public void dijkstraAlgorithm_fromUpperLeft_returnsCorrectPrevious(int prevX, int prevY, int targetX, int targetY) {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        calculator = new DijkstraCalculator(halfMap);

        Position from = new Position(0, 0);
        Position to = new Position(targetX, targetY);
        Position expectedPrevious = new Position(prevX, prevY);

        Position actualPrevious = calculator
                .dijkstraAlgorithm(from)
                .getPreviousMap()
                .get(to);

        Assertions.assertEquals(expectedPrevious, actualPrevious);
    }
}