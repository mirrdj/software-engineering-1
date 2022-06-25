package server.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.exceptions.MapAlreadySetException;

import java.util.*;

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

    private void addRandomEnemyPosition(MapClass map){
        int width = 8, height = 4;
        MapNodeClass grassNode = findRandomGrassNode(map, width, height);
        map.getNodeAtPosition(grassNode.getX(), grassNode.getY())
                .get()
                .setPlayerPosition(EnumPlayerPositionState.ENEMY_PLAYER_POSITION);
    }

    private void addTreasure(MapClass map){
        int width = 8, height = 4;
        MapNodeClass grassNode = findRandomGrassNode(map, width, height);

        map.getNodeAtPosition(grassNode.getX(), grassNode.getY())
                .get()
                .setTreasure(EnumTreasureState.MY_TREASURE_IS_PRESENT);
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

    // emMinX, emMaxX, emMinY, emMaxY - the coordinates of the enemy (non-requester) half
    // mmMinX, mmMaxX, mmMinY, mmMaxY - the coordinates of my own (requester) half
    private void filterMap(MapClass map, String requesterID, boolean firstRounds, int emMinX, int emMaxX, int emMinY, int emMaxY,
                           int mmMinX, int mmMaxX, int mmMinY, int mmMaxY){

        // If these are the first 10 rounds, remove the MY_PLAYER_PRESENT from the non-requester half
        // and add ENEMY_PLAYER_POSITION on random grass field
        if(firstRounds){
            filterPosition(map, EnumPlayerPositionState.NO_PLAYER_PRESENT, emMinX, emMaxX, emMinY, emMaxY);
            addRandomEnemyPosition(map);
        }
        // If these are not the first 10 rounds, remove the MY_PLAYER_PRESENT from the non-requester half
        // and replace with the enemy position
        else{
            filterPosition(map, EnumPlayerPositionState.ENEMY_PLAYER_POSITION, emMinX, emMaxX, emMinY, emMaxY);
        }

        // If the requester has not yet found the enemy fort
        // remove the fort information from the non-requester half
        if(!enemyFortFound.containsKey(requesterID))
            filterFort(map,  emMinX, emMaxX, emMinY, emMaxY);

        // If the requester has not yet found the treasure fort
        // remove the fort information from the requester half
        if(!treasureFound.containsKey(requesterID))
            filterTreasure(map, mmMinX, mmMaxX, mmMinY, mmMaxY);

        // Remove treasure information from non-requester half
        filterTreasure(map, emMinX, emMaxX, emMinY, emMaxY);
    }

    public Optional<MapClass> getFullMap(String requesterID, boolean firstRounds){
        // There are not two save half maps yet
        if(maps.size() != 2)
            return Optional.empty();

        // The layout of the map has not been decided yet
        if(layout.isEmpty())
            layout = Optional.of(calculateLayout());

        MapClass firstMap = (MapClass) maps.values().toArray()[0];
        MapClass secondMap = (MapClass) maps.values().toArray()[1];

        MapClass fullMapCombined = MapClassCombiner.combineMaps(layout.get(), firstMap, secondMap);

        int emMinX, emMaxX, emMinY, emMaxY, mmMinX, mmMaxX, mmMinY, mmMaxY;
        // Check if the requester sent the first half map -> second half is enemy half
        if(maps.keySet().iterator().next().equals(requesterID)){
            if(layout.get() == EnumLayout.HORIZONTAL) {
                 emMinX = 8; emMaxX = 16; emMinY = 0; emMaxY = 4;
                 mmMinX = 0; mmMaxX =  8; mmMinY = 0; mmMaxY = 4;
            }
            else {
                 emMinX = 0; emMaxX = 8; emMinY = 4; emMaxY = 8;
                 mmMinX = 0; mmMaxX = 8; mmMinY = 0; mmMaxY = 4;
            }

        }
        // Requester sent the second half -> first half is enemy half
        else{
            if(layout.get() == EnumLayout.HORIZONTAL) {
                 emMinX = 0; emMaxX =  8; emMinY = 0; emMaxY = 4;
                 mmMinX = 8; mmMaxX = 16; mmMinY = 0; mmMaxY = 4;
            }
            else {
                 emMinX = 0; emMaxX = 8; emMinY = 0; emMaxY = 4;
                 mmMinX = 0; mmMaxX = 8; mmMinY = 4; mmMaxY = 8;
            }
        }

        // Remove information that should be hidden from the client
        filterMap(fullMapCombined, requesterID, firstRounds,
                emMinX, emMaxX, emMinY, emMaxY,
                mmMinX, mmMaxX, mmMinY, mmMaxY);

        return Optional.of(fullMapCombined);
    }


}
