package group.habooba.core.domain;

import group.habooba.core.base.AttributeMap;
import group.habooba.core.base.Attributable;
import group.habooba.core.base.Copyable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static group.habooba.core.base.Utils.asMap;

public final class ComponentResult implements TextSerializable, Attributable, Copyable<ComponentResult> {
    private Component component;
    private double gradePoint;
    private String feedback;
    private boolean finished;
    private StudyTimestamp finishedAt;
    private AttributeMap attributes;

    public ComponentResult(Component component, double gradePoint, String feedback, boolean finished, StudyTimestamp finishedAt, AttributeMap attributes) {
        this.component = component;
        this.gradePoint = gradePoint;
        this.feedback = feedback;
        this.finished = finished;
        this.finishedAt = finishedAt;
        this.attributes = attributes;
    }

    public ComponentResult(Component component, double gradePoint, String feedback, boolean finished, StudyTimestamp finishedAt) {
        this(component, gradePoint, feedback, finished, finishedAt, new AttributeMap());
    }

    public ComponentResult(long componentUid, double gradePoint, String feedback, boolean finished, StudyTimestamp finishedAt) {
        this(new Component(componentUid),  gradePoint, feedback, finished, finishedAt);
    }

    public ComponentResult(ComponentResult other){
        this.component = new Component(other.component);
        this.gradePoint = other.gradePoint;
        this.feedback = other.feedback;
        this.finished = other.finished;
        this.finishedAt = new StudyTimestamp(other.finishedAt);
        this.attributes = new AttributeMap(other.attributes);
    }

    public long componentUid() {
        return component.uid();
    }

    public Component component(){
        return component;
    }

    public double gradePoint() {
        return gradePoint;
    }

    public String feedback() {
        return feedback;
    }

    public boolean finished() {
        return finished;
    }

    public StudyTimestamp finishedAt() {
        return finishedAt;
    }

    @Override
    public AttributeMap attributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ComponentResult) obj;
        return this.component == that.component &&
                Double.doubleToLongBits(this.gradePoint) == Double.doubleToLongBits(that.gradePoint) &&
                Objects.equals(this.feedback, that.feedback) &&
                this.finished == that.finished &&
                Objects.equals(this.finishedAt, that.finishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(component, gradePoint, feedback, finished, finishedAt);
    }

    @Override
    public String toString() {
        return "ComponentResult[" +
                "component=" + component + ", " +
                "gradePoint=" + gradePoint + ", " +
                "feedback=" + feedback + ", " +
                "finished=" + finished + ", " +
                "finishedAt=" + finishedAt + ']';
    }

    @Override
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("componentUid",  component.uid());
        map.put("gradePoint", gradePoint);
        map.put("feedback", feedback);
        map.put("finished", finished);
        map.put("finishedAt", finishedAt);
        map.put("attributes", attributes.toMap());
        return map;
    }

    public static ComponentResult fromMap(Map<String, Object> map){
        Component component = new Component((long) map.get("componentUid"));
        double gradePoint = (double) map.get("gradePoint");
        String feedback = (String) map.get("feedback");
        boolean finished = (boolean) map.get("finished");
        StudyTimestamp finishedAt = StudyTimestamp.fromMap(asMap(map.get("finishedAt")));
        AttributeMap attributes;
        if(map.containsKey("attributes")){
            attributes = AttributeMap.fromMap(asMap(map.get("attributes")));
        } else {
            attributes = new AttributeMap();
            attributes.put("type", "componentResult");
        }
        return new ComponentResult(component, gradePoint, feedback, finished, finishedAt, attributes);
    }

    @Override
    public String toText(){
        return TextSerializer.toTextPretty(toMap());
    }

    public static ComponentResult fromText(String text){
        return fromMap(asMap(TextParser.fromText(text)));
    }

    @Override
    public ComponentResult copy() {
        return new ComponentResult(this);
    }
}
