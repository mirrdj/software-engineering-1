package server.map;

import server.uniqueID.PlayerID;

import java.util.HashMap;
import java.util.Map;

public class MapManager {
    Map<PlayerID, MapClass> maps = new HashMap<>();
    public Map<PlayerID, MapClass> getMaps() {
        return maps;
    }
    public void addHalfMap(PlayerID playerID, MapClass halfMap){
        maps.put(playerID, halfMap);
    }

    public boolean fullMapComplete(){
        return maps.size() == 2;
    }

}
