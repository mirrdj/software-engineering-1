package exceptions;

public class NotFullMapException extends RuntimeException {
    public NotFullMapException(String message) {
        super(message);
    }
    public NotFullMapException(){
        super("Map is not full; This method requires a full map");
    }
}
