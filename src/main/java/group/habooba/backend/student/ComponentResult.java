package group.habooba.backend.student;

public record ComponentResult(
        long componentId,
        double grade,
        String feedback
) {
}
