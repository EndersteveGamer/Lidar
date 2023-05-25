package fr.enderstevegamer.lidar.utils;

import com.badlogic.gdx.Gdx;
import fr.enderstevegamer.lidar.objects.Player;

public class ProjectionUtils {
    public static Position2D projectPoint(Position toProject, Player viewer) {
        Position relativePosition = getRelativePosition(toProject, viewer);
        double[] vector = {relativePosition.getX(), relativePosition.getY(), relativePosition.getZ()};
        double[] projectedVector = MatrixUtils.multiplyMatrix(getRotationMatrix(viewer), vector);

        if (projectedVector[2] <= 0) {
            return new Position2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        }

        double screenX = projectedVector[0] / projectedVector[2];
        double screenY = projectedVector[1] / projectedVector[2];

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        double screenAspectRatio = (double) screenWidth / screenHeight;

        if (screenAspectRatio > 1) {
            screenX /= screenAspectRatio;
        } else {
            screenY *= screenAspectRatio;
        }

        double normalizedScreenX = (screenX + 1) * 0.5;
        double normalizedScreenY = (screenY + 1) * 0.5;

        double actualScreenX = normalizedScreenX * screenWidth;
        double actualScreenY = (1 - normalizedScreenY) * screenHeight;

        return new Position2D(actualScreenX, actualScreenY);
    }

    public static double[][] getRotationMatrix(Player viewer) {
        double[][] rotationX = {
                {1, 0, 0},
                {0, Math.cos(viewer.getYaw()), -Math.sin(viewer.getYaw())},
                {0, Math.sin(viewer.getYaw()), Math.cos(viewer.getYaw())}
        };

        double[][] rotationY = {
                {Math.cos(viewer.getPitch()), 0, Math.sin(viewer.getPitch())},
                {0, 1, 0},
                {-Math.sin(viewer.getPitch()), 0, Math.cos(viewer.getPitch())}
        };

        return MatrixUtils.multiplyMatrix(rotationX, rotationY);
    }

    private static Position getRelativePosition(Position pos, Player viewer) {
        return new Position(
                pos.getX() - viewer.getPos().getX(),
                pos.getY() - viewer.getPos().getY(),
                pos.getZ() - viewer.getPos().getZ()
        );
    }
}
