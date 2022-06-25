package server.map;

import server.exceptions.MapAlreadySetException;
import server.player.Player;
import server.uniqueID.PlayerID;

import java.util.*;
import java.util.stream.Collectors;

public class MapManager {
    Map<String, MapClass> maps = new HashMap<>();
    Optional<MapClass> fullMap = Optional.empty();

    public Map<String, MapClass> getMaps() {
        return maps;
    }

    public void addHalfMap(String playerID, MapClass halfMap){
        if(maps.containsKey(playerID)){
            throw new MapAlreadySetException("This player has already sent a map");
        }

        maps.put(playerID, halfMap);

        int maximumMapNumber = 2;
        if(maps.size() == maximumMapNumber){
            MapClass firstMap = (MapClass) maps.values().toArray()[0];
            MapClass secondMap = (MapClass) maps.values().toArray()[1];

            fullMap = Optional.of(MapClassCombiner.combineMaps(firstMap, secondMap));
            fullMap = Optional.of(placeEnemyPosition(fullMap.get()));
        }
    }

    private MapClass placeEnemyPosition(MapClass fullMap) {
        EnumFortState fortState = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;
        EnumTreasureState treasureState = EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE;
        EnumPlayerPositionState enemyPosition = EnumPlayerPositionState.ENEMY_PLAYER_POSITION;
        EnumTerrain desiredTerrain = EnumTerrain.GRASS;

        int width = 8, height = 4;
        int xCoordiate, yCoordinate;
        boolean fortPresent, isGrass;
        do{
            xCoordiate = (int)(Math.random() * width);
            yCoordinate = (int)(Math.random() * height);

            fortPresent = fullMap.getNodeAtPosition(xCoordiate, yCoordinate).get().isFortPresent();
            isGrass = fullMap.getNodeAtPosition(xCoordiate, yCoordinate).get().getTerrain() == EnumTerrain.GRASS;
        } while(fortPresent || !isGrass);

        MapNodeClass enemyPositionNode = new MapNodeClass(xCoordiate, yCoordinate, desiredTerrain, fortState, enemyPosition, treasureState);

        final int finalX = xCoordiate;
        final int finalY = yCoordinate;
        ArrayList<MapNodeClass> nodeList = (ArrayList<MapNodeClass>) fullMap
                .getNodes()
                .stream()
                .filter(eachNode-> !(eachNode.getX() == finalX && eachNode.getY() == finalY))
                .collect(Collectors.toList());
        nodeList.add(enemyPositionNode);

        return new MapClass(nodeList);
    }

    private Map<String, MapClass> getMapOfOtherPlayers(String playerID){
        return maps
                .entrySet()
                .stream()
                .filter(stringPlayerEntry -> !stringPlayerEntry.getKey().equals(playerID))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Optional<MapClass> getFullMap(){
        if(fullMap.isPresent())
            return fullMap;
        else
            return Optional.empty();
    }


}
