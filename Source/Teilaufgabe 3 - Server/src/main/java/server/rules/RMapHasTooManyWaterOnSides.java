package server.rules;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerMove;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import server.exceptions.WaterFieldsOnSidesExceededException;

public class RMapHasTooManyWaterOnSides implements IRule {

    private boolean shortLineCheck(HalfMap halfMap, int x){
        System.out.println(halfMap.getMapNodes());
        System.out.println("x check "  + x);
        int maxWaterShortSide = 1;
        long waterNumber = halfMap
                .getMapNodes()
                .stream()
                .filter(n -> n.getX() == x)
                .filter(n -> n.getTerrain() == ETerrain.Water)
                .count();

        System.out.println("on short side is " + waterNumber);
        if(waterNumber > maxWaterShortSide){
            return false;
        }

        return true;
    }

    private boolean longLineCheck(HalfMap halfMap, int y){
        int maxWaterLongSide = 3;
        long waterNumber = halfMap
                .getMapNodes()
                .stream()
                .filter(n -> n.getY() == y)
                .filter(n -> n.getTerrain() == ETerrain.Water)
                .count();

        if(waterNumber > maxWaterLongSide){
            return false;
        }

        return true;


    }


    private void checkSides(HalfMap halfMap){
        if(!longLineCheck(halfMap,0))
            throw new WaterFieldsOnSidesExceededException("The first long line has too many water fields");
        if(!longLineCheck(halfMap,3))
            throw new WaterFieldsOnSidesExceededException("The last long line has too many water fields");
        if(!shortLineCheck(halfMap, 0))
            throw new WaterFieldsOnSidesExceededException("The first short line has too many water fields");
        if(!shortLineCheck(halfMap,7))
            throw new WaterFieldsOnSidesExceededException("The last short line has too many water fields");

    }

    @Override
    public void validateRegisterPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {

    }

    @Override
    public void validateHalfMap(UniqueGameIdentifier gameID, HalfMap halfMap) {
        checkSides(halfMap);
    }

    @Override
    public void validateGetState(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

    }

    @Override
    public void validateMove(UniqueGameIdentifier gameID, PlayerMove playerMove) {

    }
}
