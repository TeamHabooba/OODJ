package group.habooba.core.domain;

import group.habooba.core.Copyable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static group.habooba.core.Utils.asMap;

public final class Component implements TextSerializable, Copyable<Component> {
    private final long uid;
    private final String name;
    private final int weightPercent;
    private final double requiredGradePoint;
    private final boolean required;

    public Component(
            long uid,
            String name,
            int weightPercent,
            double requiredGradePoint,
            boolean required
    ) {
        this.uid = uid;
        this.name = name;
        this.weightPercent = weightPercent;
        this.requiredGradePoint = requiredGradePoint;
        this.required = required;
    }

    public Component(long uid) {
        this(uid, "", 0, 0.0, false);
    }

    public Component(Component other){
        this(other.uid, other.name, other.weightPercent, other.requiredGradePoint, other.required);
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("name", name);
        map.put("weightPercent", weightPercent);
        map.put("requiredGradePoint", requiredGradePoint);
        map.put("required", required);
        return map;
    }

    public static Component fromMap(Map<String, Object> map) {
        long uid = (long) map.get("uid");
        String name = (String) map.get("name");
        int weightPercent = (int) map.get("weightPercent");
        double requiredGradePoint = (double) map.get("requiredGradePoint");
        boolean required = (boolean) map.get("required");
        return new Component(uid, name, weightPercent, requiredGradePoint, required);
    }

    @Override
    public String toText() {
        return TextSerializer.toTextPretty(toMap());
    }

    public static Component fromText(String string) {
        return fromMap(asMap(TextParser.fromText(string)));
    }


    public long uid() {
        return uid;
    }

    public String name() {
        return name;
    }

    public int weightPercent() {
        return weightPercent;
    }

    public double requiredGradePoint() {
        return requiredGradePoint;
    }

    public boolean required() {
        return required;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Component) obj;
        return this.uid == that.uid &&
                Objects.equals(this.name, that.name) &&
                this.weightPercent == that.weightPercent &&
                Double.doubleToLongBits(this.requiredGradePoint) == Double.doubleToLongBits(that.requiredGradePoint) &&
                this.required == that.required;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, name, weightPercent, requiredGradePoint, required);
    }

    @Override
    public String toString() {
        return "Component[" +
                "uid=" + uid + ", " +
                "name=" + name + ", " +
                "weightPercent=" + weightPercent + ", " +
                "requiredGradePoint=" + requiredGradePoint + ", " +
                "required=" + required + ']';
    }

    @Override
    public Component copy() {
        return new Component(this);
    }
}
