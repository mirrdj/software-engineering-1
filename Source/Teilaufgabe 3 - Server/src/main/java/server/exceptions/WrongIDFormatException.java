package server.exceptions;

public class WrongIDFormatException extends GenericExampleException{
    public WrongIDFormatException(String message){
        super(WrongIDFormatException.class.getName(), message);
    }
}
