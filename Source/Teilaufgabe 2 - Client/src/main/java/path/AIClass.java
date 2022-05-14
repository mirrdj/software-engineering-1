package path;

import controller.EnumGamePhase;
import exceptions.InvalidTargetException;
import map.EnumLayout;
import map.EnumTerrain;
import map.Position;
import map.MapClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class AIClass {
    private static final Logger logger = LoggerFactory.getLogger(AIClass.class);
    private DijkstraCalculator dijkstraCalculator;
    private MoveCalculator moveCalculator;
    private TargetChooser targetChooser;
    public void setUp(MapClass mapClass){
        EnumLayout mapLayout = mapClass.getLayout();

        HashMap<Position, EnumTerrain> terrain = mapClass.getTerrainNodes();
        Position initialPosition = mapClass.getMyPosition();
        List<Position> positionList = mapClass.getPositionList();

        // Set DijkstraCalculator for aiObject
        DijkstraCalculator dijkstraCalculator = new DijkstraCalculator(mapClass, positionList);
        this.setDijkstraCalculator(dijkstraCalculator);
        logger.info("DijkstraCalculator for aiObject set");

        // Set TargetChooser for aiObject
        TargetChooser targetChooser = new TargetChooser(terrain, initialPosition, mapLayout);
        targetChooser.setGamePhase(EnumGamePhase.FIND_TREASURE);
        this.setTargetChooser(targetChooser);
        logger.info("TargetChooser for aiObject set");

        // Set MoveCalculator for aiObject
        MoveCalculator moveCalculator = new MoveCalculator(terrain);
        this.setMoveCalculator(moveCalculator);
        logger.info("MoveCalculator for aiObject set");
    }

    public void setDijkstraCalculator(DijkstraCalculator dijkstraCalculator) {
        this.dijkstraCalculator = dijkstraCalculator;
    }
    public void setMoveCalculator(MoveCalculator moveCalculator) {
        this.moveCalculator = moveCalculator;
    }
    public void setTargetChooser(TargetChooser targetChooser) {
        this.targetChooser = targetChooser;
    }
    public List<EnumMove> collectTreasure(MapClass mapClass) throws Exception {
        DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(mapClass.getMyPosition());
        List<Position> path = ShortestPathCalculator.getShortestPath(result, mapClass.getMyTreasurePosition());

        return moveCalculator.getMoves(path);
    }

    public List<EnumMove> reachFortres(MapClass mapClass) throws Exception {
        DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(mapClass.getMyPosition());
        List<Position> path = ShortestPathCalculator.getShortestPath(result, mapClass.getMyTreasurePosition());

        return moveCalculator.getMoves(path);
    }

    public List<EnumMove> findTreasure(MapClass mapClass) throws Exception {
        logger.info("Looking for the treasure now");
        targetChooser.setGamePhase(EnumGamePhase.FIND_TREASURE);
        return findGameObject(mapClass);
    }

    public List<EnumMove> findFort(MapClass mapClass) throws Exception {
        logger.info("Looking for the fort now");
        targetChooser.setGamePhase(EnumGamePhase.FIND_ENEMY_FORTRESS);
        return findGameObject(mapClass);
    }

    private List<EnumMove> findGameObject (MapClass mapClass) throws Exception {
        Position myPosition = mapClass.getMyPosition();

        DijkstraResult result = dijkstraCalculator.dijkstraAlgorithm(myPosition);
        Position target = targetChooser.chooseTarget(result);

        if(target == null)
            throw new InvalidTargetException("The target chosen is not valid");

        List<Position> path = ShortestPathCalculator.getShortestPath(result, target);
        return moveCalculator.getMoves(path);

    }
}
