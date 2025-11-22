package group.habooba.backend.exceptions;

public class DataException extends ApplicationUncheckedException {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}