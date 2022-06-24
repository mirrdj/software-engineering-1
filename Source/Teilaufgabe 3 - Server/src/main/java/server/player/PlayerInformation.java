package server.player;

public class PlayerInformation {
    private String firstName;
    private String lastName;
    private String uaccount;

    public PlayerInformation(String firstName, String lastName, String uaccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uaccount = uaccount;
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
}
