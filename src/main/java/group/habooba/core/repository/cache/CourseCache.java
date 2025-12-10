package group.habooba.core.repository.cache;

import group.habooba.core.domain.Course;
import group.habooba.core.domain.StudyTimestamp;

public record CourseCache(Course course, long courseAdminUid, StudyTimestamp start) {
}
