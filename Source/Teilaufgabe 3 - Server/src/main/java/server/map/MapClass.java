package server.map;

import java.util.Collection;
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
        Optional<MapNodeClass> node = getNodes()
                .stream()
                .filter(n -> n.getX() == x)
                .filter(n -> n.getY() == y)
                .findFirst();

        return node;
    }

}
