package server.exceptions;

public class MapAlreadySetException extends RuntimeException{
    public MapAlreadySetException(String message){
        super(message);
    }
}
