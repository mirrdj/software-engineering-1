package server.rules;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;

public class RMapHasTooManyWaterOnSides implements IRule {

    private boolean shortLineCheck(HalfMap halfMap, int x){
        return false;
    }

    private boolean longLineCheck(HalfMap halfMap, int y){
        return false;
    }


    private void checkSides(HalfMap halfMap){

    }

    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap) {

    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }
}
