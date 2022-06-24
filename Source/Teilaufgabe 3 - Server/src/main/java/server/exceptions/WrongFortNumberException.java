package server.exceptions;

public class WrongFortNumberException extends GenericExampleException{
    public WrongFortNumberException(String message){
        super(WrongFortNumberException.class.getName(), message);
    }
}
