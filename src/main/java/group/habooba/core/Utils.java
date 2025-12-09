package group.habooba.core;

public class Utils {
    public static boolean almostEqual(double a, double b, double eps) {
        double diff = Math.abs(a - b);
        if (diff <= eps) return true;

        return diff <= Math.max(Math.abs(a), Math.abs(b)) * eps;
    }
}
