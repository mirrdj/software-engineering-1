package path;

import map.Position;

import java.util.HashMap;

public class DijkstraResult {
    private final HashMap<Position, Integer> distanceMap;   // Map containing the distance to each position from initial position
    private final HashMap<Position, Position> previousMap;  // The map on which the distances were calculated
    private final Position from;                            // Initial position

    public DijkstraResult(Position from, HashMap<Position, Integer> distanceMap, HashMap<Position, Position> previous) {
        this.distanceMap = distanceMap;
        this.previousMap = previous;
        this.from = from;
    }
    public Position getFrom(){return from;}
    public HashMap<Position, Integer> getDistanceMap() {
        return distanceMap;
    }
    public Integer getDistance(Position position){
        return distanceMap.get(position);
    }
    public HashMap<Position, Position> getPreviousMap() {
        return previousMap;
    }
}
