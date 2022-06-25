package server.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MapClassCombiner {
    private static MapNodeClass createNode(MapNodeClass mapNode, int x, int y){
        EnumTerrain terrain = mapNode.getTerrain();
        EnumPlayerPositionState playerPosition = mapNode.getPlayerPosition();
        EnumFortState fortState = mapNode.getFort();
        EnumTreasureState treasureState = mapNode.getTreasure();

        return new MapNodeClass(x, y, terrain, fortState, playerPosition, treasureState);
    }

    //TODO change this??
 private static MapClass combineVertically(MapClass firstMap, MapClass secondMap){
        int height = 8, width = 8;

        Collection<MapNodeClass> nodeList = new ArrayList<>();
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++){
                if(y < height / 2){
                    MapNodeClass mapNode = firstMap.getNodeAtPosition(x, y).get();
                    nodeList.add(createNode(mapNode, x, y));
                }
                else {
                    MapNodeClass mapNode = secondMap.getNodeAtPosition(x, y - height / 2).get();
                    nodeList.add(createNode(mapNode, x, y));
                }
            }

        return new MapClass(nodeList);
    }

    private static MapClass combineHorizontally(MapClass firstMap, MapClass secondMap){
        int width = 16, height = 4;

        Collection<MapNodeClass> nodeList = new ArrayList<>();
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x < width / 2) {
                    MapNodeClass mapNode = firstMap.getNodeAtPosition(x, y).get();
                    nodeList.add(createNode(mapNode, x, y));
                } else {
                    MapNodeClass mapNode = secondMap.getNodeAtPosition(x - width / 2, y ).get();
                    nodeList.add(createNode(mapNode, x, y));
                }
            }
        }

        return new MapClass(nodeList);
    }

    public static MapClass combineMaps(EnumLayout layout, MapClass myMap, MapClass enemyMap) {
        if(layout == EnumLayout.HORIZONTAL){
            return combineHorizontally(myMap, enemyMap);
        }
        else {
            return combineVertically(myMap, enemyMap);
        }
    }

}
