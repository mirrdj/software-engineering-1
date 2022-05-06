package path;

import map.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import path.adjacency_list.AdjancecyList;
import path.adjacency_list.ListNode;

import java.util.*;

public class DijkstraCalculator {
    private static final Logger logger = LoggerFactory.getLogger(DijkstraCalculator.class);
    private final AdjancecyList adjancecyList;
    private final List<Position> positionList;

    public DijkstraCalculator(AdjancecyList adjancecyList, List<Position> positionList) {
        this.adjancecyList = adjancecyList;
        this.positionList = positionList;
    }


    private HashMap<Position, Integer> createDistanceList(List<Position> positionList, Position from){
        HashMap<Position, Integer> distance = new HashMap<>();
        positionList.forEach(pos -> distance.put(pos, Integer.MAX_VALUE));
        distance.put(from, 0);
        return distance;
    }

    private HashMap<Position, Position> createPreviousList(List<Position> positionList){
        HashMap<Position, Position> previous = new HashMap<>();
        positionList.forEach(pos -> previous.put(pos, null));
        return previous;
    }

    private Position getPositionOfMinimumDistance(HashMap<Position, Integer> distance, List<Position> unvisited){
        Position minPosition = null;
        Integer minValue = null;
        for(Position p : unvisited){
            if(minValue == null || distance.get(p) < minValue) {
                minValue = distance.get(p);
                minPosition = p;
            }
        }
        return minPosition;
    }

    public DijkstraResult dijkstraAlgorithm(Position from){
        List<Position> unvisited = new ArrayList<>(positionList);
        HashMap<Position, Integer> distance = createDistanceList(unvisited, from);
        HashMap<Position, Position> previous = createPreviousList(unvisited);

        while(!unvisited.isEmpty()){
            Position current = getPositionOfMinimumDistance(distance, unvisited);
            unvisited.remove(current);

            List<ListNode> neighbourNodes = adjancecyList.getNodeList(current);
            for(ListNode node : neighbourNodes){
                Position neighbourPosition = node.getPosition();
                Integer weight = node.getWeight();

                if(unvisited.contains(neighbourPosition))
                    if(distance.get(current) + weight < distance.get(neighbourPosition)){
                        distance.put(neighbourPosition, distance.get(current) + weight);
                        previous.put(neighbourPosition, current);
                    }
            }

        }

        return new DijkstraResult(from, distance, previous);
    }

}
