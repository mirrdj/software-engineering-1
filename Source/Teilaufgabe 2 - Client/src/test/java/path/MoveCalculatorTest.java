package path;

import helper.MapCreator;
import map.EnumTerrain;
import map.MapClass;
import map.MapNodeClass;
import map.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MoveCalculatorTest {
    private MapCreator mapCreator = new MapCreator();
    private MoveCalculator moveCalculator;


    @Test
    public void getMoves() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'}
        };
        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        moveCalculator = new MoveCalculator(map.getTerrainNodes());

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 1));
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 0));
        positions.add(new Position(2, 0));
        positions.add(new Position(3, 0));
        Queue<EnumMove> actual = moveCalculator.getMoves(positions);

        Queue<EnumMove> expected = new ArrayDeque<>();
        expected.add(EnumMove.UP);
        expected.add(EnumMove.UP);
        expected.add(EnumMove.RIGHT);
        expected.add(EnumMove.RIGHT);
        expected.add(EnumMove.RIGHT);
        expected.add(EnumMove.RIGHT);
        expected.add(EnumMove.RIGHT);
        expected.add(EnumMove.RIGHT);
        expected.add(EnumMove.RIGHT);

        Assertions.assertTrue(Arrays.equals(expected.toArray(), actual.toArray()));
    }
}