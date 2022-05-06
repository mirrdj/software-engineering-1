package map.half_map;

import MessagesBase.MessagesFromClient.HalfMap;
import map.EnumTerrain;
import map.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class HalfMapValidator {
    private static Logger logger = LoggerFactory.getLogger(HalfMapValidator.class);
    private final int height = 4;
    private final int width = 8;

    private final int minMountains = 3;     // minimum number of mountain fields on half map
    private final int minGrass = 15;        // minimum number of grass fields on half map
    private final int minWater = 4;         // minimum number of water fields on half map

    private final int maxLSide = 3;        // maximum number of water fields on the long sides of the half map
    private final int maxSShortSide = 1;  // maximum number of water fields on the short sides of the half map

    public boolean mapIsValid(HalfMapClass halfMap){
        if(!checkNumberOfFields(halfMap))
            return false;

        if(!checkSides(halfMap))
            return false;

        if(!checkFortress(halfMap))
            return false;

        if(!checkSize(halfMap))
            return false;

        if(!checkIsland(halfMap))
            return false;

        return true;

    }

    private boolean checkSize(HalfMapClass halfMap){
        logger.info("Checking the size of the map");
        if(halfMap.getHeight() != height) {
            logger.warn("Height is " + halfMap.getHeight());
            logger.warn("should be " + height);
            return false;
        }
        if(halfMap.getWidth() != width) {
            logger.warn("Width is " + halfMap.getWidth());
            logger.warn("should be " + width);
            return false;
        }

        logger.info("Size of the map checked succesfully");
        return true;
    }

    private boolean checkFortress(HalfMapClass halfMap){
        logger.info("Checking the fortress");
        long fortressNumber = halfMap
                .getNodes()
                .stream()
                .filter(n -> n.isFortPresent())
                .filter(n -> n.getTerrain() == EnumTerrain.GRASS)
                .count();

        if(fortressNumber != 1) {
            logger.warn("The number of fortresses is " + fortressNumber);
            logger.warn("The number of fortresses should be 1");
            return false;
        }

        logger.info("Fortress checked succesfully");
        return true;
    }

    private boolean checkSides(HalfMapClass halfMap){
        logger.info("Checking the number of water fields on the sides");

        if(!longLineCheck(halfMap,0))
            return false;
        if(!longLineCheck(halfMap,3))
            return false;
        if(!shortLineCheck(halfMap, 0))
            return false;
        if(!shortLineCheck(halfMap,7))
            return false;

        logger.info("Sides checked succesfully");
        return true;
    }

    private boolean checkNumberOfFields(HalfMapClass halfMap){
        logger.info("Checking if there are enough fields of each type");

        int grassNumber = halfMap.getGrassNumber();
        if(grassNumber < minGrass){
            logger.warn("The number of grass fields is " + grassNumber);
            logger.warn("It should be at least " + minGrass);
            return false;
        }

        int mountainNumber = halfMap.getMountainNumber();
        if(mountainNumber < minMountains){
            logger.warn("The number of mountain fields is " + mountainNumber);
            logger.warn("It should be at least " + minMountains);
            return false;
        }

        int waterNumber = halfMap.getWaterNumber();
        if(waterNumber < minWater){
            logger.warn("The number of water fields is " + waterNumber);
            logger.warn("It should be at least " + minWater);
            return false;
        }

        logger.info("Fields checked succesfully");
        return true;
    }

    private boolean shortLineCheck(HalfMapClass halfMap, int x){
        long waterNumber = halfMap
                .getNodes()
                .stream()
                .filter(n -> n.getX() == x)
                .filter(n -> n.getTerrain() == EnumTerrain.WATER)
                .count();

        if(waterNumber > maxSShortSide){
            logger.warn("Too many water fields " + waterNumber + " on side " + x);
            return false;
        }

        return true;
    }

    private boolean longLineCheck(HalfMapClass halfMap, int y){
        long waterNumber = halfMap
                .getNodes()
                .stream()
                .filter(n -> n.getY() == y)
                .filter(n -> n.getTerrain() == EnumTerrain.WATER)
                .count();

        if(waterNumber > maxLSide){
            logger.warn("Too many water fields " + waterNumber + " on side " + y);
            return false;
        }

        return true;
    }

    private void floodFill(int x, int y, HashMap<Position, EnumTerrain> terrainNodes) {
        Position pos = new Position(x, y);
        if(!terrainNodes.containsKey(pos) || terrainNodes.get(pos).equals(EnumTerrain.WATER))
            return;
        logger.debug(pos + " gets removed");
        terrainNodes.remove(pos);

        if(x > 0)
            floodFill(x - 1, y, terrainNodes);

        if(x  < 7)
            floodFill(x + 1, y, terrainNodes);

        if(y > 0)
            floodFill(x, y - 1, terrainNodes);

        if(y < 3)
            floodFill(x, y + 1, terrainNodes);

    }

    private boolean checkIsland(HalfMapClass halfMap){
        HashMap<Position, EnumTerrain> terrainNodes = halfMap.getTerrainNodes();
        floodFill(0,0, terrainNodes);

        return terrainNodes.size() <= halfMap.getWaterNumber() + 4;
    }

}
