package server.exceptions;

public class PlayerRegistrationException extends RuntimeException{
    public PlayerRegistrationException(String message){
        super(message);
    }
}
