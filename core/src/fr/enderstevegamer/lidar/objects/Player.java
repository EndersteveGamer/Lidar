package fr.enderstevegamer.lidar.objects;

import com.badlogic.gdx.graphics.Color;
import fr.enderstevegamer.lidar.utils.IntersectionUtils;
import fr.enderstevegamer.lidar.utils.Position;
import fr.enderstevegamer.lidar.utils.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    Position pos;
    double yaw;
    double pitch;
    double cameraAngle = 75;
    static final double SHOOT_ANGLE = 30;

    public Player(Position pos, double yaw, double pitch) {
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Player() {
        this(new Position(0, 0, 0), 0, 0);
    }

    public Position getPos() {
        return pos;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getCameraAngle() {
        return cameraAngle;
    }

    public Vector getDirectionVector() {
        Vector vect = new Vector(
                Math.sin(-pitch),
                Math.tan(yaw),
                Math.cos(-pitch)
        );
        vect.normalize();
        return vect;
    }

    public Vector getDirectionVector(double shakeAngle) {
        double pitchModif;
        double yawModif;
        do {
            pitchModif = (Math.random() - 0.5) * shakeAngle * shakeAngle;
            yawModif = (Math.random() - 0.5) * shakeAngle * shakeAngle;
        } while (Math.sqrt(pitchModif * pitchModif + yawModif * yawModif) > shakeAngle);
        double newPitch = pitch + Math.toRadians(pitchModif);
        double newYaw = yaw + Math.toRadians(yawModif);
        Vector vect = new Vector(
                Math.sin(-newPitch),
                Math.tan(newYaw),
                Math.cos(-newPitch)
        );
        vect.normalize();
        return vect;
    }

    public void shootRay(HashMap<Wall, Boolean> collidables) {
        Position potentialDot = null;
        Color color = null;
        Wall selectedWall = null;
        for (Wall wall : collidables.keySet()) {
            if (!collidables.get(wall)) continue;
            Position pos2 = this.getPos().copy();
            Vector directionVector = getDirectionVector(SHOOT_ANGLE);
            pos2.addPosition(directionVector);
            IntersectionUtils.IntersectionResult result = IntersectionUtils.getIntersection(this.getPos(), pos2, wall);
            if (result == null) continue;
            if (potentialDot == null) {
                potentialDot = result.getPos();
                color = result.getColor();
                selectedWall = wall;
            }
            else if (this.pos.distanceWith(result.getPos()) < this.pos.distanceWith(potentialDot)) {
                potentialDot = result.getPos();
                color = result.getColor();
                selectedWall = wall;
            }
        }

        if (potentialDot == null) return;
        if (selectedWall.getColor().r == 0
                && selectedWall.getColor().g == 0
                && selectedWall.getColor().b == 0) return;
        selectedWall.getDots().add(new Dot(potentialDot, color));
    }
}
