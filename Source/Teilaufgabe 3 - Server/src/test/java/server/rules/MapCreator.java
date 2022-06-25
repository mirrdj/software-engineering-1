package server.rules;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;

import java.util.ArrayList;
import java.util.List;

public class MapCreator {
    public HalfMap createMap(char[][] nodes) {
        boolean fortressPlaced = false;
        List<HalfMapNode> nodeList = new ArrayList<>();

        for(int y = 0; y < nodes.length; y++) {
            for (int x = 0; x < nodes[0].length; x++) {
                ETerrain terrain;
                boolean fortPresent = false;

                switch (nodes[y][x]) {
                    case 'W' -> terrain = ETerrain.Water;
                    case 'M' -> terrain = ETerrain.Mountain;
                    default -> {
                        terrain = ETerrain.Grass;
                        if (!fortressPlaced) {
                            fortressPlaced = true;
                            fortPresent = true;
                        }
                    }
                }
                nodeList.add(new HalfMapNode(x, y, fortPresent, terrain));
            }
        }

        String playerID = "id0123456789";
        return new HalfMap(playerID, nodeList);
    }

    public HalfMap createMap(int minY, int height, int minX, int width){
        List<HalfMapNode> nodeList = new ArrayList<>();

        for(int x = minX; x < width; x++) {
            for (int y = minY; y < height; y++) {
                nodeList.add(new HalfMapNode(x, y, ETerrain.Grass));
            }
        }

        String playerID = "id0123456789";
        return new HalfMap(playerID, nodeList);
    }

    public HalfMap createMap(int height, int width, boolean placeFort){
        List<HalfMapNode> nodeList = new ArrayList<>();

        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodeList.add(new HalfMapNode(x, y, placeFort, ETerrain.Grass));
            }
        }

        String playerID = "id0123456789";
        return new HalfMap(playerID, nodeList);
    }
}
