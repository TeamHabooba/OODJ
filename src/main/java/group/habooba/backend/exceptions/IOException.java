package group.habooba.backend.exceptions;

public class IOException extends ApplicationCheckedException {

    public IOException(String message) {
        super(message);
    }

    public IOException(String message, Throwable cause) {
        super(message, cause);
    }
}