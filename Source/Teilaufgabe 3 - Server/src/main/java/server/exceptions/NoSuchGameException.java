package server.exceptions;

public class NoSuchGameException extends GenericExampleException {
    public NoSuchGameException(String message){
        super(NoSuchGameException.class.getName(), message);
    }
}
