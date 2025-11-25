package group.habooba.core.exceptions;

public class ApplicationUncheckedException extends RuntimeException {

    public ApplicationUncheckedException(String message) {
        super(message);
    }

    public ApplicationUncheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}