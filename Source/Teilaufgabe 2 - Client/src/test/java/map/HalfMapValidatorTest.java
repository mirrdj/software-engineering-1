package map;


import helper.MapCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class HalfMapValidatorTest {
    private HalfMapValidator validator = null;
    private MapCreator mapCreator = new MapCreator();

    @BeforeEach
    private void setUpBeforeClass(){
        validator = new HalfMapValidator();
    }

    private MapClass createHalfMap(char[][] nodes) {
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

                nodeList.add(new MapNodeClass(i, j, terrain, fortPresent));

            }
        }

        return new MapClass(nodeList);
    }

    private MapClass createMapFullMapStyle(char[][] nodes, Position myPlayerPosition, Position myTreasure, Position enemyFort, boolean placeMultipleFortresses){
        List<MapNodeClass> nodeList = new ArrayList<>();
        EnumPlayerPositionState player;
        EnumTreasureState treasure;
        EnumFortState fort = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;

        for(int y = 0; y < nodes.length; y++) {
            for (int x = 0; x < nodes[0].length; x++) {
                Position currPosition = new Position(x, y);
                EnumTerrain terrain;

                switch (nodes[y][x]) {
                    case 'W' -> terrain = EnumTerrain.WATER;
                    case 'M' -> terrain = EnumTerrain.MOUNTAIN;
                    default -> {
                        terrain = EnumTerrain.GRASS;
                        if(placeMultipleFortresses)
                            fort = EnumFortState.MY_FORT_PRESENT;
                    }
                }
                if(currPosition.getX() == myPlayerPosition.getX() && currPosition.getY() == myPlayerPosition.getY())
                    player = EnumPlayerPositionState.MY_POSITION;
                else
                    player = EnumPlayerPositionState.NO_PLAYER_PRESENT;

                if(currPosition.getX() == myTreasure.getX() && currPosition.getY() == myTreasure.getY())
                    treasure = EnumTreasureState.MY_TREASURE_IS_PRESENT;
                else
                    treasure = EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE;

                if(currPosition.getX() == enemyFort.getX() && currPosition.getY() == enemyFort.getY())
                    fort = EnumFortState.ENEMY_FORT_PRESENT;
                else if(!placeMultipleFortresses)
                    fort = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;

                nodeList.add(new MapNodeClass(x, y, terrain, fort, player, treasure));
            }
        }

        return new MapClass(nodeList);
    }
    
    @Test
    public void mapIsValid_validMap_returnTrue(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G'},
                {'G', 'W', 'W', 'W', 'W', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'M', 'M', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        boolean valid = validator.mapIsValid(halfMap);

        Assertions.assertTrue(valid);
    }
    @Test
    public void mapIsValid_islandExists_returnFalse_1(){
        char[][] nodes = {
                {'G', 'W', 'G', 'G', 'W', 'G', 'G', 'G'},
                {'G', 'W', 'W', 'W', 'W', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'M', 'M', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }

    @Test
    public void mapIsValid_islandExists_returnFalse_2(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'W', 'W', 'W', 'G', 'G', 'G'},
                {'G', 'G', 'W', 'G', 'W', 'G', 'G', 'G'},
                {'M', 'M', 'W', 'W', 'W', 'G', 'G', 'G'},
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }

    @Test
    public void mapIsValid_islandExists_returnFalse_3(){
        char[][] nodes = {
                {'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'W', 'W', 'G', 'W', 'W', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'M', 'M', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }
    @Test
    public void mapIsValid_islandExists_returnFalse_4(){
        char[][] nodes = {
                {'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'W', 'M', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'M', 'W', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
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

    @Test
    public void mapIsValid_exceededWaterFieldShortSide_returnFalse(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'W', 'G', 'W', 'W', 'W', 'G', 'G', 'G'},
                {'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'M', 'M', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }

    @Test
    public void mapIsValid_exceededWaterFieldLongSide_returnFalse(){
        char[][] nodes = {
                {'G', 'W', 'W', 'G', 'G', 'W', 'W', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'M', 'M', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
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
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'W', 'W', 'W', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
        };

        MapClass halfMap = mapCreator.createMapHalfMapStyle(nodes);
        boolean valid = validator.mapIsValid(halfMap);
        Assertions.assertFalse(valid);
    }
}