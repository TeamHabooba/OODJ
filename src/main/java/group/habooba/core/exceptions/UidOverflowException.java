package group.habooba.core.exceptions;

public class UidOverflowException extends AuthException {
    public UidOverflowException(String message) {
        super(message);
    }
    public UidOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
