package group.habooba.backend.exceptions;

public class NoMatchingPolicyException extends PermissionException {

    public NoMatchingPolicyException(String name) {
        super(name);
    }

    public NoMatchingPolicyException(String name, Throwable cause) {
        super(name, cause);
    }
}