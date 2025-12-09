package group.habooba.core.domain;

import group.habooba.core.Utils;
import group.habooba.core.exceptions.NullValueException;

import java.util.ArrayList;

public record RecoveryMilestone(Enrollment enrollment, ArrayList<ActionPlanEntry> actionPlan) {

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
}