package server.exceptions;

public class MaximumOfPlayersAlreadyRegisteredExeception extends PlayerRegistrationException{
    public MaximumOfPlayersAlreadyRegisteredExeception(){
        super(MaximumOfPlayersAlreadyRegisteredExeception.class.getName(), "Maximum amount of registered players, which is equal to 2, has already been reached");
    }
}
