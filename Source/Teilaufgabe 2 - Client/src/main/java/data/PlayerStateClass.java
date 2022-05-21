package data;

public class PlayerStateClass {
    String uniquePlayerID;
    EnumPlayerGameState playerGS;
    boolean treasureCollected;

    public PlayerStateClass(String uniquePlayerID, EnumPlayerGameState playerGameState, boolean treasureCollected) {
        this.uniquePlayerID = uniquePlayerID;
        this.playerGS = playerGameState;
        this.treasureCollected = treasureCollected;
    }

    public String getUniquePlayerID() {
        return uniquePlayerID;
    }
    public EnumPlayerGameState getPlayerGS() {
        return playerGS;
    }
    public boolean hasCollectedTreasure() {
        return treasureCollected;
    }

    public boolean hasWon(){
        return playerGS.equals(EnumPlayerGameState.WON);
    }

    public boolean hasLost(){
        return playerGS.equals(EnumPlayerGameState.LOST);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("\n");
        string.append("Player ID: ").append(uniquePlayerID).append('\n');
        string.append("Player game state: ").append(playerGS).append('\n');
        string.append("Treasure collected: ").append(treasureCollected).append('\n');
        return string.toString();
    }
}
