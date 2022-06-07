package server.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import server.uniqueID.GameID;
import server.uniqueID.PlayerID;

import static org.junit.jupiter.api.Assertions.*;

class MapManagerTest {
    MapManager mapManager;
    @BeforeEach
    private void createMapManager(){
        mapManager = new MapManager();
    }

    @Test
    public void fullMapComplete_twoHalfMapsSet_returnTrue() {
        PlayerID playerID1 = Mockito.mock(PlayerID.class);
        PlayerID playerID2 = Mockito.mock(PlayerID.class);
        MapClass halfMap1 = Mockito.mock(MapClass.class);
        MapClass halfMap2 = Mockito.mock(MapClass.class);
        mapManager.addHalfMap(playerID1, halfMap1);
        mapManager.addHalfMap(playerID2, halfMap2);

        boolean checkComplete = mapManager.fullMapComplete();
        Assertions.assertTrue(checkComplete);
    }
}