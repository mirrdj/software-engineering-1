package exceptions;

public class PlacedOnWrongFieldException extends RuntimeException {
    public PlacedOnWrongFieldException(String message){ super(message); }
    public PlacedOnWrongFieldException(){
        super("Game object was placed on the wrong type of field");
    }
}
