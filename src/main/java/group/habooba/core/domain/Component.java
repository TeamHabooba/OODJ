package group.habooba.core.domain;

public record Component(
        long id,
        String name,
        double weightPercent,
        boolean required
) {
}
