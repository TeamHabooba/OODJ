package group.habooba.core.domain;

import group.habooba.core.exceptions.InvalidValueException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Enrollment implements TextSerializable {
    private Course course;
    private ArrayList<ComponentResult> results;
    private double requiredGrade;

    Enrollment(){
        course = null;
        results = null;
        requiredGrade = 0;
    }

    Enrollment(Course course, ArrayList<ComponentResult> results, SchoolOfStudy schoolOfStudy,
                      double requiredGrade, StudyTimestamp firstWeek, StudyTimestamp lastWeek) {
        this.course = course;
        this.results = results;
        this.requiredGrade = requiredGrade;
    }

    Course course() {
        return course;
    }

    void course(Course course) {
        this.course = course;
    }

    ArrayList<ComponentResult> results() {
        return results;
    }

    void results(ArrayList<ComponentResult> results) {
        this.results = results;
    }

    double requiredGrade() {
        return requiredGrade;
    }

    void requiredGrade(double requiredGrade) {
        this.requiredGrade = requiredGrade;
    }


    /**
     * Returns current enrollment grade.
     * <p/>
     * Goes through all the results.
     * Gets weight percentage from course.componentById().
     * Multiplies result grade and result weight percentage.
     * Sums all the multiplied results up.
     * Returns the sum.
     *
     * @return current course grade.
     * @throws InvalidValueException when meets invalid componentId in `results` list.
     */
    public double currentGrade(){
        double sum = 0;
        Optional<Component> component;
        for(var result : results){
            component = course.componentByUid(result.componentUid());
            if(component.isEmpty())
                throw new InvalidValueException(String.format("Invalid component id: %d", result.componentUid()));
            sum += component.get().weightPercent() * result.gradePoint() / 100;
        }
        return sum;
    }

    @Override
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("courseUid",  course.uid());

        return map;
    }
}
