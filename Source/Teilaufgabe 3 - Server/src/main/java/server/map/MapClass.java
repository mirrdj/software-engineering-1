package server.map;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class MapClass {
    private final Collection<MapNodeClass> nodes;
    public MapClass(Collection<MapNodeClass> nodes){
        this.nodes = nodes;
    }
    public Collection<MapNodeClass> getNodes() {
        return nodes;
    }

    public Optional<MapNodeClass> getNodeAtPosition(int x, int y){
        return getNodes()
                .stream()
                .filter(n -> n.getX() == x)
                .filter(n -> n.getY() == y)
                .findFirst();
    }

    public int getHeight(){
        Optional<MapNodeClass> nodeMin = nodes
                .stream()
                .min(Comparator.comparing(MapNodeClass::getY));

        Optional<MapNodeClass> nodeMax = nodes
                .stream()
                .max(Comparator.comparing(MapNodeClass::getY));

        if(nodeMin.isEmpty() || nodeMax.isEmpty())
            return -1;
        else
            return nodeMax.get().getY() - nodeMin.get().getY() + 1;
    }

    public int getWidth(){
        Optional<MapNodeClass> nodeMin = nodes
                .stream()
                .min(Comparator.comparing(MapNodeClass::getX));

        Optional<MapNodeClass> nodeMax = nodes
                .stream()
                .max(Comparator.comparing(MapNodeClass::getX));

        if(nodeMin.isEmpty() || nodeMax.isEmpty())
            return -1;
        else
            return nodeMax.get().getX() - nodeMin.get().getX() + 1;
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("\n");

        int height = getHeight();
        int width = getWidth();

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(getNodeAtPosition(x, y).isPresent())
                    string.append(getNodeAtPosition(x, y)).append("   ");
            }
            string.append('\n');
        }

        return string.toString();
    }


}
