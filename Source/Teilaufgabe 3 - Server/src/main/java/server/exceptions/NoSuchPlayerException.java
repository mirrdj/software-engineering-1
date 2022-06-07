package server.exceptions;

public class NoSuchPlayerException extends PlayerRegistrationException{
    public NoSuchPlayerException(String message){
        super(NoSuchPlayerException.class.getName(), message);
    }
}
