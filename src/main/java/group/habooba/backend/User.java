package group.habooba.backend;

public class User {

    protected long uid;
    protected String password;
    protected String email;


    public AttributeMap attributes;
    public long policyId;


    User() {
    }

    User(long uid, String password, String email) {
        this.uid = uid;
        this.password = password;
        this.email = email;
    }


    long uid() {
        return uid;
    }

    void uid(long value) {
        this.uid = value;
    }

    AttributeMap attributes() {
        return attributes;
    }

    String email() {
        return email;
    }

    void email(String value) {
        this.email = value;
    }

    String password() {
        return password;
    }

    void password(String value) {
        this.password = value;
    }


    public PolicyAttributeMap attributes() {
        return (PolicyAttributeMap) attributes;
    }

    public long policyId() {
        return policyId;
    }
}