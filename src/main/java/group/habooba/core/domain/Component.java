package group.habooba.core.domain;

import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;

public record Component(
        long uid,
        String name,
        int weightPercent,
        double requiredGradePoint,
        boolean required
) {

    public Map<String, Object> toMap(Course course) {
        Map<String, Object> map = new HashMap<>();
        map.put("courseUid", course.uid());
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

    public String toString(Course course) {
        return TextSerializer.toTextPretty(toMap(course));
    }

    public static Component fromString(String string){
        return fromMap((Map<String, Object>)TextParser.fromText(string));
    }
}
