package server.map;

public class MapNodeClass {
    private final int x;
    private final int y;
    private final EnumTerrain terrain;
    private  EnumPlayerPositionState playerPosition;
    private  EnumTreasureState treasure;
    private  EnumFortState fort;

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

    public void setPlayerPosition(EnumPlayerPositionState playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setTreasure(EnumTreasureState treasure) {
        this.treasure = treasure;
    }

    public void setFort(EnumFortState fort) {
        this.fort = fort;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(getX());
        stringBuilder.append(" ");
        stringBuilder.append(getY());
        stringBuilder.append(" ");

        if(getTerrain() == EnumTerrain.GRASS)
            stringBuilder.append("G|");
        else if(getTerrain() == EnumTerrain.MOUNTAIN)
            stringBuilder.append("M|");
        else if(getTerrain() == EnumTerrain.WATER)
            stringBuilder.append("W|");

        if(playerPosition == EnumPlayerPositionState.BOTH_PLAYER_POSITION)
            stringBuilder.append("BP|");
        else if(playerPosition == EnumPlayerPositionState.ENEMY_PLAYER_POSITION)
            stringBuilder.append("EP|");
        else if(playerPosition == EnumPlayerPositionState.MY_POSITION)
            stringBuilder.append("MP|");
        else if(playerPosition == EnumPlayerPositionState.NO_PLAYER_PRESENT)
            stringBuilder.append("--|");

        if(treasure == EnumTreasureState.MY_TREASURE_IS_PRESENT)
            stringBuilder.append("T|");
        else if(treasure == EnumTreasureState.NO_OR_UNKNOWN_TREASURE_STATE)
            stringBuilder.append("-|");

        if(fort == EnumFortState.ENEMY_FORT_PRESENT)
            stringBuilder.append("EF|");
        else if(fort == EnumFortState.MY_FORT_PRESENT)
            stringBuilder.append("MF|");
        else if(fort == EnumFortState.NO_OR_UNKNOWN_FORT_STATE)
            stringBuilder.append("--|");

        return stringBuilder.toString();
    }

}
