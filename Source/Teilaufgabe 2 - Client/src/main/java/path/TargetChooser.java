package path;

import exceptions.PositionInaccessibleException;
import map.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class TargetChooser {
    private static final Logger logger = LoggerFactory.getLogger(TargetChooser.class);

    // Finds the closest position from a list positions
    // to the starting position
    public Position chooseTarget(Position from, List<Position> positions, HashMap<Position, Integer> distanceMap) {
        if(!distanceMap.containsKey(from))
            throw new PositionInaccessibleException("The starting position is not among the calculated positions");

        Position target =  positions
                .stream()
                .filter(p -> !p.equals(from))
                .min(Comparator.comparing(distanceMap::get))
                .stream()
                .findFirst()
                .orElse(null);

        logger.debug("Calculated target is " + target);
        return target;
    }


}



