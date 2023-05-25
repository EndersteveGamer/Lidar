package fr.enderstevegamer.lidar.utils;

import com.badlogic.gdx.graphics.Color;
import fr.enderstevegamer.lidar.objects.Wall;

public class IntersectionUtils {
    public static IntersectionResult getIntersection(Position pos1, Position pos2, Wall wall) {
        Vector Lab = new Vector(pos1, pos2);
        Position La = new Position(pos1);
        Vector P01 = new Vector(wall.getPos1(), wall.getPos2());
        Vector P02 = new Vector(wall.getPos1(), wall.getPos3());
        Position P0 = new Position(wall.getPos1());

        double determinant = Lab.multiply(-1).dot(P01.crossProduct(P02));
        if (determinant == 0) return null;

        double t = (
                (P01.crossProduct(P02)).dot(new Vector(P0, La)) /
                        (Lab.multiply(-1).dot(P01.crossProduct(P02)))
        );
        double u = (
                ((P02.crossProduct(Lab.multiply(-1))).dot(new Vector(P0, La))) /
                        (Lab.multiply(-1).dot(P01.crossProduct(P02)))
        );
        double v = (
                ((Lab.multiply(-1).crossProduct(P01)).dot(new Vector(P0, La))) /
                        (Lab.multiply(-1).dot(P01.crossProduct(P02)))
        );

        if (t <= 0) return null;
        if (u < 0 || u > 1 || v < 0 || v > 1) return null;

        return new IntersectionResult(Position.addPositions(La, Lab.multiply(t)), wall.getColor());
    }

    public static class IntersectionResult {
        Position pos;
        Color color;

        public IntersectionResult(Position pos, Color color) {
            this.pos = pos;
            this.color = color;
        }

        public Position getPos() {return this.pos;}
        public Color getColor() {return this.color;}
    }
}
