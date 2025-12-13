package group.habooba.core.domain;

import group.habooba.core.Copyable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;

import static group.habooba.core.Utils.asMap;

public record StudyTimestamp(int year, int semester, int week) implements TextSerializable, Copyable<StudyTimestamp>, Comparable<StudyTimestamp> {
    public StudyTimestamp(){
        this(0, 0, 0);
    }

    public StudyTimestamp(StudyTimestamp other){
        this(other.year, other.semester, other.week);
    }

    public boolean empty(){
        return year == 0 || semester == 0 || week == 0;
    }

    public static StudyTimestamp fromMap(Map<String, Object> map){
        int year = (Integer) map.get("year");
        int semester = (Integer) map.get("semester");
        int week = (Integer) map.get("week");
        return new StudyTimestamp(year, semester, week);
    }

    public StudyTimestamp fromText(String text){
        return fromMap(asMap(TextParser.fromText(text)));
    }

    @Override
    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("semester", semester);
        map.put("week", week);
        return map;
    }

    @Override
    public String toText() {
        return TextSerializer.toTextPretty(toMap());
    }

    @Override
    public StudyTimestamp copy() {
        return new StudyTimestamp(this);
    }

    @Override
    public int compareTo(StudyTimestamp other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare with null");
        }

        int cmp = Integer.compare(this.year, other.year);
        if (cmp != 0) return cmp;

        cmp = Integer.compare(this.semester, other.semester);
        if (cmp != 0) return cmp;

        return Integer.compare(this.week, other.week);
    }

    public boolean isBefore(StudyTimestamp other) {
        return compareTo(other) < 0;
    }

    public boolean isAfter(StudyTimestamp other) {
        return compareTo(other) > 0;
    }

}
