package group.habooba.core.exceptions;

public class NullValueException extends ValueException {
    public NullValueException(String message) {
        super(message);
    }
    public NullValueException(String message, Throwable cause) { super(message, cause); }
}
