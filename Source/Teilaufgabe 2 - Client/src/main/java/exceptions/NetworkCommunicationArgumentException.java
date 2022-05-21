package exceptions;

public class NetworkCommunicationArgumentException extends Exception{
    public NetworkCommunicationArgumentException(String message) {
        super(message);
    }
    public NetworkCommunicationArgumentException(){
        super("Network communication argument invalid");
    }
}
