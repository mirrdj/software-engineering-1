package server.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MapClassCombiner {
    private static MapNodeClass createNode(MapNodeClass mapNode, int x, int y, boolean isMyMap){
        EnumTerrain terrain = mapNode.getTerrain();
        EnumTreasureState treasureState = EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE;

        EnumPlayerPositionState playerPosition;
        EnumFortState fortState;
        if(mapNode.isFortPresent() && isMyMap){
            fortState = EnumFortState.MY_FORT_PRESENT;
            playerPosition = EnumPlayerPositionState.MY_POSITION;
        }
        else if(mapNode.getPlayerPosition() != EnumPlayerPositionState.ENEMY_PLAYER_POSITION){
            fortState = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;
            playerPosition = EnumPlayerPositionState.NO_PLAYER_PRESENT;
        }
        else {
            fortState = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;
            playerPosition = mapNode.getPlayerPosition();
        }

        return new MapNodeClass(x, y, terrain, fortState, playerPosition, treasureState);
    }

 private static MapClass combineVertically(MapClass myMap, MapClass enemyMap){
        MapClass result;
        int height = 8, width = 8;

        //enemyMap = placeEnemyPosition(enemyMap);

        Collection<MapNodeClass> nodeList = new ArrayList<>();

        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++){
                if(y < height / 2){
                    MapNodeClass mapNode = myMap.getNodeAtPosition(x, y).get();
                    nodeList.add(createNode(mapNode, x, y, true));
                }
                else {
                    MapNodeClass mapNode = enemyMap.getNodeAtPosition(x, y - height / 2).get();
                    nodeList.add(createNode(mapNode, x, y, false));
                }
            }

        result = new MapClass(nodeList);
        return result;
    }

    private static MapClass combineHorizontally(MapClass myMap, MapClass enemyMap){
        MapClass result;
        int width = 16, height = 4;

        //enemyMap = placeEnemyPosition(enemyMap);

        Collection<MapNodeClass> nodeList = new ArrayList<>();
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x < width / 2) {
                    MapNodeClass mapNode = myMap.getNodeAtPosition(x, y).get();
                    nodeList.add(createNode(mapNode, x, y, true));
                } else {
                    MapNodeClass mapNode = enemyMap.getNodeAtPosition(x - width / 2, y ).get();
                    nodeList.add(createNode(mapNode, x, y, false));
                }
            }
        }

        System.out.println("First half");
        System.out.println(myMap);
        System.out.println("Second half");
        System.out.println(enemyMap);
        result = new MapClass(nodeList);
        System.out.println(result);
        System.out.println("Size is " + nodeList.size());
        System.out.println(result);
        return result;
    }

    public static MapClass combineMaps(MapClass myMap, MapClass enemyMap) {
        double random = Math.random();
        if(random < 0.5){
            return combineHorizontally(myMap, enemyMap);
        }
        else {
            return combineVertically(myMap, enemyMap);
        }
    }

}
