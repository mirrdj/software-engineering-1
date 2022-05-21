package exceptions;

public class WrongIdentificationDetailsException extends Exception{
    public WrongIdentificationDetailsException(String message) {
        super(message);
    }
    public WrongIdentificationDetailsException(){
        super("Identification argument invalid");
    }
}
