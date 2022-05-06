package path;

import controller.EnumGamePhase;
import exceptions.InvalidTargetException;
import map.Position;
import map.full_map.FullMapClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AIClass {
    private static Logger logger = LoggerFactory.getLogger(AIClass.class);
    private DijkstraCalculator dijkstraCalculator;
    private MoveCalculator moveCalculator;
    private TargetChooser targetChooser;

    public void setDijkstraCalculator(DijkstraCalculator dijkstraCalculator) {
        this.dijkstraCalculator = dijkstraCalculator;
    }
    public void setMoveCalculator(MoveCalculator moveCalculator) {
        this.moveCalculator = moveCalculator;
    }
    public void setTargetChooser(TargetChooser targetChooser) {
        this.targetChooser = targetChooser;
    }
    public List<EnumMove> collectTreasure(FullMapClass fullMap) throws Exception {
        DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(fullMap.getMyPosition());
        List<Position> path = ShortestPathCalculator.getShortestPath(result, fullMap.getMyTreasurePosition());

        return moveCalculator.getMoves(path);
    }

    public List<EnumMove> reachFortres(FullMapClass fullMap) throws Exception {
        DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(fullMap.getMyPosition());
        List<Position> path = ShortestPathCalculator.getShortestPath(result, fullMap.getMyTreasurePosition());

        return moveCalculator.getMoves(path);
    }

    public List<EnumMove> findTreasure(FullMapClass fullMap) throws Exception {
        targetChooser.setGamePhase(EnumGamePhase.FIND_TREASURE);
        return findGameObject(fullMap);
    }

    public List<EnumMove> findFortress(FullMapClass fullMap) throws Exception {
        targetChooser.setGamePhase(EnumGamePhase.FIND_ENEMY_FORTRESS);
        return findGameObject(fullMap);
    }

    private List<EnumMove> findGameObject (FullMapClass fullMap) throws Exception {
        Position myPosition = fullMap.getMyPosition();

        DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(myPosition);
        Position target = targetChooser.chooseTarget(result);

        if(target == null)
            throw new InvalidTargetException("The target chosen is not valid");

        List<Position> path = ShortestPathCalculator.getShortestPath(result, target);
        return moveCalculator.getMoves(path);

    }
}
