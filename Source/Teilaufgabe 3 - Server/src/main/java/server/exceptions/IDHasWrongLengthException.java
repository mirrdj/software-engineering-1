package server.exceptions;

public class IDHasWrongLengthException extends GenericExampleException{
    public IDHasWrongLengthException(){
        super(IDHasWrongLengthException.class.getName(), "ID has wrong length");
    }
}
