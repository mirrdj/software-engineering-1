package path;

import exceptions.PositionInaccessibleException;
import map.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ShortestPathCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ShortestPathCalculator.class);

    public List<Position> getShortestPath(Position from, Position target,  HashMap<Position, Position> previous){
        if(!previous.containsValue(from))
            throw new PositionInaccessibleException("The starting position is not among the calculated positions");

        if(!previous.containsKey(target))
            throw new PositionInaccessibleException("The target position is not among the calculated positions");

        List<Position> positionList = new ArrayList<>();
        positionList.add(target);

        Position current = previous.get(target);
        while(!current.equals(from)){
            positionList.add(current);
            current = previous.get(current);
        }
        positionList.add(from);

        Collections.reverse(positionList);
        logger.debug("Shortest path is: ", positionList);
        return positionList;
    }
}
