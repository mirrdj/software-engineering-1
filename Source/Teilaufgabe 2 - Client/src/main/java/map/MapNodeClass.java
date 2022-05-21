package map;

import exceptions.PlacedOnWrongFieldException;

import java.util.Objects;

public class MapNodeClass {
    private final int x;
    private final int y;
    private final EnumTerrain terrain;
    private  EnumPlayerPositionState playerPosition;
    private  EnumTreasureState treasure;
    private final EnumFortState fort;

    public MapNodeClass(int x, int y, EnumTerrain terrain, boolean fortPresent) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;

        if(terrain != EnumTerrain.GRASS && fortPresent)
            throw new PlacedOnWrongFieldException("Fort cannot be placed on this type of field " + terrain);

        if(fortPresent)
            this.fort = EnumFortState.MY_FORT_PRESENT;
        else
            this.fort = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;
    }

    public MapNodeClass(int x, int y, EnumTerrain terrain, EnumFortState fort, EnumPlayerPositionState playerPosition, EnumTreasureState treasure) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;

        if(terrain == EnumTerrain.WATER && playerPosition != EnumPlayerPositionState.NO_PLAYER_PRESENT)
            throw new PlacedOnWrongFieldException("Player cannot be placed on this type of field: " + terrain);
        this.playerPosition = playerPosition;

        if(terrain != EnumTerrain.GRASS && treasure != EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE)
            throw new PlacedOnWrongFieldException("Treasure cannot be placed on this type of field " + terrain);
        this.treasure = treasure;

        if(terrain != EnumTerrain.GRASS && fort != EnumFortState.NO_OR_UNKNOWN_FORT_STATE)
            throw new PlacedOnWrongFieldException("Fort cannot be placed on this type of field " + terrain);
        this.fort = fort;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public EnumTerrain getTerrain() {
        return terrain;
    }
    public EnumPlayerPositionState getPlayerPosition() {
        return playerPosition;
    }
    public EnumTreasureState getTreasure() {
        return treasure;
    }
    public EnumFortState getFort() {
        return fort;
    }

    public boolean isFortPresent(){return fort == EnumFortState.MY_FORT_PRESENT;}

    @Override
    public String toString() {
        String string = "";

        string += Integer.toString(getX()) + ' ';
        string += Integer.toString(getY()) + ' ';

        if(getTerrain() == EnumTerrain.GRASS)
            string += "G" + '|';
        else if(getTerrain() == EnumTerrain.MOUNTAIN)
            string += "M" + '|';
        else if(getTerrain() == EnumTerrain.WATER)
            string += "W" + '|';

        if(playerPosition == EnumPlayerPositionState.BOTH_PLAYER_POSITION)
            string += "BP" + '|';
        else if(playerPosition == EnumPlayerPositionState.ENEMY_PLAYER_POSITION)
            string += "EP" + '|';
        else if(playerPosition == EnumPlayerPositionState.MY_POSITION)
            string += "MP" + '|';
        else if(playerPosition == EnumPlayerPositionState.NO_PLAYER_PRESENT)
            string += "--" + '|';

        if(treasure == EnumTreasureState.MY_TREASURE_IS_PRESENT)
            string += "T" + '|';
        else if(treasure == EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE)
            string += "-" + '|';

        if(fort == EnumFortState.ENEMY_FORT_PRESENT)
            string += "EF" + '|';
        else if(fort == EnumFortState.MY_FORT_PRESENT)
            string += "MF" + '|';
        else if(fort == EnumFortState.NO_OR_UNKNOWN_FORT_STATE)
            string += "--" + '|';

        return string;
    }
}
