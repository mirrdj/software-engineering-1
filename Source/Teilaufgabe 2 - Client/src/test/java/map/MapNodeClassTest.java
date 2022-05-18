package map;

import exceptions.PlacedOnWrongFieldException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MapNodeClassTest {

    @Test
    void equals_checkTwoEqualNodes_returnTrue() {
        MapNodeClass node1 = new MapNodeClass(0, 0, EnumTerrain.GRASS, false);
        MapNodeClass node2 = new MapNodeClass(0, 0, EnumTerrain.GRASS, false);

        Assertions.assertTrue(node1.equals(node2));
    }

    @Test
    void equals_checkTwoEqualNodes_returnFalse() {
        MapNodeClass node1 = new MapNodeClass(0, 1, EnumTerrain.GRASS, false);
        MapNodeClass node2 = new MapNodeClass(0, 0, EnumTerrain.GRASS, false);

        Assertions.assertFalse(node1.equals(node2));
    }

    @Test
    void constructor_placeFortOnWater_throwPlacedOnWrongFieldException(){
        Executable exec = () ->  new MapNodeClass(0, 1,
                EnumTerrain.WATER,
                EnumFortState.MY_FORT_PRESENT,
                EnumPlayerPositionState.NO_PLAYER_PRESENT,
                EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE);

        Assertions.assertThrows(PlacedOnWrongFieldException.class, exec);
    }

    @Test
    void constructor_placeTreasureOnMountain_throwPlacedOnWrongFieldException(){
        Executable exec = () ->  new MapNodeClass(0, 1,
                EnumTerrain.MOUNTAIN,
                EnumFortState.NO_OR_UNKNOWN_FORT_STATE,
                EnumPlayerPositionState.NO_PLAYER_PRESENT,
                EnumTreasureState.MY_TREASURE_IS_PRESENT);

        Assertions.assertThrows(PlacedOnWrongFieldException.class, exec);
    }

    @Test
    void constructor_placeMyPlayerOnWater_throwPlacedOnWrongFieldException(){
        Executable exec = () ->  new MapNodeClass(0, 1,
                EnumTerrain.WATER,
                EnumFortState.NO_OR_UNKNOWN_FORT_STATE,
                EnumPlayerPositionState.MY_POSITION,
                EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE);

        Assertions.assertThrows(PlacedOnWrongFieldException.class, exec);
    }
}