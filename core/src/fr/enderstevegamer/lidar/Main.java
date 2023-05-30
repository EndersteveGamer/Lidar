package fr.enderstevegamer.lidar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.enderstevegamer.lidar.objects.Dot;
import fr.enderstevegamer.lidar.objects.Player;
import fr.enderstevegamer.lidar.objects.Wall;
import fr.enderstevegamer.lidar.utils.ObjParser;
import fr.enderstevegamer.lidar.utils.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends ApplicationAdapter {
	static Player player;
	static HashMap<Wall, Boolean> walls = new HashMap<>();
	// Specify the file path WITHOUT EXTENSION
	// The mtl file must have the same name as the obj file
	static final String OBJ_PATH = "C:\\Users\\theop\\OneDrive\\Bureau\\Pour coder\\Fichiers Java\\Lidar\\core\\src\\fr\\enderstevegamer\\lidar\\blender ressources\\untitled";

	ShapeRenderer shape;
	
	@Override
	public void create () {
		Gdx.input.setInputProcessor(new InputHandler());
		Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		Gdx.input.setCursorCatched(true);
		shape = new ShapeRenderer();

		player = new Player();

		try {
			ArrayList<Wall> objWalls= ObjParser.parseObj(OBJ_PATH);
			for (Wall wall : objWalls) {
				walls.put(wall, true);
			}
		} catch (IOException e) {
			Gdx.app.error("File not found", "File at " + OBJ_PATH + " not found!");
			Gdx.app.error("File not found", e.toString());
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		walls.replaceAll((w, v) -> w.isVisible(player));

		shape.begin(ShapeRenderer.ShapeType.Filled);
		for (Wall wall : walls.keySet()) {
			if (!walls.get(wall)) continue;
			if (wall.getColor().r == 0
					&& wall.getColor().g == 0
					&& wall.getColor().b == 0) continue;
			for (Dot dot : wall.getDots()) {
				dot.draw(shape, player);
			}
		}
		for (Wall wall : walls.keySet()) {
			if (wall.getColor().r != 0
					|| wall.getColor().g != 0
					|| wall.getColor().b != 0) continue;
			wall.draw(shape, player);
		}
		shape.end();

		((InputHandler) Gdx.input.getInputProcessor()).heldKeys();
	}
	
	@Override
	public void dispose () {
		shape.dispose();
	}

	public static Player getPlayer() {
		return player;
	}

	public static void shootRay() {
		player.shootRay(walls);
	}
}
