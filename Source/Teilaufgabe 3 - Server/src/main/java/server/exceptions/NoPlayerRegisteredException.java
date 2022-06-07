package server.exceptions;

public class NoPlayerRegisteredException extends PlayerRegistrationException{
    public NoPlayerRegisteredException(){
        super(NoPlayerRegisteredException.class.getName(), "No player has been registered yet");
    }
}
