package path;

import map.MapClass;
import map.Position;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class AISimpleClass implements AIInterface{
    private TargetChooser targetChooser = new TargetChooser();
    private ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator();
    private MoveCalculator moveCalculator;
    private DijkstraCalculator dijkstraCalculator;

    private List<Position> relevantPositions;
    MapClass myHalf;
    MapClass enemyHalf;

    public AISimpleClass(MapClass fullMap){
        dijkstraCalculator = new DijkstraCalculator(fullMap);
        moveCalculator = new MoveCalculator(fullMap.getTerrainNodes());

        myHalf = fullMap.getMyHalf();
        relevantPositions =  myHalf.getPositionList();
        enemyHalf = fullMap.getEnemyHalf();
    }

    private Queue<EnumMove> generateAction(boolean treasureCollected, Position myPosition){
        Queue<EnumMove> moves = new ArrayDeque<>();

        // Choose target from the first half
        if(!treasureCollected){
            DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(myPosition);
            Position target = targetChooser.chooseTarget(myPosition, relevantPositions, result.getDistanceMap());
            relevantPositions.remove(target);

            List<Position> path = shortestPathCalculator.getShortestPath(myPosition, target, result.getPreviousMap());
            moves = moveCalculator.getMoves(path);
        }

        // Choose target from the second half
        else {
            relevantPositions = enemyHalf.getPositionList();
            DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(myPosition);
            Position target = targetChooser.chooseTarget(myPosition, relevantPositions, result.getDistanceMap());
            relevantPositions.remove(target);

            List<Position> path = shortestPathCalculator.getShortestPath(myPosition, target, result.getPreviousMap());
            moves = moveCalculator.getMoves(path);
        }
        return moves;
    }

    public Queue<EnumMove> generateAction(boolean treasureFound, boolean treasureCollected,
                                         boolean fortFound, Position myPosition){
        return generateAction(treasureCollected, myPosition);
    }
}
