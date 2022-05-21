package path;

import map.EnumTerrain;
import map.MapClass;
import map.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Queue;

public class AISimpleClass implements AIInterface{
    private static final Logger logger = LoggerFactory.getLogger(AISimpleClass.class);
    private final TargetChooser targetChooser = new TargetChooser();
    private final ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator();
    private final MoveCalculator moveCalculator;
    private final DijkstraCalculator dijkstraCalculator;

    private List<Position> relevantPositionsMyHalf;
    private List<Position> relevantPositionsEnemyHalf;
    MapClass myHalf;
    MapClass enemyHalf;

    public AISimpleClass(MapClass fullMap){


        dijkstraCalculator = new DijkstraCalculator(fullMap);
        moveCalculator = new MoveCalculator(fullMap.getTerrainNodes());

        myHalf = fullMap.getMyHalf();
        relevantPositionsMyHalf =  myHalf.getPositionList();
        relevantPositionsMyHalf.removeIf(position -> fullMap.getTerrainNodes().get(position) == EnumTerrain.WATER);
        relevantPositionsMyHalf.removeIf(position -> fullMap.getTerrainNodes().get(position) == EnumTerrain.MOUNTAIN);


        enemyHalf = fullMap.getEnemyHalf();
        relevantPositionsEnemyHalf = enemyHalf.getPositionList();
        relevantPositionsEnemyHalf.removeIf(position -> fullMap.getTerrainNodes().get(position) == EnumTerrain.WATER);
        relevantPositionsEnemyHalf.removeIf(position -> fullMap.getTerrainNodes().get(position) == EnumTerrain.MOUNTAIN);
    }

    private Queue<EnumMove> generateAction(boolean treasureCollected, Position myPosition){

        // Choose target from the first half
        if(!treasureCollected){
            DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(myPosition);
            Position target = targetChooser.chooseTarget(myPosition, relevantPositionsMyHalf, result.getDistanceMap());
            relevantPositionsMyHalf.remove(target);
            logger.debug("Target is ", target);

            List<Position> path = shortestPathCalculator.getShortestPath(myPosition, target, result.getPreviousMap());

            return moveCalculator.getMoves(path);
        }

        // Choose target from the second half
        else {
            logger.debug("Relevant positions enemy ", relevantPositionsEnemyHalf);
            DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(myPosition);
            Position target = targetChooser.chooseTarget(myPosition, relevantPositionsEnemyHalf, result.getDistanceMap());
            relevantPositionsEnemyHalf.remove(target);
            logger.debug("Target is ", target);

            List<Position> path = shortestPathCalculator.getShortestPath(myPosition, target, result.getPreviousMap());

            return  moveCalculator.getMoves(path);
        }

    }

    public Queue<EnumMove> generateAction(boolean treasureFound, boolean treasureCollected,
                                         boolean fortFound, Position myPosition){
        return generateAction(treasureCollected, myPosition);
    }
}
