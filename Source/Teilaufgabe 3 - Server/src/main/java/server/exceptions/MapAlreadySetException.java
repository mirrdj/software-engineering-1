package server.exceptions;

public class MapAlreadySetException extends GenericExampleException{
    public MapAlreadySetException(String message){
        super(MapAlreadySetException.class.getName(), message);
    }
}
