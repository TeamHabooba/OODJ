package group.habooba.core.user;

import java.util.HashMap;
import java.util.Map;

public record Profile(String firstName, String lastName) {

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
}