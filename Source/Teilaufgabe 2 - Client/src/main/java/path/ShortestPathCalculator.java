package path;

import map.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class ShortestPathCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ShortestPathCalculator.class);

    private ShortestPathCalculator(){}

    public static List<Position> getShortestPath(DijkstraResult result, Position target) throws Exception{
        HashMap<Position, Position> previous = result.getPreviousMap();
        List<Position> positionList = new ArrayList<>();
        Position from = result.getFrom();

        positionList.add(target);

        Position current = previous.get(target);
        while(!current.equals(from)){
            positionList.add(current);
            current = previous.get(current);
        }
        positionList.add(from);

        Collections.reverse(positionList);
        logger.debug(positionList.toString());
        return positionList;
    }
}
