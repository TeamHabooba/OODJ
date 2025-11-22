package group.habooba.backend.student;

public record Component(
        long id,
        String name,
        double weightPercent,
        boolean required
) {
}
