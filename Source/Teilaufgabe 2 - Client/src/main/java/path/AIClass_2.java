package path;

import map.EnumLayout;
import map.EnumTerrain;
import map.MapClass;
import map.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AIClass_2 {
    private static HashMap<Position, EnumTerrain> terrainNodes;
    private static Position initialPosition;
    private static EnumLayout layout;

    MapClass fullMap;
    MapClass firstHalf;
    MapClass secondHalf;

    private static HashMap<Position, EnumTerrain> unvisitedPositionsOwnHalf; // you do not want to reinitialize this during the execution
    private static HashMap<Position, EnumTerrain> unvisitedPositionsEnemyHalf; // you do not want to reinitialize this during the execution


    public AIClass_2(){

    }
//    public List<EnumMove> generateAction(boolean treasureCollected, boolean fortReached, MapClass fullMap){
//        if(treasureCollected){
//
//        }
//        else if(){
//
//        }
//        else {
//
//        }
//
//    }
}
