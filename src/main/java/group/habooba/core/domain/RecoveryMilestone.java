package group.habooba.core.domain;

import group.habooba.core.base.*;
import group.habooba.core.exceptions.NullValueException;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.*;

import static group.habooba.core.base.Utils.asMap;
import static group.habooba.core.base.Utils.deepCopy;

public final class RecoveryMilestone extends AppObject<RecoveryMilestone> {
    private long uid;
    private Enrollment enrollment;
    private int id;
    private List<ActionPlanEntry> actionPlan;
    private AttributeMap attributes;

    public RecoveryMilestone(long uid, Enrollment enrollment, int id, List<ActionPlanEntry> actionPlan, AttributeMap attributes) {
        this.uid = uid;
        this.enrollment = enrollment;
        this.id = id;
        this.actionPlan = actionPlan;
        this.attributes = attributes;
    }

    public RecoveryMilestone(RecoveryMilestone other) {
        this(other.uid, new Enrollment(other.enrollment), other.id, (List<ActionPlanEntry>) deepCopy(other.actionPlan), other.attributes.copy());
    }

    public RecoveryMilestone() {
        this(0L, new Enrollment(), 0, new ArrayList<>(), new AttributeMap());
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
        map.put("uid", uid);
        map.put("enrollmentUid", enrollment.uid());
        map.put("id", id);
        map.put("actionPlan", actionPlan);
        map.put("attributes", attributes.toMap());
        return map;
    }

    public static RecoveryMilestone fromMap(Map<String, Object> map) {
        var result = new RecoveryMilestone();
        if(map.containsKey("uid")){
            if(map.get("uid") instanceof Integer)
                result.uid = (long) (int) map.get("uid");
            else
                result.uid = (long) map.get("uid");
        } else {
            result.uid = -1;
        }
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


    public long uid(){
        return uid;
    }

    public void uid(long value){
        this.uid = value;
    }

    public Enrollment enrollment() {
        return enrollment;
    }

    public void enrollment(Enrollment value){
        this.enrollment = value;
    }

    public int id() {
        return id;
    }

    public void id(int value){
        this.id = value;
    }

    public List<ActionPlanEntry> actionPlan() {
        return actionPlan;
    }

    public void actionPlan(List<ActionPlanEntry> value){
        this.actionPlan = value;
    }

    public AttributeMap attributes() {
        return attributes;
    }

    public void attributes(AttributeMap value){
        this.attributes = value;
    }

}