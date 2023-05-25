package fr.enderstevegamer.lidar.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fr.enderstevegamer.lidar.utils.Position;
import fr.enderstevegamer.lidar.utils.Position2D;
import fr.enderstevegamer.lidar.utils.ProjectionUtils;

public class Dot {
    private static final int DRAW_SIZE = 10;
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
        float size = (float) (DRAW_SIZE / distanceWithViewer);

        Position2D screenPos = ProjectionUtils.projectPoint(this.pos, viewer);
        shape.setColor(color);
        shape.circle((float) screenPos.getX(), (float) screenPos.getY(), size);
    }
}
