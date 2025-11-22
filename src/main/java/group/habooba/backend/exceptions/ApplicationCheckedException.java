package group.habooba.backend.exceptions;

public class ApplicationCheckedException extends Exception {

    public ApplicationCheckedException(String message) {
        super(message);
    }

    public ApplicationCheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}