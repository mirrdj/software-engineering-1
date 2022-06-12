package server.network;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.map.MapClass;
import server.map.MapNodeClass;
import server.map.EnumTerrain;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;



import java.util.ArrayList;
import java.util.List;

public class NetworkConverter {
    // convert from my own GameID to UniqueGameIdentifier
    public UniqueGameIdentifier convertGameID(GameID gameID){
        String id = gameID.getID();
        return new UniqueGameIdentifier(id);
    }

    // convert from UniqueGameIdentifier to my own GameID
    public GameID convertUniqueGameIdentifier(UniqueGameIdentifier gameIdentifier){
        String id = gameIdentifier.getUniqueGameID();
        return new GameID(id);
    }

    // convert from my own PlayerID to UniquePlayerIdentifier
    public UniquePlayerIdentifier convertPlayerID(PlayerID playerID){
        String id = playerID.getID();
        return new UniquePlayerIdentifier(id);
    }

    // convert from the network ETerrain to my own EnumTerrain
    private EnumTerrain convertETerrain(ETerrain terrain) {
        return switch (terrain) {
            case Water -> EnumTerrain.WATER;
            case Grass -> EnumTerrain.GRASS;
            case Mountain -> EnumTerrain.MOUNTAIN;
        };

    }

    // convert from the network HalfMapNode class to my own MapNodeClass
    private MapNodeClass convertHalfMapNode(HalfMapNode halfMapNode) {
        int x = halfMapNode.getX();
        int y = halfMapNode.getY();

        boolean fort = halfMapNode.isFortPresent();
        EnumTerrain terrain = convertETerrain(halfMapNode.getTerrain());

        return new MapNodeClass(x, y, terrain, fort);
    }

    public MapClass convertHalfMap(HalfMap halfMap) {
        List<HalfMapNode> networkNodes = new ArrayList<>(halfMap.getMapNodes());
        List<MapNodeClass> classNodes = new ArrayList<>();

        for(HalfMapNode fmNode : networkNodes)
            classNodes.add(convertHalfMapNode(fmNode));

        return new MapClass(classNodes);
    }

}
