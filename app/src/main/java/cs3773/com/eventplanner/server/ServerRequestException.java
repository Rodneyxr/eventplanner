package cs3773.com.eventplanner.server;

/**
 * Created by Rodney on 4/9/2015.
 * <p/>
 * This class is an exception that is to be thrown when something goes wrong with server communication
 */
public class ServerRequestException extends Exception {
    public ServerRequestException(String message) {
        super(message);
    }
}
