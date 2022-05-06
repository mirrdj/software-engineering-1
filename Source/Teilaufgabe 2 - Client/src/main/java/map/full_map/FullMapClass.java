package map.full_map;

import map.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class FullMapClass {
    private static final Logger logger = LoggerFactory.getLogger(FullMapClass.class);
    private Collection<MapNodeClass> nodes;

    public FullMapClass(Collection<MapNodeClass> nodes){
        this.nodes = nodes;
    }
    public Collection<MapNodeClass> getNodes() {
        return nodes;
    }

    public List<MapNodeClass> getNodesAsList(){
        List<MapNodeClass> list = new ArrayList<>(getNodes());
        return list;
    }

    public int getHeight(){
        int height = getNodes()
                .stream()
                .max(Comparator.comparing(MapNodeClass::getY))
                .get()
                .getY();
        height += 1;

        return height;
    }
    public int getWidth(){
        int width = getNodes()
                .stream()
                .max(Comparator.comparing(MapNodeClass::getX))
                .get()
                .getX();
        width += 1;

        return width;
    }

    public EnumLayout getLayout(){
        if(getHeight() == 8 && getWidth() == 8)
            return EnumLayout.VERTICAL;
        if(getHeight() == 4 && getWidth() == 16)
            return EnumLayout.HORIZONTAL;

        return null;
    }

    public HashMap<Position, EnumTerrain> getTerrainNodes(){
        HashMap<Position, EnumTerrain> map = new HashMap<>();
        getNodes()
                .stream()
                .forEach(n -> map.put(
                        new Position(n.getX(), n.getY()), n.getTerrain()
                ));

        return map;
    }


    public MapNodeClass getNodeAtPosition(int x, int y){
       MapNodeClass node = getNodes()
                .stream()
                .filter(n -> n.getX() == x)
                .filter(n -> n.getY() == y)
                .findFirst()
                .get();

        return node;
    }

    public int getNeededMoves(int x, int y){
        int number = getNodes()
                .stream()
                .filter(n -> n.getX() == x)
                .filter(n -> n.getY() == y)
                .findFirst()
                .get()
                .getTerrain()
                .getNeededMoves();

        return number;
    }

    public int getMovesBetweenPositions(Position pos1, Position pos2){
        int movesNeeded1 = getNeededMoves(pos1.getX(), pos1.getY());
        int movesNeeded2 = getNeededMoves(pos2.getX(), pos2.getY());

        return movesNeeded1 + movesNeeded2;
    }

    public Position getMyPosition(){
        MapNodeClass node = getNodes()
                .stream()
                .filter(n -> n.getPlayerPosition() == EnumPlayerPositionState.MY_POSITION ||
                        n.getPlayerPosition() == EnumPlayerPositionState.BOTH_PLAYER_POSITION)
                .findFirst()
                .get();

        return new Position(node.getX(), node.getY());
    }

    public List<Position> getPositionList(){
        return getNodesAsList()
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

    public Position getEnemyFortPosition(){
        MapNodeClass node = getNodes()
                .stream()
                .filter(n -> n.getFort() == EnumFortState.ENEMY_FORT_PRESENT)
                .findFirst()
                .orElse(null);

        if(node != null)
            return new Position(node.getX(), node.getY());
        return null;
    }

    public List<Position> getMountainPositions(){
        List<Position> list = getNodes()
                .stream()
                .filter(n -> n.getTerrain() == EnumTerrain.MOUNTAIN)
                .map(n -> new Position(n.getX(), n.getY()))
                .collect(Collectors.toList());
        return list;
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
