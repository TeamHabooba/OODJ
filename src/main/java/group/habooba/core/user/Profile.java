package group.habooba.core.user;

import group.habooba.core.base.Copyable;

import java.util.HashMap;
import java.util.Map;

public record Profile(String firstName, String lastName) implements Copyable<Profile> {

    public Profile(Profile other){
        this(other.firstName, other.lastName);
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        return map;
    }

    public static Profile fromMap(Map<String, Object> map) {
        return new Profile((String) map.get("firstName"), (String) map.get("lastName"));
    }

    @Override
    public Profile copy() {
        return new Profile(this);
    }
}