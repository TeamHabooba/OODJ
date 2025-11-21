package group.habooba.backend;

public class PermissionRecordDoesNotExistException extends PermissionException {

    public PermissionRecordDoesNotExistException(String name) {
        super(name);
    }

    public PermissionRecordDoesNotExistException(String name, Throwable cause) {
        super(name, cause);
    }
}