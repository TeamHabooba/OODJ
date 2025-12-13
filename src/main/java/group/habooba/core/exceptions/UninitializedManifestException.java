package group.habooba.core.exceptions;

public class UninitializedManifestException extends NullValueException {
    public UninitializedManifestException(String message) {
        super(message);
    }
    public UninitializedManifestException(String message, Throwable cause) {
        super(message, cause);
    }
}
