package fr.enderstevegamer.lidar.utils;

public class Vector extends Position {
    public Vector(Position pos) {
        super(pos.getX(), pos.getY(), pos.getZ());
    }

    public Vector(Position pos1, Position pos2) {
        super(
                pos2.getX() - pos1.getX(),
                pos2.getY() - pos1.getY(),
                pos2.getZ() - pos1.getZ()
        );
    }

    public Vector(double xPos, double yPos, double zPos) {
        super(xPos, yPos, zPos);
    }

    public Vector() {
        super();
    }

    public void normalize() {
        double divider = this.distanceWith(new Position());
        this.xPos /= divider;
        this.yPos /= divider;
        this.zPos /= divider;
    }

    public Vector multiply(double factor) {
        return new Vector(
                this.xPos * factor,
                this.yPos * factor,
                this.zPos * factor
        );
    }

    public Vector add(Vector vect) {
        return new Vector(
                this.xPos + vect.getX(),
                this.yPos + vect.getY(),
                this.zPos + vect.getY()
        );
    }

    public double dot(Vector vect) {
        return this.xPos * vect.getX() + this.yPos * vect.getY() + this.zPos * vect.getZ();
    }

    public Vector crossProduct(Vector vect) {
        return new Vector(
                this.getY() * vect.getZ() - this.getZ() * vect.getY(),
                this.getZ() * vect.getX() - this.getX() * vect.getZ(),
                this.getX() * vect.getY() - this.getY() * vect.getX()
        );
    }
}
