package group.habooba.backend;

public class ApplicationUncheckedException extends RuntimeException {
    protected String message;

    public ApplicationUncheckedException(String message) {
        super(message);
        this.message = message;
    }

    public ApplicationUncheckedException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}