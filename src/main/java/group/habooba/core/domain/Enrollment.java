package group.habooba.core.domain;

import group.habooba.core.exceptions.InvalidValueException;

import java.util.ArrayList;
import java.util.Optional;

public class Enrollment {
    private Course course;
    private ArrayList<ComponentResult> results;
    private SchoolOfStudy schoolOfStudy;
    private double requiredGrade;
    private StudyTimestamp firstWeek;
    private StudyTimestamp lastWeek;

    Enrollment(){
        course = null;
        results = null;
        schoolOfStudy = SchoolOfStudy.NONE;
        requiredGrade = 0;
        firstWeek = null;
        lastWeek = null;
    }

    Enrollment(Course course, ArrayList<ComponentResult> results, SchoolOfStudy schoolOfStudy,
                      double requiredGrade, StudyTimestamp firstWeek, StudyTimestamp lastWeek) {
        this.course = course;
        this.results = results;
        this.schoolOfStudy = schoolOfStudy;
        this.requiredGrade = requiredGrade;
        this.firstWeek = firstWeek;
        this.lastWeek = lastWeek;
    }

    Course getCourse() {
        return course;
    }

    void setCourse(Course course) {
        this.course = course;
    }

    ArrayList<ComponentResult> getResults() {
        return results;
    }

    void setResults(ArrayList<ComponentResult> results) {
        this.results = results;
    }

    SchoolOfStudy getSchoolOfStudy() {
        return schoolOfStudy;
    }

    void setSchoolOfStudy(SchoolOfStudy schoolOfStudy) {
        this.schoolOfStudy = schoolOfStudy;
    }

    double getRequiredGrade() {
        return requiredGrade;
    }

    void setRequiredGrade(double requiredGrade) {
        this.requiredGrade = requiredGrade;
    }

    StudyTimestamp getFirstWeek() {
        return firstWeek;
    }

    void setFirstWeek(StudyTimestamp firstWeek) {
        this.firstWeek = firstWeek;
    }

    StudyTimestamp getLastWeek() {
        return lastWeek;
    }

    void setLastWeek(StudyTimestamp lastWeek) {
        this.lastWeek = lastWeek;
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
        for(var res : results){
            component = course.componentById(res.componentId());
            if(component.isEmpty())
                throw new InvalidValueException(String.format("Invalid component id: %d", res.componentId()));
            sum += component.get().weightPercent() * res.grade();
        }
        return sum;
    }
}
