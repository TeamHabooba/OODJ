package group.habooba.backend;

public class DataException extends ApplicationUncheckedException {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}