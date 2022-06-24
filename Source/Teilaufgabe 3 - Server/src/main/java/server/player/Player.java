package server.player;

import server.uniqueID.PlayerID;

public class Player {
    String firstName;
    String lastName;
    String uaccount;
    private String playerID;
    private EnumPlayerGameState playerGameState;
    public Player(PlayerID playerID, PlayerInformation playerInformation) {
        this.playerID = playerID.getID();
        this.firstName = playerInformation.getFirstName();
        this.lastName = playerInformation.getLastName();
        this.uaccount = playerInformation.getUaccount();
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


}
