package server.exceptions;

public class NoPlayerRegisteredException extends PlayerRegistrationException{
    public NoPlayerRegisteredException(){
        super("No player has been registered yet");
    }
}
