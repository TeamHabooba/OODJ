package group.habooba.core.exceptions;

public final class EmptyParserTextException extends InvalidValueException {
    public EmptyParserTextException(String message) {
        super(message);
    }
    public EmptyParserTextException(String message, Throwable cause) {
        super(message, cause);
    }
}
