package group.habooba.core.user;

import group.habooba.core.Attributable;
import group.habooba.core.AttributeMap;
import group.habooba.core.Copyable;
import group.habooba.core.domain.TextSerializable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;

import static group.habooba.core.Utils.asMap;

public class User implements Attributable, TextSerializable, Copyable<User> {

    protected long uid;
    protected String password;
    protected String email;
    protected Profile profile;
    public AttributeMap attributes;


    public User() {
        this(0x0001_0000_0000_0000L);
    }

    public User(long uid) {this(uid, "", "");}

    public User(long uid, String password) {this(uid, password, "");}

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

    public User(User other){
        this.uid = other.uid;
        this.password = other.password;
        this.email = other.email;
        this.attributes = other.attributes.copy();
        this.profile = other.profile.copy();
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

    public Profile profile() {
        return profile;
    }
    public void profile(Profile profile) {
        this.profile = profile;
    }

    public static User fromMap(Map<String, Object> map){
        var res = new User();
        if(map.get("uid") instanceof Integer){
            res.uid = (long) (int)  map.get("uid");
        } else res.uid = (Long) map.get("uid");
        res.password = (String) map.get("password");
        res.email = (String) map.get("email");
        res.attributes = AttributeMap.fromMap(asMap(map.get("attributes")));
        res.profile = Profile.fromMap(asMap(map.get("profile")));
        return res;
    }

    /**
     * Returns HashMap representation of User
     * @return HashMap representation
     */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapped = new HashMap<>();
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
    public static User fromText(String serialized){
        return User.fromMap(asMap(TextParser.fromText(serialized)));
    }

    /**
     * Serializes User into a formatted String.
     * @return pretty formated String.
     */
    @Override
    public String toText(){
        return TextSerializer.toTextPretty(toMap());
    }

    @Override
    public User copy() {
        return new User(this);
    }
}
