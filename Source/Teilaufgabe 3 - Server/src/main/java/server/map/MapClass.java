package server.map;

import java.util.Collection;

public class MapClass {
    private final Collection<MapNodeClass> nodes;
    public MapClass(Collection<MapNodeClass> nodes){
        this.nodes = nodes;
    }
    public Collection<MapNodeClass> getNodes() {
        return nodes;
    }
}
