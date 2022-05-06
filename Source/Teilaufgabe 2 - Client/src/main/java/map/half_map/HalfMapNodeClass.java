package map.half_map;

import map.EnumTerrain;

public class HalfMapNodeClass{
    private final int x;
    private final int y;
    private final EnumTerrain terrain;
    private final boolean fortPresent;

    public HalfMapNodeClass(int x, int y, boolean fortPresent, EnumTerrain terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
        this.fortPresent = fortPresent;
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
    public boolean isFortPresent() {
        return fortPresent;
    }

    @Override
    public String toString() {
        return "HalfMapNodeClass{" +
                "x=" + x +
                ", y=" + y +
                ", terrain=" + terrain +
                '}';
    }
}
