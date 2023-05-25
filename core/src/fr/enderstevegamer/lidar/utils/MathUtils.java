package fr.enderstevegamer.lidar.utils;

public class MathUtils {
    public static double max(double... doubles) {
        double max = doubles[0];
        for (double num : doubles) {
            if (num > max) max = num;
        }
        return max;
    }
}
