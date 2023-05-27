package fr.enderstevegamer.lidar.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fr.enderstevegamer.lidar.utils.Position;
import fr.enderstevegamer.lidar.utils.Position2D;
import fr.enderstevegamer.lidar.utils.ProjectionUtils;
import fr.enderstevegamer.lidar.utils.Vector;

import java.util.ArrayList;

public class Wall {
    private final Position pos1;
    private final Position pos2;
    private final Position pos3;
    private final Color color;
    private ArrayList<Dot> dots;
    private final int SCREEN_ERROR_TRESHOLD = 1000;

    public Wall(Position pos1, Position pos2, Position pos3, Color color) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos3 = pos3;
        this.color = color;
        this.dots = new ArrayList<>();
    }

    public Wall(Position pos1, Position pos2, Position pos3) {
        this(pos1, pos2, pos3, Color.WHITE);
    }

    public Wall(Position2D pos1, Position2D pos2, double wallBottom, double wallTop) {
        this(
                new Position(pos1.getX(), -wallBottom, pos1.getY()),
                new Position(pos1.getX(), -wallTop, pos1.getY()),
                new Position(pos2.getX(), -wallBottom, pos2.getY()),
                Color.WHITE
        );
    }

    public Wall(Position2D pos1, Position2D pos2, double wallBottom, double wallTop, Color color) {
        this(
                new Position(pos1.getX(), -wallBottom, pos1.getY()),
                new Position(pos1.getX(), -wallTop, pos1.getY()),
                new Position(pos2.getX(), -wallBottom, pos2.getY()),
                color
        );
    }

    public Wall(double pos1X, double pos1Y,
                double pos2X, double pos2Y,
                double wallBottom, double wallTop,
                Color color) {
        this(
                new Position2D(pos1X, pos1Y),
                new Position2D(pos2X, pos2Y),
                wallBottom, wallTop,
                color
        );
    }
    public Wall(double pos1X, double pos1Y,
                double pos2X, double pos2Y,
                double wallBottom, double wallTop) {
        this(
                new Position2D(pos1X, pos1Y),
                new Position2D(pos2X, pos2Y),
                wallBottom, wallTop
        );
    }

    public Position getPos1() {
        return this.pos1;
    }

    public Position getPos2() {
        return this.pos2;
    }

    public Position getPos3() {
        return this.pos3;
    }

    public Color getColor() {return this.color;}

    public ArrayList<Dot> getDots() {
        return dots;
    }

    public boolean isVisible(Player player) {
        Position pos4 = Position.addPositions(Position.addPositions(pos1, new Vector(pos1, pos2)),
                new Vector(pos1, pos3));

        if (player.getPos().distanceWith(this.pos1) > Dot.VIEW_DISTANCE
                && player.getPos().distanceWith(this.pos2) > Dot.VIEW_DISTANCE
                && player.getPos().distanceWith(this.pos3) > Dot.VIEW_DISTANCE
                && player.getPos().distanceWith(pos4) > Dot.VIEW_DISTANCE) return false;

        Position2D screenPos1 = ProjectionUtils.projectPoint(this.pos1, player);
        Position2D screenPos2 = ProjectionUtils.projectPoint(this.pos2, player);
        Position2D screenPos3 = ProjectionUtils.projectPoint(this.pos3, player);
        Position2D screenPos4 = ProjectionUtils.projectPoint(pos4, player);

        Position2D screenBottomLeft = new Position2D(-SCREEN_ERROR_TRESHOLD,
                Gdx.graphics.getHeight() + SCREEN_ERROR_TRESHOLD);
        Position2D screenTopLeft = new Position2D(-SCREEN_ERROR_TRESHOLD,
                -SCREEN_ERROR_TRESHOLD);
        Position2D screenBottomRight = new Position2D(Gdx.graphics.getWidth() + SCREEN_ERROR_TRESHOLD,
                Gdx.graphics.getHeight() + SCREEN_ERROR_TRESHOLD);
        Position2D screenTopRight = new Position2D(Gdx.graphics.getWidth() + SCREEN_ERROR_TRESHOLD,
                -SCREEN_ERROR_TRESHOLD);

        return isInPolygon(screenBottomLeft, screenTopLeft, screenBottomRight, screenTopRight, screenPos1) ||
                isInPolygon(screenBottomLeft, screenTopLeft, screenBottomRight, screenTopRight, screenPos2) ||
                isInPolygon(screenBottomLeft, screenTopLeft, screenBottomRight, screenTopRight, screenPos3) ||
                isInPolygon(screenBottomLeft, screenTopLeft, screenBottomRight, screenTopRight, screenPos4) ||
                isInPolygon(screenPos1, screenPos2, screenPos3, screenPos4, screenBottomLeft) ||
                isInPolygon(screenPos1, screenPos2, screenPos3, screenPos4, screenTopLeft) ||
                isInPolygon(screenPos1, screenPos2, screenPos3, screenPos4, screenBottomRight) ||
                isInPolygon(screenPos1, screenPos2, screenPos3, screenPos4, screenTopRight);
    }

    private boolean isInPolygon(Position2D polyPos1, Position2D polyPos2,
                                Position2D polyPos3, Position2D polyPos4,
                                Position2D point) {
        return isInTriangle(polyPos1, polyPos2, polyPos3, point) ||
                isInTriangle(polyPos2, polyPos3, polyPos4, point);
    }

    private boolean isInTriangle(Position2D trianglePos1, Position2D trianglePos2,
                                Position2D trianglePos3, Position2D point) {
        double triangleArea = triangleArea(trianglePos1, trianglePos2, trianglePos3);
        double sumArea = triangleArea(trianglePos1, trianglePos2, point) +
                triangleArea(trianglePos2, trianglePos3, point) +
                triangleArea(trianglePos1, trianglePos3, point);
        double DISPLAY_ERROR_TRESHOLD = 0.1;
        return Math.abs(sumArea - triangleArea) <= DISPLAY_ERROR_TRESHOLD;
    }

    private double triangleArea(Position2D A, Position2D B, Position2D C) {
        Position2D AB = new Position2D(
                B.getX() - A.getX(),
                B.getY() - A.getY()
        );
        Position2D AC = new Position2D(
                C.getX() - A.getX(),
                C.getY() - A.getY()
        );
        double crossProduct = AB.getX() * AC.getY() - AB.getY() * AC.getX();
        return Math.abs(crossProduct) / 2;
    }

    public void draw(ShapeRenderer shape, Player player) {
        Position pos4 = Position.addPositions(Position.addPositions(pos1, new Vector(pos1, pos2)),
                new Vector(pos1, pos3));
        Position2D screenPos1 = ProjectionUtils.projectPoint(this.pos1, player);
        Position2D screenPos2 = ProjectionUtils.projectPoint(this.pos2, player);
        Position2D screenPos3 = ProjectionUtils.projectPoint(this.pos3, player);
        Position2D screenPos4 = ProjectionUtils.projectPoint(pos4, player);

        shape.setColor(this.color);
        shape.triangle((float) screenPos1.getX(), (float) screenPos1.getY(),
                (float) screenPos2.getX(), (float) screenPos2.getY(),
                (float) screenPos3.getX(), (float) screenPos3.getY());
        shape.triangle((float) screenPos2.getX(), (float) screenPos2.getY(),
                (float) screenPos3.getX(), (float) screenPos3.getY(),
                (float) screenPos4.getX(), (float) screenPos4.getY());
    }
}
