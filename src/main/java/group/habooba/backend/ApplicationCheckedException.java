package group.habooba.backend;

public class ApplicationCheckedException extends Exception {
    protected String message;

    public ApplicationCheckedException(String message) {
        super(message);
        this.message = message;
    }

    public ApplicationCheckedException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}