package group.habooba.core.domain;

public record ComponentResult(
        long componentUid,
        double gradePoint,
        String feedback,
        boolean finished,
        StudyTimestamp finishedAt
) {

}
