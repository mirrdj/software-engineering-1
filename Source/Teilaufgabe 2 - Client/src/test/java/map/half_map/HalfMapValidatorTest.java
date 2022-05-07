package map.half_map;

import map.EnumTerrain;
import map.MapClass;
import map.MapNodeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class HalfMapValidatorTest {
    private HalfMapValidator validator = null;

    @BeforeEach
    private void setUpBeforeClass(){
        validator = new HalfMapValidator();
    }

    public MapClass createHalfMap(char[][] nodes) {
        boolean fortressPlaced = false;
        List<MapNodeClass> nodeList = new ArrayList<>();

        for(int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                EnumTerrain terrain;
                boolean fortPresent = false;

                switch (nodes[i][j]) {
                    case 'W' -> terrain = EnumTerrain.WATER;
                    case 'M' -> terrain = EnumTerrain.MOUNTAIN;
                    default -> {
                        terrain = EnumTerrain.GRASS;
                        if (!fortressPlaced) {
                            fortressPlaced = true;
                            fortPresent = true;
                        }
                    }
                }

                nodeList.add(new MapNodeClass(i, j, fortPresent, terrain));

            }
        }

        return new MapClass(nodeList);
    }
    @Test
    public void mapIsValid_validMap_returnTrue(){
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'W', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = createHalfMap(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertTrue(valid);
    }
    @Test
    public void mapIsValid_islandExists_returnFalse(){
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'W', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'W', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = createHalfMap(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }
    @Test
    public void mapIsValid_wrongSize_returnTrue(){
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G'}
        };

        MapClass halfMap = createHalfMap(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }

    @Test void mapIsValid_exceededWaterFieldShortSide_returnFalse(){
        char[][] nodes = {
                {'G', 'W', 'W', 'M'},
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},

        };

        MapClass halfMap = createHalfMap(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }

    @Test void mapIsValid_exceededWaterFieldLongSide_returnFalse(){
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'W', 'G', 'G', 'M'},
                {'W', 'G', 'G', 'M'},
                {'G', 'G', 'G', 'M'},
                {'G', 'G', 'G', 'G'},
                {'W', 'G', 'G', 'G'},
                {'W', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},

        };

        MapClass halfMap = createHalfMap(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }

    @Test
    public void mapIsValid_notEnoughWaterFields_returnFalse(){
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

        MapClass halfMap = createHalfMap(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }

    @Test
    public void mapIsValid_notEnoughMountainFields_returnFalse(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G'},

        };

        MapClass halfMap = createHalfMap(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }



}