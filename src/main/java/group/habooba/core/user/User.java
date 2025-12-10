package group.habooba.core.user;

import group.habooba.core.auth.AttributeMap;
import group.habooba.core.auth.PolicySubject;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

public class User implements PolicySubject {

    protected long uid;
    protected String password;
    protected String email;
    protected Profile profile;
    public AttributeMap attributes;


    public User() {
        this(0x0001_0000_0000_0000L);
    }

    public User(long uid) {this(uid, "", "");}

    public User(long uid, String password, String email) {
        this(uid, password, email, new AttributeMap());
    }

    public User(long uid, String password, String email, AttributeMap attributes) {
        this(uid, password, email, attributes, new Profile("", ""));
    }

    public User(long uid, String password, String email, AttributeMap attributes, Profile profile){
        this.uid = uid;
        this.password = password;
        this.email = email;
        this.attributes = attributes;
        this.profile = profile;
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
        return uid;
    }

    public Profile profile() {
        return profile;
    }
    public void profile(Profile profile) {
        this.profile = profile;
    }

    public static User fromMap(Map<String, Object> map){
        var res = new User();
        res.uid = (Long) map.get("uid");
        res.password = (String) map.get("password");
        res.email = (String) map.get("email");
        res.attributes = AttributeMap.fromMap((Map<String, Object>) map.get("attributes"));
        res.profile = Profile.fromMap((Map<String, Object>) map.get("profile"));
        return res;
    }

    /**
     * Returns LinkedHashMap representation of User
     * @return LinkedHashMap representation
     */
    public Map<String, Object> toMap() {
        Map<String, Object> mapped = new LinkedHashMap<>();
        mapped.put("uid", this.uid());
        mapped.put("password", this.password());
        mapped.put("email", this.email());
        mapped.put("attributes", this.attributes().toMap());
        mapped.put("profile", this.profile().toMap());
        return mapped;
    }

    /**
     * Creates new User instance from String.
     * @param serialized - String. Must be parseable by TextParser class.
     * @return User instance
     */
    public static User fromString(String serialized){
        return User.fromMap((Map<String, Object>) new TextParser(serialized).parse());
    }

    /**
     * Serializes User into a formatted String.
     * @return pretty formated String.
     */
    public String toString(){
        return new TextSerializer(true).serialize(this.toMap());
    }
}
