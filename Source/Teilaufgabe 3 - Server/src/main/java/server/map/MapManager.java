package server.map;

import server.uniqueID.PlayerID;

import java.util.HashMap;
import java.util.Map;

public class MapManager {
    Map<String, MapClass> maps = new HashMap<>();
    public Map<String, MapClass> getMaps() {
        return maps;
    }
    public void addHalfMap(String playerID, MapClass halfMap){
        maps.put(playerID, halfMap);
    }

    public boolean fullMapComplete(){
        return maps.size() == 2;
    }

}
