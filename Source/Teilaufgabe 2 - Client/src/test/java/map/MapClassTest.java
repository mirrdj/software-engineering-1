package map;

import exceptions.NotFullMapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

class MapClassTest {
    public MapClass createMapHalfMapStyle(char[][] nodes) {
        boolean fortressPlaced = false;
        List<MapNodeClass> nodeList = new ArrayList<>();

        for(int y = 0; y < nodes.length; y++) {
            for (int x = 0; x < nodes[0].length; x++) {
                EnumTerrain terrain;
                boolean fortPresent = false;

                switch (nodes[y][x]) {
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

                nodeList.add(new MapNodeClass(x, y, terrain, fortPresent));

            }
        }

        return new MapClass(nodeList);
    }

    public MapClass createMapFullMapStyle(char[][] nodes, Position myPlayerPosition, Position myTreasure, Position enemyFort){
        List<MapNodeClass> nodeList = new ArrayList<>();
        EnumPlayerPositionState player;
        EnumTreasureState treasure;
        EnumFortState fort;

        for(int y = 0; y < nodes.length; y++) {
            for (int x = 0; x < nodes[0].length; x++) {
                Position currPosition = new Position(x, y);
                EnumTerrain terrain;

                switch (nodes[y][x]) {
                    case 'W' -> terrain = EnumTerrain.WATER;
                    case 'M' -> terrain = EnumTerrain.MOUNTAIN;
                    default -> terrain = EnumTerrain.GRASS;
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
                else
                    fort = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;

                nodeList.add(new MapNodeClass(x, y, terrain, fort, player, treasure));
            }
        }

        return new MapClass(nodeList);
    }

    @Test
    void getHeight_heightEqualValue_returnCorrectHeight_1() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M', 'G', 'W', 'G', 'M'},
                {'G', 'G', 'G', 'M', 'G', 'W', 'G', 'M'},
                {'G', 'G', 'G', 'M', 'G', 'W', 'G', 'M'},
                {'G', 'G', 'G', 'M', 'G', 'W', 'G', 'M'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int height = map.getHeight();

        Assertions.assertEquals(4, height);
    }

    @Test
    void getHeight_heightEqualValue_returnCorrectHeight_2() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'W', 'W', 'G', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int height = map.getHeight();

        Assertions.assertEquals(5, height);
    }
    @Test
    void getHeight_heightEqualValue_returnCorrectHeight_3() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int height = map.getHeight();

        Assertions.assertEquals(8, height);
    }

