package server.map;

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

        if(fortPresent)
            this.fort = EnumFortState.MY_FORT_PRESENT;
        else
            this.fort = EnumFortState.NO_OR_UNKNOWN_FORT_STATE;
    }
    public MapNodeClass(int x, int y, EnumTerrain terrain, EnumFortState fort, EnumPlayerPositionState playerPosition, EnumTreasureState treasure) {
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

    public boolean isFortPresent(){return fort == EnumFortState.MY_FORT_PRESENT;}
}
