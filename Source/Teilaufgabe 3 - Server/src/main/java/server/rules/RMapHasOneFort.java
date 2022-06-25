package server.rules;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.exceptions.WrongFortNumberException;

public class RMapHasOneFort implements IRule {
    private static Logger logger = LoggerFactory.getLogger(RMapHasOneFort.class);

    private void checkFortNumber(HalfMap map){
        int desiredNumber = 1;
        long fortNumber = map
                .getMapNodes()
                .stream()
                .filter(eachNode -> eachNode.isFortPresent())
                .count();
        logger.debug("There number of fortresses is {}", fortNumber);

        if(fortNumber != desiredNumber)
            throw new WrongFortNumberException("There should be one fort, but there are " + fortNumber);
    }
    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap) {
        checkFortNumber(halfMap);
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }
}
