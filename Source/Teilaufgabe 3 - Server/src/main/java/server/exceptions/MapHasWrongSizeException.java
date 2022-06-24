package server.exceptions;

public class MapHasWrongSizeException extends GenericExampleException {
    public MapHasWrongSizeException(String message){
        super(MapHasWrongSizeException.class.getName(), message);
    }
}
