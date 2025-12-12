package group.habooba.core.exceptions;

public class WrongPasswordException extends AuthenticationException {
    public WrongPasswordException(String message) {
        super(message);
    }
    public WrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
