package group.habooba.core.domain;

import java.util.ArrayList;
import java.util.List;

public record StudentAcademics(
        String name,
        ProgramType program,
        SchoolOfStudy schoolOfStudy,
        StudyTimestamp currentTimestamp,
        List<Enrollment> enrollments
) {

    public StudentAcademics(){
        this("", ProgramType.DEGREE, SchoolOfStudy.NONE, new StudyTimestamp(), new ArrayList<Enrollment>());
    }

    public boolean empty(){
        return name.isEmpty() || program ==  ProgramType.DEGREE && schoolOfStudy == SchoolOfStudy.NONE || currentTimestamp().empty() || enrollments.isEmpty();
    }

    //Not the final Change,it will modify in the future
    public boolean nextLevelAllowed() {
        return false;
    }


}