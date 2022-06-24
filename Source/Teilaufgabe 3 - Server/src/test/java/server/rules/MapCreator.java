package server.rules;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;
import MessagesBase.UniquePlayerIdentifier;

import java.util.ArrayList;
import java.util.List;

public class MapCreator {
    public HalfMap createHalfMap(char[][] nodes, String playerID) {
        boolean fortressPlaced = false;
        List<HalfMapNode> nodeList = new ArrayList<>();

        for(int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                ETerrain terrain;
                boolean fortPresent = false;

                switch (nodes[i][j]) {
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
                nodeList.add(new HalfMapNode(i, j, fortPresent, terrain));
            }
        }

        return new HalfMap(playerID, nodeList);
    }
}
