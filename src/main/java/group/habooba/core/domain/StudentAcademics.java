package group.habooba.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class StudentAcademics {
    private ProgramType program;
    private SchoolOfStudy schoolOfStudy;
    private StudyTimestamp currentTimestamp;
    private List<Enrollment> enrollments;

    public StudentAcademics(
            ProgramType program,
            SchoolOfStudy schoolOfStudy,
            StudyTimestamp currentTimestamp,
            List<Enrollment> enrollments
    ) {
        this.program = program;
        this.schoolOfStudy = schoolOfStudy;
        this.currentTimestamp = currentTimestamp;
        this.enrollments = enrollments;
    }

    public StudentAcademics() {
        this(ProgramType.DEGREE, SchoolOfStudy.NONE, new StudyTimestamp(), new ArrayList<Enrollment>());
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

    public SchoolOfStudy schoolOfStudy() {
        return schoolOfStudy;
    }

    public StudyTimestamp currentTimestamp() {
        return currentTimestamp;
    }

    public List<Enrollment> enrollments() {
        return enrollments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (StudentAcademics) obj;
        return  Objects.equals(this.program, that.program) &&
                Objects.equals(this.schoolOfStudy, that.schoolOfStudy) &&
                Objects.equals(this.currentTimestamp, that.currentTimestamp) &&
                Objects.equals(this.enrollments, that.enrollments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(program, schoolOfStudy, currentTimestamp, enrollments);
    }

    @Override
    public String toString() {
        return "StudentAcademics[" +
                "program=" + program + ", " +
                "schoolOfStudy=" + schoolOfStudy + ", " +
                "currentTimestamp=" + currentTimestamp + ", " +
                "enrollments=" + enrollments + ']';
    }


}