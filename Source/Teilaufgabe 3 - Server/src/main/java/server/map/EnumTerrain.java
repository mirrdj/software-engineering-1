package server.map;

public enum EnumTerrain {
    GRASS(1),
    MOUNTAIN(2),
    WATER(5000);

    private final int neededMoves;
    EnumTerrain(int neededMoves) {
        this.neededMoves = neededMoves;
    }
    public int getNeededMoves() { return neededMoves; }
}
