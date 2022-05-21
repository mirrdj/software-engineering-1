package map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import helper.MapCreator;

class MapClassTest {
    private MapCreator mapCreator = new MapCreator();
    @Test
    public void getHeight_heightEqualValue_returnCorrectHeight_1() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M', 'G', 'W', 'G', 'M'},
                {'G', 'G', 'G', 'M', 'G', 'W', 'G', 'M'},
                {'G', 'G', 'G', 'M', 'G', 'W', 'G', 'M'},
                {'G', 'G', 'G', 'M', 'G', 'W', 'G', 'M'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int height = map.getHeight();

        Assertions.assertEquals(4, height);
    }

    @Test
    public void getHeight_heightEqualValue_returnCorrectHeight_2() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'W', 'W', 'G', 'G'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int height = map.getHeight();

        Assertions.assertEquals(5, height);
    }
    @Test
    public void getHeight_heightEqualValue_returnCorrectHeight_3() {
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

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int height = map.getHeight();

        Assertions.assertEquals(8, height);
    }

    @Test
    public void getWidth_widthEqualValue_returnCorrectWidth_1() {
        char[][] nodes = {
                {'G', 'G', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'G', 'W', 'G', 'M'},
                {'W', 'W', 'G', 'G'},
                {'W', 'W', 'G', 'G'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int width = map.getWidth();

        Assertions.assertEquals(4, width);
    }

    @Test
    public void getWidth_widthEqualValue_returnCorrectWidth_2() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
        };


        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int width = map.getWidth();

        Assertions.assertEquals(16, width);
    }

    @Test
    public void getWidth_widthEqualValue_returnCorrectWidth_3() {
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

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int width = map.getWidth();

        Assertions.assertEquals(8, width);
    }

    @Test
    public void getMountainNumber_noMountains_assertToZero() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int mountainNumber = map.getMountainNumber();

        Assertions.assertEquals(0, mountainNumber);
    }

    @Test
    public void getMountainNumber_multipleMountainFields_returnExpectedNumberOfMountains() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'M', 'M', 'G', 'M', 'G', 'M', 'G'},
                {'G', 'G', 'G', 'G', 'M', 'W', 'G', 'G'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int mountainNumber = map.getMountainNumber();

        Assertions.assertEquals(6, mountainNumber);
    }

    @Test
    public void getGrassNumber_noGrasss_assertToZero() {
        char[][] nodes = {
                {'W', 'W', 'M', 'W', 'M', 'M', 'M', 'M'},
                {'W', 'W', 'W', 'M', 'M', 'M', 'M', 'M'},
                {'W', 'W', 'M', 'W', 'M', 'W', 'M', 'M'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int grassNumber = map.getGrassNumber();

        Assertions.assertEquals(0, grassNumber);
    }

    @Test
    public void getGrassNumber_multipleGrassFields_returnExpectedNumberOfGrass() {
        char[][] nodes = {
                {'W', 'G', 'G', 'G', 'G', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'G', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'G', 'W', 'M', 'M'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int grassNumber = map.getGrassNumber();

        Assertions.assertEquals(12, grassNumber);
    }

    @Test
    public void getWaterNumber_noWater_assertToZero() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'M', 'M', 'M'},
                {'G', 'G', 'G', 'G', 'G', 'M', 'M', 'M'},
                {'G', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int waterNumber = map.getWaterNumber();

        Assertions.assertEquals(0, waterNumber);
    }

    @Test
    public void getWaterNumber_multipleWaterFields_returnExpectedNumberOfWater() {
        char[][] nodes = {
                {'W', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        MapClass map = mapCreator.createMapHalfMapStyle(nodes);
        int waterNumber = map.getWaterNumber();

        Assertions.assertEquals(5, waterNumber);
    }

    @Test
    public void getMyPosition_myPlayerAtCertainPos_returnsMyPlayerAtThatPos() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'W', 'M', 'G', 'M'},
                {'W', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        Position expectedMyPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(1, 1);
        Position enemyFort = new Position(6, 1);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, expectedMyPlayer, treasure, enemyFort, myFort);
        Position actualMyPlayer = map.getMyPosition();

        Assertions.assertEquals(expectedMyPlayer, actualMyPlayer);
    }

    @Test
    public void getMyTreasurePosition_treasureAtCertainPos_returnsTreasureAtThatPos() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'W', 'M', 'G', 'M'},
                {'W', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        Position myPlayer = new Position(1, 1);
        Position expectedTreasure = new Position(6, 1);
        Position myFort = new Position(1, 1);
        Position enemyFort = new Position(6, 1);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, expectedTreasure, enemyFort, myFort);
        Position actualTreasure = map.getMyTreasurePosition();

        Assertions.assertEquals(expectedTreasure, actualTreasure);
    }

    @Test
    public void getEnemyHalf_8by8MapMyFortInSecondHalf_returnFirstHalf(){
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

        Position myPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(6, 5);
        Position enemyFort = new Position(1, 1);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, treasure, enemyFort, myFort);
        List<Position> enemyHalfPositions = map.getEnemyHalf().getPositionList();

        List<Position> expectedPositions = new ArrayList<>();
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 8; j++)
                expectedPositions.add(new Position(j, i));

        Assertions.assertEquals(expectedPositions, enemyHalfPositions);
    }
    @Test
    public void getEnemyHalf_8by8MapMyFortInFirstHalf_returnSecondHalf(){
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

        Position myPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(6, 5);
        Position enemyFort = new Position(1, 1);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, treasure, enemyFort, myFort);
        List<Position> enemyHalfPositions = map.getEnemyHalf().getPositionList();

        List<Position> expectedPositions = new ArrayList<>();
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 8; j++)
                expectedPositions.add(new Position(j, i));

        Assertions.assertEquals(expectedPositions, enemyHalfPositions);
    }

    @Test
    public void getEnemyHalf_4by16MapMyFortInSecondHalf_returnFirstHalf(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        Position myPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(12, 3);
        Position enemyFort = new Position(1, 1);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, treasure, enemyFort, myFort);
        List<Position> enemyHalfPositions = map.getEnemyHalf().getPositionList();

        List<Position> expectedPositions = new ArrayList<>();
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 8; j++)
                expectedPositions.add(new Position(j, i));

        Assertions.assertEquals(expectedPositions, enemyHalfPositions);
    }
    @Test
    public void getEnemyHalf_4by16MapMyFortInFirstHalf_returnSecondHalf(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        Position myPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(1, 2);
        Position enemyFort = new Position(12, 3);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, treasure, enemyFort, myFort);
        List<Position> enemyHalfPositions = map.getEnemyHalf().getPositionList();

        List<Position> expectedPositions = new ArrayList<>();
        for(int i = 0; i < 4; i++)
            for(int j = 8; j < 16; j++)
                expectedPositions.add(new Position(j, i));

        Assertions.assertEquals(expectedPositions, enemyHalfPositions);
    }

    @Test
    public void getMyHalf_8by8MapMyFortInFirstHalf_returnFirstHalf(){
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

        Position myPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(1, 1);
        Position enemyFort = new Position(6, 5);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, treasure, enemyFort, myFort);
        List<Position> enemyHalfPositions = map.getMyHalf().getPositionList();

        List<Position> expectedPositions = new ArrayList<>();
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 8; j++)
                expectedPositions.add(new Position(j, i));

        Assertions.assertEquals(expectedPositions, enemyHalfPositions);
    }

    @Test
    public void getMyHalf_8by8MapMyFortInSecondHalf_returnSecondHalf(){
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

        Position myPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(6, 5);
        Position enemyFort = new Position(1, 1);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, treasure, enemyFort, myFort);
        List<Position> enemyHalfPositions = map.getMyHalf().getPositionList();

        List<Position> expectedPositions = new ArrayList<>();
        for(int i = 4; i < 8; i++)
            for(int j = 0; j < 8; j++)
                expectedPositions.add(new Position(j, i));

        Assertions.assertEquals(expectedPositions, enemyHalfPositions);
    }

    @Test
    public void getMyHalf_4by16MapMyFortInFirstHalf_returnFirstHalf(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        Position myPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(1, 2);
        Position enemyFort = new Position(12, 3);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, treasure, enemyFort, myFort);
        List<Position> enemyHalfPositions = map.getMyHalf().getPositionList();

        List<Position> expectedPositions = new ArrayList<>();
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 8; j++)
                expectedPositions.add(new Position(j, i));

        Assertions.assertEquals(expectedPositions, enemyHalfPositions);
    }

    @Test
    public void getMyHalf_4by16MapMyFortInSecondHalf_returnSecondHalf(){
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G'},
                {'G', 'G', 'M', 'M', 'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'M', 'G', 'G', 'G'},
                {'G', 'G', 'G', 'G', 'G', 'W', 'G', 'G', 'G', 'W', 'G', 'M', 'G', 'W', 'G', 'G'},
                {'G', 'W', 'G', 'G', 'M', 'W', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
        };

        Position myPlayer = new Position(1, 1);
        Position treasure = new Position(3, 2);
        Position myFort = new Position(12, 3);
        Position enemyFort = new Position(1, 2);

        MapClass map = mapCreator.createMapFullMapStyle(nodes, myPlayer, treasure, enemyFort, myFort);
        List<Position> enemyHalfPositions = map.getMyHalf().getPositionList();

        List<Position> expectedPositions = new ArrayList<>();
        for(int i = 0; i < 4; i++)
            for(int j = 8; j < 16; j++)
                expectedPositions.add(new Position(j, i));

        Assertions.assertEquals(expectedPositions, enemyHalfPositions);
    }

    @Test
    public void getEnemyFortPosition_fortAtCertainPos_returnsFortAtThatPos() {
        char[][] nodes = {
                {'G', 'G', 'G', 'G', 'W', 'M', 'M', 'M'},
                {'W', 'G', 'G', 'G', 'W', 'M', 'G', 'M'},
                {'W', 'G', 'G', 'G', 'M', 'G', 'M', 'M'}
        };

        Position expectedMyPlayer = new Position(1, 1);
        Position expectedTreasure = new Position(3, 2);
        Position expectedMyFort = new Position(1, 1);
        Position expectedEnemyFort = new Position(6, 1);


        MapClass map = mapCreator.createMapFullMapStyle(nodes, expectedMyPlayer, expectedTreasure, expectedEnemyFort, expectedMyFort);
        Position actualFort = map.getEnemyFortPosition();

        Assertions.assertEquals(expectedEnemyFort, actualFort);
    }

}