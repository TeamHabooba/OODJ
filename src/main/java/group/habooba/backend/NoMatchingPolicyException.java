package group.habooba.backend;

public class NoMatchingPolicyException extends PermissionException {

    public NoMatchingPolicyException(String name) {
        super(name);
    }

    public NoMatchingPolicyException(String name, Throwable cause) {
        super(name, cause);
    }
}