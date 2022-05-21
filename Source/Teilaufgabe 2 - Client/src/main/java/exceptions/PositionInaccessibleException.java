package exceptions;

public class PositionInaccessibleException extends RuntimeException{
    public PositionInaccessibleException(String message){
        super(message);
    }

    public PositionInaccessibleException(){
        super("This position can not be accessed");
    }

}
