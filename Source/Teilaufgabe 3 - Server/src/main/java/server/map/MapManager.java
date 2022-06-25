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
    // Optional<MapClass> fullMap = Optional.empty();


    public Map<String, MapClass> getMaps() {
        return maps;
    }

    public void addHalfMap(String playerID, MapClass halfMap){
        if(maps.containsKey(playerID)){
            throw new MapAlreadySetException("This player has already sent a map");
        }

        addTreasure(halfMap);
        maps.put(playerID, halfMap);

        int maximumMapNumber = 2;//TODO add exception??
    }

    private Map<String, MapClass> getMapOfOtherPlayers(String playerID){
        return maps
                .entrySet()
                .stream()
                .filter(stringPlayerEntry -> !stringPlayerEntry.getKey().equals(playerID))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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


//        List<MapNodeClass> filteredNodes = map
//                .getNodes()
//                .stream()
//                .filter(eachNode -> eachNode.getPlayerPosition() != playerPosition)
//                .collect(Collectors.toList());
//        logger.debug("There are {} filtered nodes", filteredNodes.size());

//        if(nodeWithPosition.isPresent()){
//            nodeWithPosition.get().setPlayerPosition(EnumPlayerPositionState.MY_POSITION);
//            filteredNodes.add(nodeWithPosition.get());
//        }
//        return new MapClass(filteredNodes);
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

//        List<MapNodeClass> filteredNodes = map
//                .getNodes()
//                .stream()
//                .filter(eachNode -> eachNode.getTreasure() != EnumTreasureState.MY_TREASURE_IS_PRESENT)
//                .collect(Collectors.toList());
//        logger.debug("There are {} filtered nodes", filteredNodes.size());
//
//        nodeWithTreasure.setTreasure(EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE);
//        filteredNodes.add(nodeWithTreasure);
//        return new MapClass(filteredNodes);
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

        /*List<MapNodeClass> filteredNodes = map
                .getNodes()
                .stream()
                .filter(eachNode -> eachNode.getFort() != EnumFortState.MY_FORT_PRESENT )
                .collect(Collectors.toList());
        logger.debug("There are {} filtered nodes", filteredNodes.size());

        if(nodeWithFort.isPresent()){
            nodeWithFort.get().setFort(EnumFortState.NO_OR_UNKNOWN_FORT_STATE);
            filteredNodes.add(nodeWithFort.get());
        }*/

//        return new MapClass(filteredNodes);
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

        // Check if the requester sent the first half map -> second half is enemy half
        if(maps.keySet().iterator().next().equals(requesterID)){
            System.out.println("player sent first half");

            MapClass fullMapCombined = MapClassCombiner.combineMaps(layout.get(), firstMap, secondMap);

            if(layout.get() == EnumLayout.HORIZONTAL) {
                if(firstRounds){
                    System.out.println("Before filtering myPos");
                    System.out.println(fullMapCombined);
                    filterPosition(fullMapCombined, EnumPlayerPositionState.NO_PLAYER_PRESENT, 8, 16, 0, 4);

                    System.out.println("After filtering myPos");
                    System.out.println(fullMapCombined);

                    addEnemyPosition(fullMapCombined);

                    System.out.println("After adding enemy");
                    System.out.println(fullMapCombined);
                }

                System.out.println("Layout horizontal, seraching in the second half");
                filterFort(fullMapCombined, 8, 16, 0, 4);
                filterTreasure(fullMapCombined, 0, 16, 0, 4);
                filterTreasure(fullMapCombined, 0, 16, 0, 4);
            }
            else {
                if(firstRounds){
                    filterPosition(fullMapCombined, EnumPlayerPositionState.NO_PLAYER_PRESENT, 0, 8, 4, 8);
                    addEnemyPosition(fullMapCombined);
                }
                System.out.println("Layout vertical, seraching in the second half");
                filterFort(fullMapCombined, 0, 8, 4, 8);
                filterTreasure(fullMapCombined, 0, 8, 0, 8);
                filterTreasure(fullMapCombined, 0, 8, 0, 8);

            }

            System.out.println("after");
            System.out.println(fullMapCombined);
            return Optional.of(fullMapCombined);
        }
        // Requester sent the second half -> first half is enemy half
        else{
            MapClass fullMapCombined =  MapClassCombiner.combineMaps(layout.get(), firstMap, secondMap);

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
