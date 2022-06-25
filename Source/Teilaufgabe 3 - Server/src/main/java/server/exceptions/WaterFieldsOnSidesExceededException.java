package server.exceptions;

public class WaterFieldsOnSidesExceededException extends GenericExampleException{
    public WaterFieldsOnSidesExceededException(String message){
        super(WaterFieldsOnSidesExceededException.class.getName(), message);
    }
}
