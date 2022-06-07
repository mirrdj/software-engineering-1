package server.exceptions;

public class TwoPlayersAlreadyRegisteredExeception extends PlayerRegistrationException{
    public TwoPlayersAlreadyRegisteredExeception(){
        super("Maximum amount of registered players, which is equal to 2, has already been reached");
    }
}
