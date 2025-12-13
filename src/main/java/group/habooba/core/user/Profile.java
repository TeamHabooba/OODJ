package group.habooba.core.user;

import group.habooba.core.base.AppObject;
import group.habooba.core.base.Copyable;
import group.habooba.core.domain.TextSerializable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static group.habooba.core.base.Utils.asMap;

public final class Profile extends AppObject<Profile> {
    private final String firstName;
    private final String lastName;

    public Profile(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Profile(Profile other) {
        this(other.firstName, other.lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
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
    public String toText() {
        return TextSerializer.toTextPretty(toMap());
    }

    public static Profile fromText(String text) {
        return fromMap(asMap(TextParser.fromText(text)));
    }

    @Override
    public Profile copy() {
        return new Profile(this);
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Profile) obj;
        return Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Profile[" +
                "firstName=" + firstName + ", " +
                "lastName=" + lastName + ']';
    }

}