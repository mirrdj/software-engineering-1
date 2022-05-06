package map.half_map;

import map.EnumTerrain;
import map.MapNodeClass;
import map.Position;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HalfMapClass {
    private Collection<MapNodeClass> nodes;
    public Collection<MapNodeClass> getNodes() {
        return nodes;
    }
    public HalfMapClass(Collection<MapNodeClass> nodes) {
        this.nodes = nodes;
    }

    public int getHeight(){
        int height = nodes
                .stream()
                .max(Comparator.comparing(MapNodeClass::getY))
                .get()
                .getY();
        height += 1;

        return height;
    }
    public int getWidth(){
        int width = nodes
                .stream()
                .max(Comparator.comparing(MapNodeClass::getX))
                .get()
                .getX();
        width += 1;

        return width;
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

    @Override
    public String toString() {
        return "HalfMapClass{" +
                "nodes=" + nodes +
                '}';
    }
    public int getMountainNumber(){
        int mountainNumber = nodes
                .stream()
                .filter(n -> n.getTerrain() == EnumTerrain.MOUNTAIN)
                .collect(Collectors.toList())
                .size();
        return mountainNumber;
    }

    public int getWaterNumber(){
        int waterNumber = nodes
                .stream()
                .filter(n -> n.getTerrain() == EnumTerrain.WATER)
                .collect(Collectors.toList())
                .size();
        return waterNumber;
    }

    public int getGrassNumber(){
        int grassNumber = nodes
                .stream()
                .filter(n -> n.getTerrain() == EnumTerrain.GRASS)
                .collect(Collectors.toList())
                .size();
        return grassNumber;
    }



}
