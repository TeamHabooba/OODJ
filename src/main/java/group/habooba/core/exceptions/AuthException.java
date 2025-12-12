package group.habooba.core.exceptions;

public class AuthException extends ApplicationUncheckedException {
    public AuthException(String message) {
        super(message);
    }
    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
