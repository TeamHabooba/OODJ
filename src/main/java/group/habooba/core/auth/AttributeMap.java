package group.habooba.core.auth;

import group.habooba.core.domain.TextSerializable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

import static group.habooba.core.Utils.asMap;

public class AttributeMap implements TextSerializable {

    private final Map<String, Object> attributes;


    public AttributeMap() {
        this.attributes = new HashMap<>();
    }


    public AttributeMap(Map<String, Object> map) {
        this.attributes = new HashMap<>(map);
    }


    public Object get(String key) {
        return attributes.get(key);
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        Object value = attributes.get(key);
        if (value != null && type.isInstance(value)) {
            return Optional.of(type.cast(value));
        }
        return Optional.empty();
    }

    public void put(String key, Object value) {
        attributes.put(key, value);
    }

    public boolean has(String key) {
        return attributes.containsKey(key);
    }

    public Set<String> keys() {
        return attributes.keySet();
    }

    @Override
    public Map<String, Object> toMap() {
        return attributes;
    }

    public static AttributeMap fromMap(Map<String, Object> map) {
        return new AttributeMap(map);
    }

    @Override
    public String toText(){
        return TextSerializer.toTextPretty(toMap());
    }

    public static AttributeMap fromText(String text){
        return fromMap(asMap(TextParser.fromText(text)));
    }
}
