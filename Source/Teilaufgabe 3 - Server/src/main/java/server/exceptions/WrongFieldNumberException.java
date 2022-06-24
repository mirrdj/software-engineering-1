package server.exceptions;

public class WrongFieldNumberException extends GenericExampleException {
    public WrongFieldNumberException(String message){
        super(WrongFieldNumberException.class.getName(), message);
    }
}
