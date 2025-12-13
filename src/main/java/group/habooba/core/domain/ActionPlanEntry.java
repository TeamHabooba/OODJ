package group.habooba.core.domain;

import group.habooba.core.Copyable;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.HashMap;
import java.util.Map;

import static group.habooba.core.Utils.asMap;

public class ActionPlanEntry implements Copyable<ActionPlanEntry>, TextSerializable {

    private ActionPlanTask task;
    private StudyTimestamp targetTimestamp;
    private boolean completed;
    private boolean failed;
    private StudyTimestamp completedOn;


    public ActionPlanEntry(){
        this(new ActionPlanTask(), new StudyTimestamp(), false, false, new StudyTimestamp());
    }

    public ActionPlanEntry(ActionPlanTask task, StudyTimestamp targetTimestamp,
                           boolean isCompleted, boolean failed, StudyTimestamp completedOn) {
        this.task = task;
        this.targetTimestamp = targetTimestamp;
        this.completed = isCompleted;
        this.failed = failed;
        this.completedOn = completedOn;
    }

    public ActionPlanEntry(ActionPlanEntry other) {
        this.task = other.task.copy();
        this.targetTimestamp = other.targetTimestamp.copy();
        this.completed = other.completed;
        this.failed = other.failed;
        this.completedOn = other.completedOn.copy();
    }


    public ActionPlanTask task() {
        return task;
    }

    public void task(ActionPlanTask value) {
        this.task = value;
    }

    public StudyTimestamp targetTimestamp() {
        return targetTimestamp;
    }

    public void targetTimestamp(StudyTimestamp value) {
        this.targetTimestamp = value;
    }

    public boolean completed() {
        return completed;
    }

    public void completed(boolean value) {
        this.completed = value;
    }

    private boolean calcFailed(){
        return completed && completedOn.isAfter(targetTimestamp);
    }

    public boolean failed() {
        return failed;
    }

    public void failed(boolean value) {
        this.failed = value;
    }

    public StudyTimestamp completedOn() {
        return completedOn;
    }

    public void completedOn(StudyTimestamp value) {
        this.completedOn = value;
    }

    @Override
    public ActionPlanEntry copy(){
        return new ActionPlanEntry(this);
    }

    @Override
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("task", task.toMap());
        map.put("targetTimestamp", targetTimestamp.toMap());
        map.put("completed", completed);
        map.put("failed", failed);
        map.put("completedOn", completedOn.toMap());
        return map;
    }

    public static ActionPlanEntry fromMap(Map<String, Object> map){
        ActionPlanEntry result = new ActionPlanEntry();
        result.task(ActionPlanTask.fromMap(asMap(map.get("task"))));
        result.targetTimestamp(StudyTimestamp.fromMap(asMap(map.get("targetTimestamp"))));
        result.completed((boolean)map.get("completed"));
        result.completedOn(StudyTimestamp.fromMap(asMap(map.get("completedOn"))));
        if(map.containsKey("failed")){
            result.failed ((boolean) map.get("failed"));
        } else {
            result.failed(result.calcFailed());
        }
        return result;
    }

    @Override
    public String toText(){
        return TextSerializer.toTextPretty(toMap());
    }

    public static ActionPlanEntry fromText(String text){
        return fromMap(asMap(TextParser.fromText(text)));
    }
}