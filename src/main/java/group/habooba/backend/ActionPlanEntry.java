package group.habooba.backend;

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


    public String name() {
        return task.name();
    }

    public void description() {
        task.description();
    }
}