package group.habooba.backend.student;

public class ActionPlanEntry {

    private ActionPlanTask task;
    private StudyTimestamp targetTimestamp;
    private boolean isCompleted;
    private StudyTimestamp completedOn;


    public ActionPlanEntry(ActionPlanTask task, StudyTimestamp targetTimestamp,
                           boolean isCompleted, StudyTimestamp completedOn) {
        this.task = task;
        this.targetTimestamp = targetTimestamp;
        this.isCompleted = isCompleted;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void isCompleted(boolean value) {
        this.isCompleted = value;
    }

    public StudyTimestamp completedOn() {
        return completedOn;
    }

    public void completedOn(StudyTimestamp value) {
        this.completedOn = value;
    }
}