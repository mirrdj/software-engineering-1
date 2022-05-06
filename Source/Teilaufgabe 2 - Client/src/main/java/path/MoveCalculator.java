package path;

import map.EnumTerrain;
import map.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoveCalculator {
    private static final Logger logger = LoggerFactory.getLogger(MoveCalculator.class);
    private HashMap<Position, EnumTerrain> terrainNodes;

    public MoveCalculator(HashMap<Position, EnumTerrain> terrainNodes) {
        this.terrainNodes = terrainNodes;
    }

    private EnumMove getDirection(Position current, Position next){
        // Current is on the left, needs to move to the right
        if(current.getX() < next.getX())
            return EnumMove.RIGHT;

        // Current is on the right, needs to move to the left
        if(current.getX() > next.getX())
            return EnumMove.LEFT;

        // Current is above, needs to move down
        if(current.getY() < next.getY())
            return EnumMove.DOWN;

        // Current is under, needs to move up
        if(current.getY() > next.getY())
            return EnumMove.UP;

        return null;
    }

    // TODO remove getNeededMoves, make sure it still works without - it used to be ingetMovesBetweenPositions
    private int getNeededMoves(Position pos){
        return terrainNodes.get(pos).getNeededMoves();
    }

    // Calculates the number of moves it takes to get from one position to a neighbouring positions
    private int getNumberOfMoves(Position pos1, Position pos2){
        int pos1Value = terrainNodes.get(pos1).getNeededMoves();
        int pos2Value = terrainNodes.get(pos2).getNeededMoves();

        return pos1Value + pos2Value;
    }

    public List<EnumMove> getMoves(List<Position> path){
        List<EnumMove> moves = new ArrayList<>();

        int index = 0;
        Position current = path.get(index);
        Position last = path.get(path.size() - 1);

        while(!current.equals(last)){
           Position next = path.get(index + 1);

           int movesNeeded = getNumberOfMoves(current, next);
           int moveIndex = 0;
           while(moveIndex < movesNeeded){
               moves.add(getDirection(current, next));
               moveIndex++;
           }

           current = next;
           index++;
        }

        logger.debug("The path is " + path);
        logger.debug("The moves are " + moves);
        return moves;
    }

}
