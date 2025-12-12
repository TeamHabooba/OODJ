package group.habooba.core.exceptions;

public class AuthenticationException extends AuthException {
    public AuthenticationException(String message) {
        super(message);
    }
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
