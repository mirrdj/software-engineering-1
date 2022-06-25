package server.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.exceptions.MapAlreadySetException;

import java.util.*;
import java.util.stream.Collectors;

public class MapManager {
    private static Logger logger = LoggerFactory.getLogger(MapManager.class);
    Map<String, MapClass> maps = new HashMap<>();
    Map<String, Boolean> treasureFound = new HashMap<>();
    Map<String, Boolean> enemyFortFound = new HashMap<>();
    Optional<EnumLayout> layout = Optional.empty();


    public Map<String, MapClass> getMaps() {
        return maps;
    }

    public void addHalfMap(String playerID, MapClass halfMap){
        if(maps.containsKey(playerID)){
            throw new MapAlreadySetException("This player has already sent a map");
        }

        addTreasure(halfMap);
        maps.put(playerID, halfMap);
    }

    private MapNodeClass findRandomGrassNode(MapClass map, int width, int height){
        int xCoord, yCoord;
        boolean isGrass = false, fortPresent = false;
        do{
            xCoord = (int)(Math.random() * width);
            yCoord = (int)(Math.random() * height);

            if(map.getNodeAtPosition(xCoord, yCoord).isPresent()) {
                fortPresent = map.getNodeAtPosition(xCoord, yCoord).get().isFortPresent();
                isGrass = map.getNodeAtPosition(xCoord, yCoord).get().getTerrain() == EnumTerrain.GRASS;
            }
        } while(!isGrass && !fortPresent);

        return map.getNodeAtPosition(xCoord, yCoord).get();
    }

    private MapClass addEnemyPosition(MapClass map){
        int width = 8, height = 4;
        MapNodeClass grassNode = findRandomGrassNode(map, width, height);
        map.getNodeAtPosition(grassNode.getX(), grassNode.getY())
                .get()
                .setPlayerPosition(EnumPlayerPositionState.ENEMY_PLAYER_POSITION);

        return map;
    }

    private MapClass addTreasure(MapClass map){
        int width = 8, height = 4;
        MapNodeClass grassNode = findRandomGrassNode(map, width, height);

        map.getNodeAtPosition(grassNode.getX(), grassNode.getY())
                .get()
                .setTreasure(EnumTreasureState.MY_TREASURE_IS_PRESENT);

        return map;
    }

    private void filterPosition(MapClass map, EnumPlayerPositionState playerPositionToSet, int minX, int maxX, int minY, int maxY){
        Optional<MapNodeClass> nodeWithPosition = map
                .getNodes()
                .stream()
                .filter(eachNode -> eachNode.getX() >= minX && eachNode.getX() < maxX)
                .filter(eachNode -> eachNode.getY() >= minY && eachNode.getY() < maxY)
                .filter(eachNode -> eachNode.getPlayerPosition() == EnumPlayerPositionState.MY_POSITION)
                .findFirst();

        map
                .getNodeAtPosition(nodeWithPosition.get().getX(), nodeWithPosition.get().getY()).get()
                .setPlayerPosition(playerPositionToSet);
    }

    private void filterTreasure(MapClass map, int minX, int maxX, int minY, int maxY){
        Optional<MapNodeClass> nodeWithTreasure = map
                .getNodes()
                .stream()
                .filter(eachNode -> eachNode.getX() >= minX && eachNode.getX() < maxX)
                .filter(eachNode -> eachNode.getY() >= minY && eachNode.getY() < maxY)
                .filter(eachNode -> eachNode.getTreasure() == EnumTreasureState.MY_TREASURE_IS_PRESENT)
                .findFirst();

        map
                .getNodeAtPosition(nodeWithTreasure.get().getX(), nodeWithTreasure.get().getY()).get()
                .setTreasure(EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE);
    }

    private void filterFort(MapClass map, int minX, int maxX, int minY, int maxY){
        Optional<MapNodeClass> nodeWithFort = map
                .getNodes()
                .stream()
                .filter(eachNode -> eachNode.getX() >= minX && eachNode.getX() < maxX)
                .filter(eachNode -> eachNode.getY() >= minY && eachNode.getY() < maxY)
                .filter(eachNode -> eachNode.getFort() == EnumFortState.MY_FORT_PRESENT)
                .findFirst();

        map
                .getNodeAtPosition(nodeWithFort.get().getX(), nodeWithFort.get().getY()).get()
                .setFort(EnumFortState.NO_OR_UNKNOWN_FORT_STATE);
    }

    private EnumLayout calculateLayout(){
        double random = Math.random();
        if(random < 0.5){
            return EnumLayout.HORIZONTAL;
        }
        else {
            return EnumLayout.VERTICAL;
        }
    }

    public Optional<MapClass> getFullMap(String requesterID, boolean firstRounds){
        if(maps.size() != 2)
            return Optional.empty();

        if(layout.isEmpty())
            layout = Optional.of(calculateLayout());

        MapClass firstMap = (MapClass) maps.values().toArray()[0];
        MapClass secondMap = (MapClass) maps.values().toArray()[1];

        MapClass fullMapCombined = MapClassCombiner.combineMaps(layout.get(), firstMap, secondMap);

        // Check if the requester sent the first half map -> second half is enemy half
        if(maps.keySet().iterator().next().equals(requesterID)){
            if(layout.get() == EnumLayout.HORIZONTAL) {
                if(firstRounds){
                    filterPosition(fullMapCombined, EnumPlayerPositionState.NO_PLAYER_PRESENT, 8, 16, 0, 4);
                    addEnemyPosition(fullMapCombined);
                }
                else{
                     filterPosition(fullMapCombined, EnumPlayerPositionState.ENEMY_PLAYER_POSITION, 8, 16, 0, 4);
                }

                if(!enemyFortFound.containsKey(requesterID))
                    filterFort(fullMapCombined, 8, 16, 0, 4);
                if(!treasureFound.containsKey(requesterID))
                    filterTreasure(fullMapCombined, 0, 8, 0, 4);

                filterTreasure(fullMapCombined, 8, 16, 0, 4);
            }
            else {
                if(firstRounds){
                    filterPosition(fullMapCombined, EnumPlayerPositionState.NO_PLAYER_PRESENT, 0, 8, 4, 8);
                    addEnemyPosition(fullMapCombined);
                }
                else{
                     filterPosition(fullMapCombined, EnumPlayerPositionState.ENEMY_PLAYER_POSITION, 0, 8, 4, 8);
                }

                if(!enemyFortFound.containsKey(requesterID))
                    filterFort(fullMapCombined, 0, 8, 4, 8);
                if(!treasureFound.containsKey(requesterID))
                    filterTreasure(fullMapCombined, 0, 8, 0, 4);

                filterTreasure(fullMapCombined, 0, 8, 0, 8);
            }

            return Optional.of(fullMapCombined);
        }
        // Requester sent the second half -> first half is enemy half
        else{
            if(firstRounds){
                filterPosition(fullMapCombined, EnumPlayerPositionState.NO_PLAYER_PRESENT, 0, 8, 0, 4);
                addEnemyPosition(fullMapCombined);
            }
            filterFort(fullMapCombined, 0, 8, 0, 4);
            if(layout.get() == EnumLayout.HORIZONTAL) {
                filterTreasure(fullMapCombined, 0, 16, 0, 4);
                filterTreasure(fullMapCombined, 0, 16, 0, 4);
            }
            else {
                filterTreasure(fullMapCombined, 0, 8, 0, 8);
                filterTreasure(fullMapCombined, 0, 8, 0, 8);
            }
            return Optional.of(fullMapCombined);
        }
    }


}
