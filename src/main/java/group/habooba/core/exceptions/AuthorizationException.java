package group.habooba.core.exceptions;

public class AuthorizationException extends AuthException {
    public AuthorizationException(String message) {
        super(message);
    }
    public AuthorizationException(String message, Throwable cause) { super(message, cause); }
}
