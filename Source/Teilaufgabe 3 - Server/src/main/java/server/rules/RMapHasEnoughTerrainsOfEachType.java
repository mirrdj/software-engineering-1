package server.rules;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.exceptions.WrongFieldNumberException;

public class RMapHasEnoughTerrainsOfEachType implements IRule{
    private static Logger logger = LoggerFactory.getLogger(RMapHasEnoughTerrainsOfEachType.class);
    private void checkCertainType(HalfMap halfMap, int desiredNumber, ETerrain terrainType){
        long terrainNumber = halfMap
                .getMapNodes()
                .stream()
                .filter(eachNode -> eachNode.getTerrain().equals(terrainType))
                .count();
        logger.debug("There are {} nodes of type {}", terrainNumber, terrainType);

        if(terrainNumber < desiredNumber)
            throw new WrongFieldNumberException("There are not enough nodes of type " + terrainType);
    }
    private void checkTerrains(HalfMap halfMap){
        int minimumWaterFields = 4;
        int minimumMountainFields = 3;
        int minimumGrassFields = 15;

        checkCertainType(halfMap, minimumGrassFields, ETerrain.Grass);
        checkCertainType(halfMap, minimumWaterFields, ETerrain.Water);
        checkCertainType(halfMap, minimumMountainFields, ETerrain.Mountain);
    }
    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap map) {
        checkTerrains(map);
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }
}
