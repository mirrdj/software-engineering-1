package server.exceptions;

public class IDHasWrongLengthException extends RuntimeException{
    public IDHasWrongLengthException(){
        super("ID has wrong length");
    }
}