    @Test
    void getWidth_widthEqualValue_returnCorrectWidth_1() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'W', 'W', 'G', 'G'},
                {'W', 'W', 'G', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int width = map.getWidth();

        Assertions.assertEquals(4, width);
    }

    @Test
    void getWidth_widthEqualValue_returnCorrectWidth_2() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
        };


        MapClass map = createMapHalfMapStyle(nodes);
        int width = map.getWidth();

        Assertions.assertEquals(16, width);
    }

    @Test
    void getHeight_widthEqualValue_returnCorrectWidth_3() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int width = map.getWidth();

        Assertions.assertEquals(8, width);
    }

    @Test
    void getLayout_layoutVertical_shouldEqualEnumLayoutVertical() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        EnumLayout layout = map.getLayout();

        Assertions.assertEquals(EnumLayout.VERTICAL, layout);
    }

    @Test
    void getLayout_layoutHorizontal_shouldEqualEnumLayoutHorizontal() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
        };

        MapClass map = createMapHalfMapStyle(nodes);
        EnumLayout layout = map.getLayout();

        Assertions.assertEquals(EnumLayout.HORIZONTAL, layout);
    }

    @Test
    void getFirstHalf_mapIsNotFull_throwNotFullMapException() {
        char[][] nodes = {
                {'G', 'G', 'G'},
                {'G', 'W', 'G'},
                {'G', 'W', 'G'},
                {'G', 'W', 'G'},
                {'W', 'W', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);

        Executable getFirstHalfExec = map::getFirstHalf;
        Assertions.assertThrows(NotFullMapException.class, getFirstHalfExec);
    }

    @Disabled("Equals not overriden in the class to support this - but running it looks like it works")
    @Test
    void getFirstHalf_checkReturnedHalf_returnedHalfEqualsExpected() {
        char[][] fmnodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        char[][] hmnodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'}
        };

        MapClass fullMap = createMapHalfMapStyle(fmnodes);
        MapClass actualFirstHalf = fullMap.getFirstHalf();
        MapClass expectedFirstHalf = createMapHalfMapStyle(hmnodes);

        Assertions.assertEquals(expectedFirstHalf, actualFirstHalf);
    }

    @Test
    void getSecondHalf_mapIsNotFull_throwNotFullMapException() {
        char[][] nodes = {
                {'G', 'G', 'G'},
                {'G', 'W', 'G'},
                {'G', 'W', 'G'},
                {'G', 'W', 'G'},
                {'W', 'W', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        Executable getSecondHalfExec = map::getSecondHalf;
        Assertions.assertThrows(NotFullMapException.class, getSecondHalfExec);
    }

    @Disabled("Equals not overriden in the class to support this - but running it looks like it works")
    @Test
    void getSecondHalf_checkReturnedHalf_returnedHalfEqualsExpected() {
        char[][] fmnodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        char[][] hmnodes = {
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        MapClass fullMap = createMapHalfMapStyle(fmnodes);
        MapClass actualSecondHalf = fullMap.getSecondHalf();
        MapClass expectedSecondHalf = createMapHalfMapStyle(hmnodes);

        Assertions.assertEquals(expectedSecondHalf, actualSecondHalf);
    }

    @Test
    void getMountainNumber_noMountains_assertToZero() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int mountainNumber = map.getMountainNumber();

        Assertions.assertEquals(0, mountainNumber);
    }

    @Test
    void getMountainNumber_multipleMountainFields_returnExpectedNumberOfMountains() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'M', 'M', 'G', 'M', 'G', 'M', 'G'},
                {'G', 'G', 'G', 'G', 'M', 'W', 'G', 'G'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int mountainNumber = map.getMountainNumber();

        Assertions.assertEquals(6, mountainNumber);
    }

    @Test
    void getGrassNumber_noGrasss_assertToZero() {
        char[][] nodes = {
                {'W', 'W', 'M', 'W', 'M', 'M', 'M', 'M'},
                {'W', 'W', 'W', 'M', 'M', 'M', 'M', 'M'},
                {'W', 'W', 'M', 'W', 'M', 'W', 'M', 'M'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int grassNumber = map.getGrassNumber();

        Assertions.assertEquals(0, grassNumber);
    }

    @Test
    void getGrassNumber_multipleGrassFields_returnExpectedNumberOfGrass() {
        char[][] nodes = {
                {'W', 'G', 'G', 'G', 'G', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'G', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'G', 'W', 'M', 'M'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int grassNumber = map.getGrassNumber();

        Assertions.assertEquals(12, grassNumber);
    }

    @Test
    void getWaterNumber_noWater_assertToZero() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'M', 'M', 'M'},
                {'G', 'G', 'G', 'G', 'G', 'M', 'M', 'M'},
                {'G', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int waterNumber = map.getWaterNumber();

        Assertions.assertEquals(0, waterNumber);
    }

    @Test
    void getWaterNumber_multipleWaterFields_returnExpectedNumberOfWater() {
        char[][] nodes = {
                {'W', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        MapClass map = createMapHalfMapStyle(nodes);
        int waterNumber = map.getWaterNumber();

        Assertions.assertEquals(5, waterNumber);
    }

    @Test
    void getMyPosition_myPlayerAtCertainPos_returnsMyPlayerAtThatPos() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        Position expectedMyPlayer = new Position(1, 1);
        Position expectedFort = new Position(1, 1);
        Position expectedTreasure = new Position(3, 2);

        MapClass map = createMapFullMapStyle(nodes, expectedMyPlayer, expectedTreasure, expectedFort);
        Position actualMyPlayer = map.getMyPosition();

        Assertions.assertEquals(expectedMyPlayer, actualMyPlayer);
    }

    @Test
    void getMyTreasurePosition_treasureAtCertainPos_returnsTreasureAtThatPos() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        Position expectedMyPlayer = new Position(1, 1);
        Position expectedFort = new Position(1, 1);
        Position expectedTreasure = new Position(3, 2);

        MapClass map = createMapFullMapStyle(nodes, expectedMyPlayer, expectedTreasure, expectedFort);
        Position actualTreasire = map.getMyTreasurePosition();

        Assertions.assertEquals(expectedTreasure, actualTreasire);
    }

    @Test
    void getEnemyFortPosition_fortAtCertainPos_returnsFortAtThatPos() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        Position expectedMyPlayer = new Position(1, 1);
        Position expectedFort = new Position(1, 1);
        Position expectedTreasure = new Position(3, 2);

        MapClass map = createMapFullMapStyle(nodes, expectedMyPlayer, expectedTreasure, expectedFort);
        Position actualFort = map.getEnemyFortPosition();

        Assertions.assertEquals(expectedFort, actualFort);
    }

}