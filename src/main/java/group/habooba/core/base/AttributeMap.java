package group.habooba.core.base;

import group.habooba.core.domain.TextSerializable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

import static group.habooba.core.base.Utils.asMap;
import static group.habooba.core.base.Utils.deepCopy;

public class AttributeMap implements TextSerializable, Copyable<AttributeMap> {

    private Map<String, Object> attributes;

    public AttributeMap() {
        this.attributes = new HashMap<>();
    }

    public AttributeMap(Map<String, Object> map) {
        this.attributes = new HashMap<>(map);
    }

    public AttributeMap(AttributeMap other){
        if(other == null) {
            this.attributes = new HashMap<>();
        } else if(other.attributes == null ||  other.attributes.isEmpty()) {
            this.attributes = new HashMap<>();
        } else {
            attributes = new HashMap<>();
            for (Map.Entry<String, Object> entry : other.attributes.entrySet()) {
                attributes.put(entry.getKey(), deepCopy(entry.getValue()));
            }
        }
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

    public AttributeMap copy() {
        return new AttributeMap(this);
    }
}
