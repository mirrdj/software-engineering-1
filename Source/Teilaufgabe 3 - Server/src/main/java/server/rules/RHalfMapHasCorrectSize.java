package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.HalfMapNode;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.exceptions.MapHasWrongSizeException;

import java.util.Comparator;

public class RHalfMapHasCorrectSize implements IRule{
    private static Logger logger = LoggerFactory.getLogger(RHalfMapHasCorrectSize.class);
    private void checkCorrectSize(HalfMap map){
        int minY = map.getMapNodes()
                .stream()
                .min(Comparator.comparing(HalfMapNode::getY))
                .get()
                .getY();
        logger.debug("minY={}", minY);

        int maxY = map.getMapNodes()
                .stream()
                .max(Comparator.comparing(HalfMapNode::getY))
                .get()
                .getY();
        logger.debug("maxY={}", maxY);

        int minX = map.getMapNodes()
                .stream()
                .min(Comparator.comparing(HalfMapNode::getX))
                .get()
                .getX();
        logger.debug("minX={}", minX);

        int maxX = map.getMapNodes()
                .stream()
                .max(Comparator.comparing(HalfMapNode::getX))
                .get()
                .getX();
        logger.debug("maxX={}", maxX);
        System.out.println("max x " + maxX);

        int nodeNumber = map.getMapNodes().size();

        if(minY != 0)
            throw new MapHasWrongSizeException("Smallest Y is not 0");

        if(maxY != 3)
            throw new MapHasWrongSizeException("Highest Y is not 3");

        if(minX != 0)
            throw new MapHasWrongSizeException("Smallest X is not 0");

        if(maxX != 7)
            throw new MapHasWrongSizeException("Highest X is not 7");

        if(nodeNumber != 32)
            throw new MapHasWrongSizeException("The number of the nodes is not 32");

    }
    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap map) {
        checkCorrectSize(map);
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }
}
