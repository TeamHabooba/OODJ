package group.habooba.backend;

public class PolicyIsImmutableException extends PermissionException {

    public PolicyIsImmutableException(String name) {
        super(name);
    }

    public PolicyIsImmutableException(String name, Throwable cause) {
        super(name, cause);
    }
}