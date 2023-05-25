package fr.enderstevegamer.lidar.utils;

public class Position2D {
    double x;
    double y;

    public Position2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position2D() {
        this(0, 0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
