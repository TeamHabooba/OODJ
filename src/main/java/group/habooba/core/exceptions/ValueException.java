package group.habooba.core.exceptions;

public class ValueException extends DataException {
    public ValueException(String message) {
        super(message);
    }
    public ValueException(String message, Throwable cause) { super(message, cause); }
}
