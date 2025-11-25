package group.habooba.core;

import group.habooba.core.auth.AttributeMap;
import group.habooba.core.auth.PolicySubject;

public class User implements PolicySubject{

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


    protected long uid() {
        return uid;
    }

    protected void uid(long value) {
        this.uid = value;
    }

    protected String email() {
        return email;
    }

    protected void email(String value) {
        this.email = value;
    }

    protected String password() {
        return password;
    }

    protected void password(String value) {
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
