package group.habooba.backend;

public class PermissionListIsImmutableException extends PermissionException {

    public PermissionListIsImmutableException(String name) {
        super(name);
    }

    public PermissionListIsImmutableException(String name, Throwable cause) {
        super(name, cause);
    }
}