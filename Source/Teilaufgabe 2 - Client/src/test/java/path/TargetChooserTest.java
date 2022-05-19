package path;

import map.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TargetChooserTest {
    static TargetChooser targetChooser = null;
    static HashMap<Position, Integer> distances;

    @BeforeAll
    static void setUp(){
        targetChooser = new TargetChooser();
        distances = new HashMap<>();
        int value = 0;

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                distances.put(new Position(i, j), value * 2);
                value++;
            }
        }
    }

    List<Position> createPositionList(int startX, int startY, int w, int h){
        List<Position> positions = new ArrayList<>();

        for(int i = startX; i < w; i++) {
            for (int j = startY; j < h; j++) {
                positions.add(new Position(i, j));
            }
        }

        return positions;
    }

    @Test
    void chooseTarget_positionsHasAllPositionsInDistance_chooseTheClosestNode() {
        Position from = new Position(0, 0);
        List<Position> positions = createPositionList(0,0, 8, 4);

        Position target = targetChooser.chooseTarget(from, positions, distances);
        System.out.println(distances);

        Position expected = new Position(0, 1);
        Assertions.assertEquals(expected, target);
    }

    @Test
    void chooseTarget_positionsDoesNotHasAllPositionsInDistance_chooseTheClosestNode() {
        Position from = new Position(0, 0);
        List<Position> positions = createPositionList(6,0, 7, 3);

        Position target = targetChooser.chooseTarget(from, positions, distances);
        Position expected = new Position(6, 0);
        Assertions.assertEquals(expected, target);
    }
}