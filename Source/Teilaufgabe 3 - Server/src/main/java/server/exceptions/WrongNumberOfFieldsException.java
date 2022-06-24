package server.exceptions;

public class WrongNumberOfFieldsException extends GenericExampleException {
    public WrongNumberOfFieldsException(String message){
        super(WrongNumberOfFieldsException.class.getName(), message);
    }
}
