package group.habooba.core.exceptions;

public final class NullParserTextException extends NullValueException {
    public NullParserTextException(String message) {
        super(message);
    }
    public NullParserTextException(String message, Throwable cause) { super(message,cause); }
}
