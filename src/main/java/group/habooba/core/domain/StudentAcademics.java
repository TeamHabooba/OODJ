package group.habooba.core.domain;

import java.util.List;

public record StudentAcademics(
        String name,
        ProgramType program,
        SchoolOfStudy schoolOfStudy,
        StudyTimestamp currentTimestamp,
        List<Enrollment> enrollments
) {
//Not the final Change,it will modify in the future
    public boolean nextLevelAllowed() {
        return false;
    }


}