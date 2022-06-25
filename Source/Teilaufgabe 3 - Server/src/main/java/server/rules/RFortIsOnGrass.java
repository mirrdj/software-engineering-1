package server.rules;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerMove;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.exceptions.WrongFortNumberException;
import server.exceptions.WrongFortTerrainException;

public class RFortIsOnGrass implements IRule{
    private static Logger logger = LoggerFactory.getLogger(RFortIsOnGrass.class);
    private void checkFortField(HalfMap map){
        ETerrain desiredTerrain = ETerrain.Grass;
        ETerrain fortField = map
                .getMapNodes()
                .stream()
                .filter(eachNode -> eachNode.isFortPresent())
                .findFirst()
                .get()
                .getTerrain();
        logger.debug("There fortress is on {}", fortField);

        if(fortField != desiredTerrain)
            throw new WrongFortTerrainException("Fort was placed on" + fortField);
    }
    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap) {
        checkFortField(halfMap);
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }

    @Override
    public void validateMove(UniqueGameIdentifier gameID, PlayerMove playerMove) {

    }
}
