package group.habooba.backend;

import java.util.List;

public record StudentAcademics(
        String name,
        ProgramType program,
        SchoolOfStudy schoolOfStudy,
        int year,
        boolean nextLevelAllowed,
        List<Course> courses
) {

}