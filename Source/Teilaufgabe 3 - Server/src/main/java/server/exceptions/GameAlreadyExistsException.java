package server.exceptions;

public class GameAlreadyExistsException extends GenericExampleException{
    public GameAlreadyExistsException(String message){
        super(GameAlreadyExistsException.class.getName(), message);
    }
}
