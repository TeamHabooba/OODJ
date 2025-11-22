package group.habooba.backend.exceptions;

public class PolicyIsImmutableException extends PermissionException {

    public PolicyIsImmutableException(String name) {
        super(name);
    }

    public PolicyIsImmutableException(String name, Throwable cause) {
        super(name, cause);
    }
}