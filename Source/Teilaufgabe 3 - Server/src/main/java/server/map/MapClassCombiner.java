package server.map;

import java.util.ArrayList;
import java.util.Collection;

public class MapClassCombiner {
    private static MapNodeClass createNode(MapNodeClass mapNode, int x, int y){
        EnumTerrain terrain = mapNode.getTerrain();
        EnumTreasureState treasureState = EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE;


        EnumPlayerPositionState playerPosition;
        EnumFortState fortState;
        if(mapNode.isFortPresent()){
            fortState = EnumFortState.MY_FORT_PRESENT;
            playerPosition = EnumPlayerPositionState.MY_POSITION;
        }
        else {
            fortState = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;
            playerPosition = EnumPlayerPositionState.NO_PLAYER_PRESENT;
        }

        return new MapNodeClass(x, y, terrain, fortState, playerPosition, treasureState);
    }

    private static MapClass combineVertically(MapClass firstMap, MapClass secondMap){
        Collection<MapNodeClass> nodeList = new ArrayList<>();
        int height = 8, width = 8;
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++){
                if(y < height / 2){
                    MapNodeClass mapNode = firstMap.getNodeAtPosition(x, y).get();
                    nodeList.add(createNode(mapNode, x, y));
                }
                else if(y >= height / 2){
                    MapNodeClass mapNode = secondMap.getNodeAtPosition(x, y - height / 2).get();
                    nodeList.add(createNode(mapNode, x, y - height / 2));
                }
            }

        return new MapClass(nodeList);
    }

    private static MapClass combineHorizontally(MapClass firstMap, MapClass secondMap){
        Collection<MapNodeClass> nodeList = new ArrayList<>();

        int width = 16, height = 4;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x < width / 2) {
                    MapNodeClass mapNode = firstMap.getNodeAtPosition(x, y).get();
                    nodeList.add(createNode(mapNode, x, y));
                } else if (x >= width / 2) {
                    MapNodeClass mapNode = secondMap.getNodeAtPosition(x - width / 2, y ).get();
                    nodeList.add(createNode(mapNode, x - width / 2, y));
                }
            }
        }

        return new MapClass(nodeList);
    }

    public static MapClass combineMaps(MapClass firstMap, MapClass secondMap) {
        double random = Math.random();
        if(random < 0.5){
            return combineHorizontally(firstMap, secondMap);
        }
        else {
            return combineVertically(firstMap, secondMap);
        }
    }

}
