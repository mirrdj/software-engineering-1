package path;

import map.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TargetChooserTest {
    private static TargetChooser targetChooser = null;
    private static HashMap<Position, Integer> distanceMap;

    @BeforeAll
    private static void setUp(){
        targetChooser = new TargetChooser();
        distanceMap = new HashMap<>();

        int[][] distances = {
                {0,    2,    4,    6},
                {2, 5002, 5004, 5006},
                {4,    6,    8,   10},
                {7,    9,   11,   13},
                {9,   11,   13,   15},
                {12,  13,   16,   17},
                {16,  15,   18,   19},
                {15,  17,   20,   21},
        };

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                distanceMap.put(new Position(i, j), distances[i][j]);
            }
        }
    }

    private List<Position> createPositionList(int startX, int startY, int w, int h){
        List<Position> positions = new ArrayList<>();

        for(int i = startX; i < w; i++) {
            for (int j = startY; j < h; j++) {
                positions.add(new Position(i, j));
            }
        }

        return positions;
    }

    @Test
    public void chooseTarget_positionsHasAllPositionsInDistance_chooseTheClosestNode() {
        Position from = new Position(0, 0);
        List<Position> positions = createPositionList(0,0, 8, 4);

        Position target = targetChooser.chooseTarget(from, positions, distanceMap);
        System.out.println(distanceMap);

        Position expected = new Position(0, 1);
        Assertions.assertEquals(expected, target);
    }

    @Test
    public void chooseTarget_positionsDoesNotHasAllPositionsInDistance_chooseTheClosestNode() {
        Position from = new Position(0, 0);
        List<Position> positions = createPositionList(6,0, 7, 3);

        Position target = targetChooser.chooseTarget(from, positions, distanceMap);
        Position expected = new Position(6, 1);
        Assertions.assertEquals(expected, target);
    }

    @Test
    public void chooseTarget_positionsDoesNotHasAllPositionsInDistanceRemoveNode_chooseTheClosestNode() {
        Position from = new Position(0, 0);
        List<Position> positions = createPositionList(6,0, 7, 3);

        Position firsTarget = targetChooser.chooseTarget(from, positions, distanceMap);
        positions.remove(firsTarget);

        Position secondTarget = targetChooser.chooseTarget(from, positions, distanceMap);

        Position expected = new Position(6, 0);
        Assertions.assertEquals(expected, secondTarget);
    }
}