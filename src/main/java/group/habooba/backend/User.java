package group.habooba.backend;

public class User {
    protected long uid;
    protected String password;
    protected String name;
    protected String email;
    protected PermissionList permissionList;

    User() {
    }

    User(long uid, String password) {
        this.uid = uid;
        this.password = password;
    }




}
