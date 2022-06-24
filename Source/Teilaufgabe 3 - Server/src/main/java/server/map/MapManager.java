package server.map;

import server.player.Player;
import server.uniqueID.PlayerID;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapManager {
    Map<String, MapClass> maps = new HashMap<>();
    Optional<MapClass> fullMap = Optional.empty();

    public Map<String, MapClass> getMaps() {
        return maps;
    }


    public void addHalfMap(String playerID, MapClass halfMap){
        // TODO: throw exception if player tries to send another map
        maps.put(playerID, halfMap);

        int maximumMapNumber = 2;
        if(maps.size() == maximumMapNumber){
            MapClass thisPlayerMap = maps.get(playerID);
            MapClass otherPlayerMap = maps.values().stream().findFirst().get();

            fullMap = Optional.of(MapClassCombiner.combineMaps(thisPlayerMap, otherPlayerMap));
        }
    }

    private Map<String, MapClass> getMapOfOtherPlayers(String playerID){
        return maps
                .entrySet()
                .stream()
                .filter(stringPlayerEntry -> !stringPlayerEntry.getKey().equals(playerID))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Optional<MapClass> getFullMap(){
        if(fullMap.isPresent())
            return fullMap;
        else
            return Optional.empty();
    }


}
