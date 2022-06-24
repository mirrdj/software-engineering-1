package server.player;

import server.uniqueID.PlayerID;

public class Player {
    private String firstName;
    private String lastName;
    private String uaccount;
    private String playerID;
    private EnumPlayerGameState playerGameState = EnumPlayerGameState.MUST_WAIT;
    private boolean collectedTreasure = false;

    public Player(PlayerID playerID, PlayerInformation playerInformation) {
        this.playerID = playerID.getID();
        this.firstName = playerInformation.getFirstName();
        this.lastName = playerInformation.getLastName();
        this.uaccount = playerInformation.getUaccount();
    }

    public Player(PlayerID playerID, String firstName, String lastName, String uaccount, EnumPlayerGameState playerGameState, boolean collectedTreasure) {
        this.playerID = playerID.getID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.uaccount = uaccount;
        this.playerGameState = playerGameState;
        this.collectedTreasure = collectedTreasure;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUaccount() {
        return uaccount;
    }

    public String getPlayerID() {
        return playerID;
    }

    public EnumPlayerGameState getPlayerGameState() {
        return playerGameState;
    }
    public void setPlayerGameState(EnumPlayerGameState playerGameState){
        this.playerGameState = playerGameState;
    }


    public boolean hasCollectedTreasure() {
        return collectedTreasure;
    }

    public void setCollectedTreasure(boolean collectedTreasure) {
        this.collectedTreasure = collectedTreasure;
    }

}
