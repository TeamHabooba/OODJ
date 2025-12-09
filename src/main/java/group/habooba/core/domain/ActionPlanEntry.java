package group.habooba.core.domain;

public class ActionPlanEntry {

    private ActionPlanTask task;
    private StudyTimestamp targetTimestamp;
    private boolean completed;
    private boolean failed;
    private StudyTimestamp completedOn;


    public ActionPlanEntry(ActionPlanTask task, StudyTimestamp targetTimestamp,
                           boolean isCompleted, StudyTimestamp completedOn) {
        this.task = task;
        this.targetTimestamp = targetTimestamp;
        this.completed = isCompleted;
        this.completedOn = completedOn;
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
}