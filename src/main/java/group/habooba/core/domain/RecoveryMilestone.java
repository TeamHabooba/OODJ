package group.habooba.core.domain;

import group.habooba.core.Copyable;
import group.habooba.core.Utils;
import group.habooba.core.exceptions.NullValueException;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.*;

import static group.habooba.core.Utils.asMap;
import static group.habooba.core.Utils.deepCopy;

public final class RecoveryMilestone implements TextSerializable, Copyable<RecoveryMilestone> {
    private Enrollment enrollment;
    private int id;
    private List<ActionPlanEntry> actionPlan;

    public RecoveryMilestone(Enrollment enrollment, int id, List<ActionPlanEntry> actionPlan) {
        this.enrollment = enrollment;
        this.id = id;
        this.actionPlan = actionPlan;
    }

    public RecoveryMilestone(RecoveryMilestone other) {
        this(new Enrollment(other.enrollment), other.id, (List<ActionPlanEntry>) deepCopy(other.actionPlan));
    }

    public RecoveryMilestone() {
        this(new Enrollment(), 0, new ArrayList<>());
    }

    /**
     * Compares current enrollment grade point with required
     *
     * @return true if current >= required, otherwise - false
     */
    public boolean requiredGradeReached() {
        if (enrollment == null)
            throw new NullValueException("enrollment is null");
        return Utils.almostEqual(enrollment.currentGrade(), enrollment.requiredGrade(), 1e-4);
    }

    /**
     * Checks if any step of recovery plan was failed
     *
     * @return true if at least one of action plan entries was failed, otherwise - false
     */
    public boolean failed() {
        for (var entry : actionPlan) {
            if (entry.failed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("enrollmentUid", enrollment.uid());
        map.put("id", id);
        map.put("actionPlan", actionPlan);
        return map;
    }

    public static RecoveryMilestone fromMap(Map<String, Object> map) {
        var result = new RecoveryMilestone();
        result.enrollment = new Enrollment((long) map.get("enrollmentUid"));
        result.id = (int) map.get("id");
        result.actionPlan = new ArrayList<>();
        var objectActions = (ArrayList<Map<String, Object>>) map.get("actionPlan");
        for(var entry : objectActions){
            result.actionPlan.add(ActionPlanEntry.fromMap(entry));
        }
        return result;
    }

    @Override
    public String toText() {
        return TextSerializer.toTextPretty(toMap());
    }

    public static RecoveryMilestone fromText(String text) {
        return fromMap(asMap(TextParser.fromText(text)));
    }

    @Override
    public RecoveryMilestone copy() {
        return new RecoveryMilestone(this);
    }

    public Enrollment enrollment() {
        return enrollment;
    }

    public int id() {
        return id;
    }

    public List<ActionPlanEntry> actionPlan() {
        return actionPlan;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RecoveryMilestone) obj;
        return Objects.equals(this.enrollment, that.enrollment) &&
                this.id == that.id &&
                Objects.equals(this.actionPlan, that.actionPlan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollment, id, actionPlan);
    }

    @Override
    public String toString() {
        return "RecoveryMilestone[" +
                "enrollment=" + enrollment + ", " +
                "id=" + id + ", " +
                "actionPlan=" + actionPlan + ']';
    }

}