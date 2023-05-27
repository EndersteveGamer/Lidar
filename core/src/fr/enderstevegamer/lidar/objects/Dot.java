package fr.enderstevegamer.lidar.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fr.enderstevegamer.lidar.utils.Position;
import fr.enderstevegamer.lidar.utils.Position2D;
import fr.enderstevegamer.lidar.utils.ProjectionUtils;

public class Dot {
    private static final int DRAW_SIZE = 10;
    public static final double VIEW_DISTANCE = 10;
    Position pos;
    public Color color;

    public Dot(Position pos, Color color) {
        this.pos = pos;
        this.color = color;
    }

    public Dot(Position pos) {
        this(pos, Color.WHITE);
    }

    public void draw(ShapeRenderer shape, Player viewer) {
        double distanceWithViewer = this.pos.distanceWith(viewer.getPos());
        if (distanceWithViewer > VIEW_DISTANCE) return;
        float size = (float) (DRAW_SIZE / distanceWithViewer);

        Position2D screenPos = ProjectionUtils.projectPoint(this.pos, viewer);
        double darkMultiplier = 1 - distanceWithViewer / VIEW_DISTANCE;
        Color drawColor = new Color(
                (float) (color.r * darkMultiplier),
                (float) (color.g * darkMultiplier),
                (float) (color.b * darkMultiplier),
                color.a
        );
        shape.setColor(drawColor);
        shape.circle((float) screenPos.getX(), (float) screenPos.getY(), size);
    }
}
