package fr.enderstevegamer.lidar.utils;

public class Position {
    double xPos, yPos, zPos;

    public Position(double xPos, double yPos, double zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public Position() {
        this(0, 0, 0);
    }

    public Position(Vector vect) {
        this(vect.getX(), vect.getY(), vect.getZ());
    }

    public Position(Position pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public double getX() {
        return xPos;
    }

    public void setX(double xPos) {
        this.xPos = xPos;
    }

    public double getY() {
        return yPos;
    }

    public void setY(double yPos) {
        this.yPos = yPos;
    }

    public double getZ() {
        return zPos;
    }

    public void setZ(double zPos) {
        this.zPos = zPos;
    }

    public void addPosition(Position pos) {
        this.xPos += pos.getX();
        this.yPos += pos.getY();
        this.zPos += pos.getZ();
    }

    public static Position addPositions(Position pos1, Position pos2) {
        return new Position(
                pos1.getX() + pos2.getX(),
                pos1.getY() + pos2.getY(),
                pos1.getZ() + pos2.getZ()
        );
    }

    public void addVector(Vector vect) {
        this.xPos += vect.getX();
        this.yPos += vect.getY();
        this.zPos += vect.getZ();
    }

    public void addPosition(double x, double y, double z) {
        this.xPos += x;
        this.yPos += y;
        this.zPos += z;
    }

    public Position multiply(double factor) {
        this.xPos *= factor;
        this.yPos *= factor;
        this.zPos *= factor;
        return this;
    }

    public double distanceWith(Position pos) {
        return Math.sqrt(
                (pos.getX() - this.xPos) * (pos.getX() - this.xPos) +
                (pos.getY() - this.yPos) * (pos.getY() - this.yPos) +
                (pos.getZ() - this.zPos) * (pos.getZ() - this.zPos)
        );
    }

    public Position copy() {
        return new Position(this.xPos, this.yPos, this.zPos);
    }

    public String toString() {
        return "(" + xPos + ", " + yPos + ", " + zPos + ")";
    }
}
