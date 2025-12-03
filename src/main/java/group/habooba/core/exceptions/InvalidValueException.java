package group.habooba.core.exceptions;

public class InvalidValueException extends ValueException {
    public InvalidValueException(String message) {
        super(message);
    }
    public InvalidValueException(String message, Throwable cause) { super(message, cause); }
}
