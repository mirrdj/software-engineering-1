package server.rules;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;

public class RMapHasEnoughTerrainsOfEachType implements IRule{
    private void checkCertainType(int desiredNumber, ETerrain terrainType){

    }
    private void checkTerrains(HalfMap halfMap){

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
