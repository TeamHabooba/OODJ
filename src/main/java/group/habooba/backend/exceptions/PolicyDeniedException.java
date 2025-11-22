package group.habooba.backend.exceptions;

public class PolicyDeniedException extends PermissionException {

    public PolicyDeniedException(String name)
    {
        super(name);
    }

    public PolicyDeniedException(String name, Throwable cause)
    {
        super(name, cause);
    }
}