package group.habooba.backend;

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