package map.full_map;

import map.EnumTerrain;

public class FullMapNodeClass {
    private final int x;
    private final int y;
    private final EnumTerrain terrain;
    private final EnumPlayerPositionState playerPosition;
    private final EnumTreasureState treasure;
    private final EnumFortState fort;

    public FullMapNodeClass(EnumTerrain terrain, EnumPlayerPositionState playerPosition, EnumTreasureState treasure, EnumFortState fort, int x, int y) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
        this.playerPosition = playerPosition;
        this.treasure = treasure;
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


    @Override
    public String toString() {
        String string = "";

//        string += String.valueOf(getX()) + '|';
//        string += String.valueOf(getY()) + '|';

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
