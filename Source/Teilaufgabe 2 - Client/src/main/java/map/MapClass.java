package map;

import exceptions.NotFullMapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class MapClass {
    private static final Logger logger = LoggerFactory.getLogger(MapClass.class);
    private final Collection<MapNodeClass> nodes;

    public MapClass(Collection<MapNodeClass> nodes){
        this.nodes = nodes;
    }
    public Collection<MapNodeClass> getNodes() {
        return nodes;
    }

    public int getHeight(){
        MapNodeClass node = nodes
                .stream()
                .max(Comparator.comparing(MapNodeClass::getY))
                .orElse(null);

        if(node != null)
            return node.getY() + 1;

        return -1;
    }

    public int getWidth(){
        MapNodeClass node = nodes
                .stream()
                .max(Comparator.comparing(MapNodeClass::getX))
                .orElse(null);

        if(node != null)
            return node.getX() + 1;

        return -1;
    }

    public EnumLayout getLayout(){
        if(!(getHeight() == 8 && getWidth() == 8) && !(getHeight() == 4 && getWidth() == 16))
            throw new NotFullMapException("The map is not a full map so there is no defined layout");

        if(getHeight() == 8 && getWidth() == 8)
            return EnumLayout.VERTICAL;
        else
            return EnumLayout.HORIZONTAL;
    }

    public MapClass getFirstHalf() {
        if(!(getHeight() == 8 && getWidth() == 8) && !(getHeight() == 4 && getWidth() == 16))
            throw new NotFullMapException("The map is not a full map so there is no first half map");

        List<MapNodeClass> firstHalf = new ArrayList<>();
        if (getHeight() == 8 && getWidth() == 8) {
            for (MapNodeClass node : nodes) {
                if (node.getX() < 4) {
                    firstHalf.add(node);
                }
            }

        } else if (getHeight() == 4 && getWidth() == 16) {
            for (MapNodeClass node : nodes) {
                if (node.getY() < 4) {
                    firstHalf.add(node);
                }
            }
        }

        return new MapClass(firstHalf);
    }

    public MapClass  getSecondHalf(){
        if(!(getHeight() == 8 && getWidth() == 8) && !(getHeight() == 4 && getWidth() == 16))
            throw new NotFullMapException("The map is not a full map so there is no first half map");

        List<MapNodeClass> secondHalf = new ArrayList<>();
        if (getHeight() == 8 && getWidth() == 8) {
            for (MapNodeClass node : nodes) {
                if (node.getX() >= 8) {
                    secondHalf.add(node);
                }
            }

        } else {
            for (MapNodeClass node : nodes) {
                if (node.getY() >= 4) {
                    secondHalf.add(node);
                }
            }
        }

        return new MapClass(secondHalf);
    }

    public HashMap<Position, EnumTerrain> getTerrainNodes(){
        HashMap<Position, EnumTerrain> terrainNodes = new HashMap<>();
        getNodes()
                .stream()
                .forEach(n -> terrainNodes.put(
                        new Position(n.getX(), n.getY()), n.getTerrain()
                ));

        return terrainNodes;
    }


    public MapNodeClass getNodeAtPosition(int x, int y){
        return getNodes()
                 .stream()
                 .filter(n -> n.getX() == x)
                 .filter(n -> n.getY() == y)
                 .findFirst()
                 .orElse(null);
    }

    public Position getMyPosition(){
        MapNodeClass node = getNodes()
                .stream()
                .filter(n -> n.getPlayerPosition() == EnumPlayerPositionState.MY_POSITION ||
                        n.getPlayerPosition() == EnumPlayerPositionState.BOTH_PLAYER_POSITION)
                .findFirst()
                .orElse(null);

        if(node != null)
            return new Position(node.getX(), node.getY());

        return null;
    }
    public List<Position> getPositionList(){
        return nodes
                .stream()
                .map(n -> new Position(n.getX(), n.getY()))
                .collect(Collectors.toList());
    }

    public Position getMyTreasurePosition(){
        MapNodeClass node = getNodes()
                .stream()
                .filter(n -> n.getTreasure().equals(EnumTreasureState.MY_TREASURE_IS_PRESENT))
                .findFirst()
                .orElse(null);

        if(node != null)
            return new Position(node.getX(), node.getY());

        return null;
    }

    public int getMountainNumber(){
        return (int) nodes
                .stream()
                .filter(n -> n.getTerrain() == EnumTerrain.MOUNTAIN)
                .count();
    }

    public int getWaterNumber(){
        return (int) nodes
                .stream()
                .filter(n -> n.getTerrain() == EnumTerrain.WATER)
                .count();
    }

    public int getGrassNumber(){
        return (int) nodes
                .stream()
                .filter(n -> n.getTerrain() == EnumTerrain.GRASS)
                .count();
    }

    public Position getEnemyFortPosition(){
        MapNodeClass node = nodes
                .stream()
                .filter(n -> n.getFort() == EnumFortState.ENEMY_FORT_PRESENT)
                .findFirst()
                .orElse(null);

        if(node != null)
            return new Position(node.getX(), node.getY());
        return null;
    }


    @Override
    public String toString() {
        String string = "\n";

        int height = getHeight();
        int width = getWidth();

        logger.debug("height is " + height);
        logger.debug("width is " + width);
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                string += getNodeAtPosition(x, y) + "   ";
            }
            string += '\n';
        }

        return string;
    }
}
