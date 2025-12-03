package group.habooba.core.user;

import group.habooba.core.auth.AttributeMap;
import group.habooba.core.auth.PolicySubject;

public abstract class User implements PolicySubject{

    protected long uid;
    protected String password;
    protected String email;


    public AttributeMap attributes;
    public long policyId;


    protected User() {
    }

    protected User(long uid, String password, String email) {
        this.uid = uid;
        this.password = password;
        this.email = email;
    }


    public long uid() {
        return uid;
    }

    public void uid(long value) {
        this.uid = value;
    }

    public String email() {
        return email;
    }

    public void email(String value) {
        this.email = value;
    }

    public String password() {
        return password;
    }

    public void password(String value) {
        this.password = value;
    }

    @Override
    public AttributeMap attributes() {
        return attributes;
    }

    @Override
    public long policyId() {
        return policyId;
    }
}
