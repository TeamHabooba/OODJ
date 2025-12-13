package group.habooba.core.domain;

import group.habooba.core.AttributeMap;
import group.habooba.core.Attributable;
import group.habooba.core.Copyable;
import group.habooba.core.exceptions.InvalidValueException;
import group.habooba.core.repository.TextSerializer;

import java.util.*;

import static group.habooba.core.Utils.asMap;
import static group.habooba.core.Utils.deepCopy;

public class Enrollment implements TextSerializable, Attributable, Copyable<Enrollment> {
    private final long uid;
    private long studentUid;
    private Course course;
    private ArrayList<ComponentResult> results;
    private double requiredGradePoint;
    private AttributeMap attributes;

    public Enrollment(){
        this(0, null, new ArrayList<>(), 0.0, new AttributeMap());
    }

    public Enrollment(long uid, Course course, ArrayList<ComponentResult> results, double requiredGradePoint, AttributeMap attributes){
        this(uid, course, results, requiredGradePoint, attributes, 0);
    }

    public Enrollment(long uid, Course course, ArrayList<ComponentResult> results, double requiredGradePoint, AttributeMap attributes, long studentUid) {
        this.uid = uid;
        this.course = course;
        this.results = results;
        this.requiredGradePoint = requiredGradePoint;
        this.attributes = attributes;
        this.studentUid = studentUid;
    }

    public Enrollment(Enrollment other){
        this.uid = other.uid;
        this.course = other.course.copy();
        this.results = (ArrayList<ComponentResult>) deepCopy(other.results);
        this.requiredGradePoint = other.requiredGradePoint;
        this.attributes = other.attributes.copy();
    }


    public long uid(){
        return uid;
    }

    public long studentUid() {
        return studentUid;
    }

    public void studentUid(long studentUid) {
        this.studentUid = studentUid;
    }

    public Course course() {
        return course;
    }

    public void course(Course course) {
        this.course = course;
    }

    public ArrayList<ComponentResult> results() {
        return results;
    }

    public void results(ArrayList<ComponentResult> results) {
        this.results = results;
    }

    public double requiredGrade() {
        return requiredGradePoint;
    }

    public void requiredGrade(double requiredGrade) {
        this.requiredGradePoint = requiredGrade;
    }

    public AttributeMap attributes() {
        return attributes;
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
        map.put("uid", uid);
        map.put("courseUid",  course.uid());
        map.put("studentUid", studentUid);
        map.put("results", new ArrayList<ComponentResult>());
        map.put("requiredGradePoint", requiredGradePoint);
        var mapResultsList = (ArrayList<ComponentResult>) map.get("results");
        mapResultsList.addAll(results);
        map.put("attributes", attributes);
        return map;
    }

    public static Enrollment fromMap(Map<String, Object> map){
        long uid = (Long) map.get("uid");
        long tempStudentUid = (Long) map.get("studentUid");
        Course course = new Course((Long) map.get("courseUid"));
        double requiredGradePoint = (Double) map.get("requiredGradePoint");
        var results = new ArrayList<ComponentResult>();
        for (var s : (List<Map<String, Object>>)map.get("results")){
            results.add(ComponentResult.fromMap(s));
        }
        AttributeMap attributes;
        if(map.containsKey("attributes")){
            attributes = AttributeMap.fromMap(asMap(map.get("attributes")));
        } else {
            attributes = new AttributeMap();
        }
        return new Enrollment(uid, course, results, requiredGradePoint, attributes, tempStudentUid);
    }

    @Override
    public String toText(){
        return TextSerializer.toTextPretty(toMap());
    }

    @Override
    public Enrollment copy(){
        return new Enrollment(this);
    }
}
