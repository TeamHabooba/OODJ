package group.habooba.core.domain;

import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;

import static group.habooba.core.Utils.asMap;

public record StudyTimestamp(int year, int semester, int week) implements TextSerializable {
    public StudyTimestamp(){
        this(0, 0, 0);
    }

    public boolean empty(){
        return year == 0 || semester == 0 || week == 0;
    }

    public StudyTimestamp fromMap(Map<String, Object> map){
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
}
