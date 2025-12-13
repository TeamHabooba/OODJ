package group.habooba.core.base;

import group.habooba.core.domain.TextSerializable;

import java.util.Map;

public abstract class AppObject<T extends AppObject<T>> implements Attributable, TextSerializable, Copyable<T> {
    protected long uid;
    protected AttributeMap attributes;

    @Override
    public AttributeMap attributes(){
        return this.attributes;
    }
    public void attributes(AttributeMap value){
        this.attributes = value;
    }

    public long uid(){
        return this.uid;
    }
    public void uid(long value){
        this.uid = value;
    }

    public String type(){
        if(!attributes.has("type")) return null;
        return (String) this.attributes.get("type");
    }
    public void type(String value){
        this.attributes.put("type", value);
    }

    @Override
    public abstract Map<String, Object> toMap();
    @Override
    public abstract String toText();
    @Override
    public abstract T copy();
}
