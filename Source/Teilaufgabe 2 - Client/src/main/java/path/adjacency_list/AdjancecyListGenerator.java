package path.adjacency_list;

import map.full_map.FullMapClass;
import map.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdjancecyListGenerator {
    private FullMapClass fullMap;
    private static Logger logger = LoggerFactory.getLogger(AdjancecyListGenerator.class);

    public AdjancecyListGenerator(FullMapClass fullMap) {
        this.fullMap = fullMap;
    }

    private void addEdge(HashMap<Position, List<ListNode>> map, Position pos1, Position pos2){
        // If there is no map containing any ListNode at index yet
        // create a new List of ListNodes
        if(map.get(pos1) == null){
            List<ListNode> list = new ArrayList<ListNode>();
            map.put(pos1, list);
        }

        // Number of moves needed to move from pos1 to pos2
        int movesNeeded = fullMap.getMovesBetweenPositions(pos1, pos2);

        // Create node list from pos2 and add it to the list of pos1
        ListNode node = new ListNode(pos2, movesNeeded);
        map.get(pos1).add(node);
//        logger.debug("added " + node + " at " + pos1);
    }

    public AdjancecyList createAdjancecyList(){
        HashMap<Position, List<ListNode>> adjacencyMap = new HashMap<Position, List<ListNode>>();
        int height = fullMap.getHeight();
        int width = fullMap.getWidth();

        // For each position, add all the surrounding nodes to its list
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++){
                Position fromPosition = new Position(x, y);
//                logger.debug("from " + fromPosition);
                if(x > 0){
                    Position toPosition = new Position(x - 1, y);
//                    logger.debug("1: " + toPosition);
                    addEdge(adjacencyMap, fromPosition, toPosition);
                }

                if(x < width - 1){
                    Position toPosition = new Position(x + 1, y);
//                    logger.debug("2: " + toPosition);
                    addEdge(adjacencyMap, fromPosition, toPosition);
                }

                if(y > 0){
                    Position toPosition = new Position(x, y - 1);
//                    logger.debug("3: " + toPosition);
                    addEdge(adjacencyMap, fromPosition, toPosition);
                }

                if(y < height - 1){
                    Position toPosition = new Position(x, y + 1);
//                    logger.debug("4: " + toPosition);
                    addEdge(adjacencyMap, fromPosition, toPosition);
                }
            }
        AdjancecyList adjancecyList = new AdjancecyList(adjacencyMap);
        return adjancecyList;
    }

}
