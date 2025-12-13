package group.habooba.core.domain;

import group.habooba.core.base.Copyable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;

import static group.habooba.core.base.Utils.asMap;

public record ActionPlanTask(String name, String description) implements Copyable<ActionPlanTask>, TextSerializable{
    public ActionPlanTask(ActionPlanTask other){
        this(other.name, other.description);
    }

    public ActionPlanTask(){
        this("", "");
    }

    @Override
    public ActionPlanTask copy() {
        return new ActionPlanTask(this);
    }

    @Override
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("description", description);
        return map;
    }

    public static ActionPlanTask fromMap(Map<String, Object> map){
        String name = (String) map.get("name");
        String description = (String) map.get("description");
        return new ActionPlanTask(name, description);
    }

    @Override
    public String toText(){
        return TextSerializer.toTextPretty(toMap());
    }

    public static ActionPlanTask fromText(String text){
        return fromMap(asMap(TextParser.fromText(text)));
    }
}