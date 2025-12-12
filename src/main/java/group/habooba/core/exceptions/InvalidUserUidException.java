package group.habooba.core.exceptions;

public class InvalidUserUidException extends AuthenticationException {
    public InvalidUserUidException(String message) {
        super(message);
    }
    public InvalidUserUidException(String message, Throwable cause) {
        super(message, cause);
    }
}
