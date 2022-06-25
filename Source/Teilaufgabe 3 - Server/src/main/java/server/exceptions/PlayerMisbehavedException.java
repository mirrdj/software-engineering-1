package server.exceptions;

public class PlayerMisbehavedException extends GenericExampleException{
    public PlayerMisbehavedException(String errorMessage) {
        super(PlayerMisbehavedException.class.getName(), errorMessage);
    }
}
