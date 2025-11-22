package group.habooba.backend;

public class User {
    // Protected - عشان Student يقدر يوصل لها
    protected long uid;
    protected String password;
    protected String email;

    // Public - حسب المخطط
    public AttributeMap attributes;
    public long policyId;

    // Package-private constructors
    User() {
    }

    User(long uid, String password, String email) {
        this.uid = uid;
        this.password = password;
        this.email = email;
    }

    // Package-private methods
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

    // Public methods
    public PolicyAttributeMap attributes() {
        return (PolicyAttributeMap) attributes;
    }

    public long policyId() {
        return policyId;
    }
}