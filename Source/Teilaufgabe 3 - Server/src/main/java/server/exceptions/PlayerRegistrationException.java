package server.exceptions;

public class PlayerRegistrationException extends GenericExampleException{
    public PlayerRegistrationException(String errorName, String message){
        super(errorName, message);
    }
}
