package path;

import map.MapClass;
import map.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DijkstraCalculator {
    private static final Logger logger = LoggerFactory.getLogger(DijkstraCalculator.class);
    private final List<Position> positionList;
    private final MapClass mapClass;

    public static class Node {
        private final Position position;
        private final int weight;

        public Node(Position position, int weight) {
            this.position = position;
            this.weight = weight;
        }

        public Integer getWeight() {
            return weight;
        }
        public Position getPosition() {
            return position;
        }
    }


    private void addEdge(HashMap<Position, List<Node>> map, Position pos1, Position pos2){
        // If there is no list containing any List<Node> at index yet
        // create a new List of Node
        if(map.get(pos1) == null){
            List<Node> list = new ArrayList<>();

            map.put(pos1, list);
        }

        // Number of moves needed to move from pos1 to pos2
        int movesNeeded = mapClass.getMovesBetweenPositions(pos1, pos2);

        // Create node list from pos2 and add it to the list of pos1
        Node node = new Node(pos2, movesNeeded);
        map.get(pos1).add(node);
        logger.debug("added " + node + " at " + pos1);
    }

    public HashMap<Position, List<Node>> createAdjancecyList(){
        HashMap<Position, List<Node>> adjacencyList = new HashMap<>();
        int height = mapClass.getHeight();
        int width = mapClass.getWidth();

        // For each position, add all the surrounding nodes to its list
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++){
                Position fromPosition = new Position(x, y);
                logger.debug("from " + fromPosition);
                if(x > 0){
                    Position toPosition = new Position(x - 1, y);
                    logger.debug("1: " + toPosition);
                    addEdge(adjacencyList, fromPosition, toPosition);
                }

                if(x < width - 1){
                    Position toPosition = new Position(x + 1, y);
                    logger.debug("2: " + toPosition);
                    addEdge(adjacencyList, fromPosition, toPosition);
                }

                if(y > 0){
                    Position toPosition = new Position(x, y - 1);
                    logger.debug("3: " + toPosition);
                    addEdge(adjacencyList, fromPosition, toPosition);
                }

                if(y < height - 1){
                    Position toPosition = new Position(x, y + 1);
                    logger.debug("4: " + toPosition);
                    addEdge(adjacencyList, fromPosition, toPosition);
                }
            }

        return adjacencyList;
    }

    public DijkstraCalculator(MapClass mapClass, List<Position> positionList) {
        this.mapClass = mapClass;
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
        HashMap<Position, List<Node>> adjancecyList = createAdjancecyList();

        while(!unvisited.isEmpty()){
            Position current = getPositionOfMinimumDistance(distance, unvisited);
            unvisited.remove(current);

            List<Node> neighbourNodes = adjancecyList.get(current);
            for(Node node : neighbourNodes){
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
