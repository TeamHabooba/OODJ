package group.habooba.core.domain;

import group.habooba.core.base.AppObject;
import group.habooba.core.base.AttributeMap;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.*;

import static group.habooba.core.base.Utils.asMap;

public final class StudentAcademics extends AppObject<StudentAcademics> {
    private ProgramType program;
    private SchoolOfStudy schoolOfStudy;
    private StudyTimestamp currentTimestamp;
    private List<Enrollment> enrollments;

    public StudentAcademics(
            long studentUid,
            ProgramType program,
            SchoolOfStudy schoolOfStudy,
            StudyTimestamp currentTimestamp,
            List<Enrollment> enrollments,
            AttributeMap attributes
    ) {
        this.uid = studentUid;
        this.program = program;
        this.schoolOfStudy = schoolOfStudy;
        this.currentTimestamp = currentTimestamp;
        this.enrollments = enrollments;
        this.attributes = attributes;
    }

    public StudentAcademics() {
        this(0L, ProgramType.DEGREE, SchoolOfStudy.NONE, new StudyTimestamp(), new ArrayList<Enrollment>(), new AttributeMap());
    }

    public StudentAcademics(StudentAcademics other) {
        this.uid = other.uid;
        this.program = other.program;
        this.schoolOfStudy = other.schoolOfStudy;
        this.currentTimestamp = new StudyTimestamp(other.currentTimestamp);
        this.enrollments = new ArrayList<>();
        for (Enrollment e : other.enrollments) {
            this.enrollments.add(new Enrollment(e));
        }
        this.attributes = new AttributeMap(other.attributes);
    }


    public boolean empty() {
        return program == ProgramType.DEGREE && schoolOfStudy == SchoolOfStudy.NONE || currentTimestamp().empty() || enrollments.isEmpty();
    }

    //Not the final Change,it will modify in the future
    public boolean nextLevelAllowed() {
        return false;
    }

    public ProgramType program() {
        return program;
    }

    public void program(ProgramType program) {
        this.program = program;
    }

    public SchoolOfStudy schoolOfStudy() {
        return schoolOfStudy;
    }

    public void schoolOfStudy(SchoolOfStudy schoolOfStudy) {
        this.schoolOfStudy = schoolOfStudy;
    }

    public StudyTimestamp currentTimestamp() {
        return currentTimestamp;
    }

    public void currentTimestamp(StudyTimestamp currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public List<Enrollment> enrollments() {
        return enrollments;
    }

    public void enrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (StudentAcademics) obj;
        return  this.uid == that.uid &&
                Objects.equals(this.program, that.program) &&
                Objects.equals(this.schoolOfStudy, that.schoolOfStudy) &&
                Objects.equals(this.currentTimestamp, that.currentTimestamp) &&
                Objects.equals(this.enrollments, that.enrollments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, program, schoolOfStudy, currentTimestamp, enrollments);
    }

    @Override
    public String toString() {
        return "StudentAcademics[" +
                "studentUid=" + uid + ", " +
                "program=" + program + ", " +
                "schoolOfStudy=" + schoolOfStudy + ", " +
                "currentTimestamp=" + currentTimestamp + ", " +
                "enrollments=" + enrollments + ']';
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("studentUid", this.uid);
        map.put("program", this.program.name());
        map.put("schoolOfStudy", this.schoolOfStudy.name());
        map.put("currentTimestamp", this.currentTimestamp.toMap());

        List<Map<String, Object>> enrollmentsList = new ArrayList<>();
        for (Enrollment e : this.enrollments) {
            enrollmentsList.add(e.toMap());
        }
        map.put("enrollments", enrollmentsList);
        map.put("attributes", this.attributes.toMap());

        return map;
    }

    public static StudentAcademics fromMap(Map<String, Object> map) {
        long studentUid;
        if (map.containsKey("studentUid")) {
            Object uidObj = map.get("studentUid");
            if (uidObj instanceof Integer) {
                studentUid = (long) (int) uidObj;
            } else if (uidObj instanceof Long) {
                studentUid = (Long) uidObj;
            } else {
                studentUid = -1L;
            }
        } else {
            studentUid = -1L;
        }

        ProgramType program = ProgramType.valueOf((String) map.get("program"));
        SchoolOfStudy schoolOfStudy = SchoolOfStudy.valueOf((String) map.get("schoolOfStudy"));
        StudyTimestamp currentTimestamp = StudyTimestamp.fromMap(asMap(map.get("currentTimestamp")));

        List<Enrollment> enrollments = new ArrayList<>();
        if (map.containsKey("enrollments")) {
            List<Map<String, Object>> enrollmentsList = (List<Map<String, Object>>) map.get("enrollments");
            for (Map<String, Object> enrollmentMap : enrollmentsList) {
                enrollments.add(Enrollment.fromMap(enrollmentMap));
            }
        }

        AttributeMap attributes;
        if (map.containsKey("attributes")) {
            attributes = AttributeMap.fromMap(asMap(map.get("attributes")));
        } else {
            attributes = new AttributeMap();
            attributes.put("type", "studentAcademics");
        }

        return new StudentAcademics(studentUid, program, schoolOfStudy, currentTimestamp, enrollments, attributes);
    }

    @Override
    public String toText() {
        return TextSerializer.toTextPretty(toMap());
    }

    public static StudentAcademics fromText(String text) {
        return fromMap(asMap(TextParser.fromText(text)));
    }

    @Override
    public StudentAcademics copy() {
        return new StudentAcademics(this);
    }
}