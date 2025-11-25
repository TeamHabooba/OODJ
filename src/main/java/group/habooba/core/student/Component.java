package group.habooba.core.student;

public record Component(
        long id,
        String name,
        double weightPercent,
        boolean required
) {
}
