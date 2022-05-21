package helper;

import map.*;

import java.util.ArrayList;
import java.util.List;

public class MapCreator {
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

    public MapClass createMapFullMapStyle(char[][] nodes, Position myPlayerPosition, Position myTreasure, Position enemyFort, Position myFort){
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
                else if(currPosition.getX() == myFort.getX() && currPosition.getY() == myFort.getY())
                    fort = EnumFortState.MY_FORT_PRESENT;
                else
                    fort = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;

                nodeList.add(new MapNodeClass(x, y, terrain, fort, player, treasure));
            }
        }

        return new MapClass(nodeList);
    }

}
