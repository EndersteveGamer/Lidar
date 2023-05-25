package fr.enderstevegamer.lidar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

public class InputHandler implements InputProcessor {
    private final double SPEED = 0.1;
    private final int SHOTS_PER_FRAME = 10;
    ArrayList<Integer> heldKeys = new ArrayList<>();

    public void heldKeys() {
        double yaw = Main.getPlayer().getPitch();
        if (heldKeys.contains(Input.Keys.W)) {
            Main.getPlayer().getPos().addPosition(
                    -Math.sin(yaw) * SPEED,
                    0,
                    Math.cos(yaw) * SPEED
            );
        }
        if (heldKeys.contains(Input.Keys.A)) {
            Main.getPlayer().getPos().addPosition(
                    -Math.cos(yaw) * SPEED,
                    0,
                    -Math.sin(yaw) * SPEED
            );
        }
        if (heldKeys.contains(Input.Keys.S)) {
            Main.getPlayer().getPos().addPosition(
                    Math.sin(yaw) * SPEED,
                    0,
                    -Math.cos(yaw) * SPEED
            );
        }
        if (heldKeys.contains(Input.Keys.D)) {
            Main.getPlayer().getPos().addPosition(
                    Math.cos(yaw) * SPEED,
                    0,
                    Math.sin(yaw) * SPEED
            );
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) for (int i = 0; i < SHOTS_PER_FRAME; i++) Main.shootRay();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE -> Gdx.app.exit();
        }
        if (!heldKeys.contains(keycode)) heldKeys.add(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        heldKeys.remove((Integer) keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return mouseDrag(screenX, screenY);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return mouseDrag(screenX, screenY);
    }

    private boolean mouseDrag(int screenX, int screenY) {
        Main.getPlayer().setPitch(
                Main.getPlayer().getPitch() - Math.toRadians((screenX - Gdx.graphics.getWidth() / 2.0) / 10));
        Main.getPlayer().setYaw(
                Main.getPlayer().getYaw() + Math.toRadians((screenY - Gdx.graphics.getHeight() / 2.0) / 10));
        if (Main.getPlayer().getYaw() > Math.toRadians(90)) {
            Main.getPlayer().setYaw(Math.toRadians(90));
        }
        if (Main.getPlayer().getYaw() < Math.toRadians(-90)) {
            Main.getPlayer().setYaw(Math.toRadians(-90));
        }
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
