package path;

import exceptions.PositionInaccessibleException;
import map.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class ShortestPathCalculatorTest {
    private HashMap<Position, Position> previousMap = new HashMap<>();
    private ShortestPathCalculator pathCalculator;

    @BeforeEach
    public void setUp(){
        pathCalculator = new ShortestPathCalculator();

        previousMap.put(new Position(0, 3), new Position(0, 2));
        previousMap.put(new Position(0, 2), new Position(0, 1));
        previousMap.put(new Position(0, 1), new Position(0, 0));
        previousMap.put(new Position(0, 0), new Position(1, 0));
        previousMap.put(new Position(1, 0), new Position(2, 0));
        previousMap.put(new Position(2, 0), new Position(2, 1));
        previousMap.put(new Position(2,1), new Position(2, 2));
        previousMap.put(new Position(2, 2), new Position(2, 3));
        previousMap.put(new Position(2, 3), new Position(3, 3));
    }

    @Test
    public void getShortestPath_givenPreviousMap_returnShortestPath_1() {
        List<Position> expected = new ArrayList<>();
        expected.add(new Position(2, 1));
        expected.add(new Position(2, 0));
        expected.add(new Position(1, 0));
        expected.add(new Position(0, 0));


        Position from = new Position(2, 1);
        Position to = new Position(0, 0);
        List<Position> shortestPath = pathCalculator.getShortestPath(from, to, previousMap);
        Assertions.assertEquals(expected, shortestPath);
    }

    @Test
    public void getShortestPath_givenPreviousMap_returnShortestPath_2() {
        List<Position> expected = new ArrayList<>();
        expected.add(new Position(3, 3));
        expected.add(new Position(2, 3));
        expected.add(new Position(2, 2));
        expected.add(new Position(2, 1));
        expected.add(new Position(2, 0));
        expected.add(new Position(1, 0));
        expected.add(new Position(0, 0));
        expected.add(new Position(0, 1));
        expected.add(new Position(0, 2));
        expected.add(new Position(0, 3));

        Position from = new Position(3, 3);
        Position to = new Position(0, 3);
        List<Position> shortestPath = pathCalculator.getShortestPath(from, to, previousMap);
        Assertions.assertEquals(expected, shortestPath);
    }
    @ParameterizedTest
    @CsvSource({"5, 3", "0, 3"})
    public void getShortestPath_invalidStartingPoint_throwIllegalArgumentException(int x, int y) {
        Position from = new Position(x, y);
        Position to = new Position(0, 1);

        Executable getShortestPath = () -> pathCalculator.getShortestPath(from, to, previousMap);
        Assertions.assertThrows(PositionInaccessibleException.class, getShortestPath);
    }


    @ParameterizedTest
    @CsvSource({"5, 3", "3, 3"})
    public void getShortestPath_invalidTargetPoint_throwIllegalArgumentException(int x, int y) {
        Position from = new Position(0, 3);
        Position to = new Position(x, y);

        Executable getShortestPath = () -> pathCalculator.getShortestPath(from, to, previousMap);
        Assertions.assertThrows(PositionInaccessibleException.class, getShortestPath);
    }
}