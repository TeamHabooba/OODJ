package group.habooba.core.exceptions;

public class PermissionException extends AuthorizationException {

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}

