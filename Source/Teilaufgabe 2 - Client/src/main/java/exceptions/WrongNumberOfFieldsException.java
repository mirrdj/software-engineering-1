package exceptions;

public class WrongNumberOfFieldsException extends RuntimeException {
    public WrongNumberOfFieldsException(String message) {
        super(message);
    }
    public WrongNumberOfFieldsException(){
        super("Not enought fields of this type");
    }
}
