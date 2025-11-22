package group.habooba.backend;

public class PermissionException extends ApplicationUncheckedException {

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}

