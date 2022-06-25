package server.exceptions;

public class WaterFieldsOnSidesExceededException extends GenericExampleException{
    WaterFieldsOnSidesExceededException(String message){
        super(WaterFieldsOnSidesExceededException.class.getName(), message);
    }
}
