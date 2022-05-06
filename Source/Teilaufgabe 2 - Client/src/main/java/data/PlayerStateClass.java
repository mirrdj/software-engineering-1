package data;

import java.util.Objects;

public class PlayerStateClass {
    String uniquePlayerID;
    EnumPlayerGameState playerGameState;
    boolean treasureCollected;

    public PlayerStateClass(String uniquePlayerID) {
        this.uniquePlayerID = uniquePlayerID;
    }

    public PlayerStateClass(String uniquePlayerID, EnumPlayerGameState playerGameState, boolean treasureCollected) {
        this.uniquePlayerID = uniquePlayerID;
        this.playerGameState = playerGameState;
        this.treasureCollected = treasureCollected;
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
        string += "Player game state: " + playerGameState;
        string += "Treasure collected: " + treasureCollected;

        return string;
    }

    public EnumPlayerGameState getState() {
        return  playerGameState;
    }
    public boolean hasCollectedTreasure() {
        return treasureCollected;
    }
}
