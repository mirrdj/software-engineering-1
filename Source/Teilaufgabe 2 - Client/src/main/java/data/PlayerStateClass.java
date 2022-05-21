package data;

import java.util.Objects;

public class PlayerStateClass {
    String uniquePlayerID;
    EnumPlayerGameState playerGS;
    boolean treasureCollected;

    public PlayerStateClass(String uniquePlayerID) {
        this.uniquePlayerID = uniquePlayerID;
    }

    public PlayerStateClass(String uniquePlayerID, EnumPlayerGameState playerGameState, boolean treasureCollected) {
        this.uniquePlayerID = uniquePlayerID;
        this.playerGS = playerGameState;
        this.treasureCollected = treasureCollected;
    }

    public void setPlayerStateClass(PlayerStateClass updatedPlayerState){
        EnumPlayerGameState newPlayerGS = updatedPlayerState.getPlayerGS();
        boolean collected = updatedPlayerState.hasCollectedTreasure();

        this.setPlayerGS(newPlayerGS);
        this.setTreasureCollected(collected);
    }

    public void setPlayerGS(EnumPlayerGameState playerGS) {
        this.playerGS = playerGS;
    }

    public void setTreasureCollected(boolean treasureCollected) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStateClass that = (PlayerStateClass) o;
        return Objects.equals(uniquePlayerID, that.uniquePlayerID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniquePlayerID);
    }

    @Override
    public String toString() {
        String string = "";
        string += "Player game state: " + playerGS + '\n';
        string += "Treasure collected: " + treasureCollected + '\n';

        return string;
    }
}
