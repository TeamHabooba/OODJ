package group.habooba.core.domain;

import java.util.List;

/**
 * Utility class for Course-related operations
 */
public class CourseUtils {

    /**
     * Validates that a course has valid firstWeek and lastWeek timestamps
     * @param course the course to validate
     * @return true if valid, false otherwise
     */
    public static boolean hasValidWeekRange(Course course) {
        if (course == null) return false;
        if (course.firstWeek() == null || course.lastWeek() == null) return false;
        if (course.firstWeek().empty() || course.lastWeek().empty()) return false;

        // Check that lastWeek comes after or equals firstWeek
        return !course.lastWeek().isBefore(course.firstWeek());
    }

    /**
     * Ensures that a course has valid default week ranges
     * @param course the course to fix
     */
    public static void ensureValidWeekRange(Course course) {
        if (course == null) return;

        if (course.firstWeek() == null || course.firstWeek().empty()) {
            course.firstWeek(new StudyTimestamp(1, 1, 1));
        }

        if (course.lastWeek() == null || course.lastWeek().empty()) {
            course.lastWeek(new StudyTimestamp(1, 2, 12));
        }

        // If lastWeek is before firstWeek, swap them
        if (course.lastWeek().isBefore(course.firstWeek())) {
            StudyTimestamp temp = course.firstWeek();
            course.firstWeek(course.lastWeek());
            course.lastWeek(temp);
        }
    }

    /**
     * Ensures valid week ranges for a list of courses
     * @param courses the list of courses to process
     */
    public static void ensureValidWeekRanges(List<Course> courses) {
        if (courses == null) return;

        for (Course course : courses) {
            ensureValidWeekRange(course);
        }
    }

    /**
     * Calculates the total number of weeks in a course
     * @param course the course
     * @return number of weeks, or 0 if invalid
     */
    public static int getTotalWeeks(Course course) {
        if (!hasValidWeekRange(course)) return 0;

        // This is a simplified calculation - you may need to adjust based on your StudyTimestamp implementation
        return course.lastWeek().week() - course.firstWeek().week() + 1;
    }

    /**
     * Checks if a given timestamp falls within the course duration
     * @param course the course
     * @param timestamp the timestamp to check
     * @return true if within range, false otherwise
     */
    public static boolean isWithinCourseDuration(Course course, StudyTimestamp timestamp) {
        if (!hasValidWeekRange(course)) return false;
        if (timestamp == null || timestamp.empty()) return false;

        return !timestamp.isBefore(course.firstWeek()) && !timestamp.isAfter(course.lastWeek());
    }

    /**
     * Creates a default course with standard week ranges
     * @param uid the course UID
     * @param name the course name
     * @param program the program type
     * @param school the school of study
     * @return a new Course with default values
     */
    public static Course createDefaultCourse(long uid, String name, ProgramType program, SchoolOfStudy school) {
        return new Course(
                uid,
                name,
                program,
                school,
                new java.util.ArrayList<>(),
                new group.habooba.core.base.AttributeMap(),
                new StudyTimestamp(1, 1, 1),  // First week of first semester, first year
                new StudyTimestamp(1, 2, 12)   // Last week of second semester, first year
        );
    }

    // Private constructor to prevent instantiation
    private CourseUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }
}