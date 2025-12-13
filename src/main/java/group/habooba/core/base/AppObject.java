package group.habooba.core.base;

public abstract class AppObject implements Attributable {
    protected long uid;
    protected AttributeMap attributes;

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
}
