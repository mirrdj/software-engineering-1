package server.exceptions;

public class WrongFortTerrainException extends GenericExampleException {
    public WrongFortTerrainException(String message){
        super(WrongFortTerrainException.class.getName(), message);
    }
}
