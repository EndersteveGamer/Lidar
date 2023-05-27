package fr.enderstevegamer.lidar.utils;

import com.badlogic.gdx.graphics.Color;
import fr.enderstevegamer.lidar.objects.Wall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class ObjParser {
    static String currentMaterial = "None";

    public static ArrayList<Wall> parseObj(String objPath) throws IOException {
        Stream<String> stream = Files.lines(Path.of(objPath + ".obj"));
        Object[] objStream = stream.toArray();
        String[] lines = new String[objStream.length];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = (String) objStream[i];
        }
        HashMap<Integer, Position> vertices = getVertices(lines);
        HashMap<Integer[], String> facesNum = getFaces(lines);
        HashMap<String, Color> mtlColors = getMtlColors(objPath);
        ArrayList<Wall> faces = new ArrayList<>();
        for (Integer[] faceNum : facesNum.keySet()) {
            faces.add(new Wall(
                    vertices.get(faceNum[1]),
                    vertices.get(faceNum[2]),
                    vertices.get(faceNum[0]),
                    mtlColors.get(facesNum.get(faceNum))
            ));
        }
        return faces;
    }

    private static HashMap<String, Color> getMtlColors(String objPath) throws IOException {
        HashMap<String, Color> colors = new HashMap<>();
        Stream<String> stream = Files.lines(Path.of(objPath + ".mtl"));
        Object[] objStream = stream.toArray();
        String[] lines = new String[objStream.length];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = (String) objStream[i];
        }
        for (int i = 0; i < lines.length; i++) {
            if (!(lines[i].startsWith("newmtl"))) continue;
            String material = lines[i].split(" ")[1];
            Color color = null;
            for (int j = i; j < lines.length; j++) {
                if (!(lines[j].startsWith("Kd"))) continue;
                String[] colorString = lines[j].split(" ");
                color = new Color(
                        Float.parseFloat(colorString[1]),
                        Float.parseFloat(colorString[2]),
                        Float.parseFloat(colorString[3]),
                        1
                );
                break;
            }
            if (color == null) continue;
            colors.put(material, color);
        }
        return colors;
    }

    public static HashMap<Integer, Position> getVertices(String[] lines) {
        HashMap<Integer, Position> vertices = new HashMap<>();
        Integer start = 1;
        for (String str : lines) {
            if (!(str.charAt(0) == 'v' && str.charAt(1) == ' ')) continue;
            String[] strings = str.split(" ");
            vertices.put(start, new Position(
                    -Double.parseDouble(strings[1]),
                    -Double.parseDouble(strings[2]),
                    Double.parseDouble(strings[3])
            ));
            start++;
        }
        return vertices;
    }

    public static HashMap<Integer[], String> getFaces(String[] lines) {
        HashMap<Integer[], String> faces = new HashMap<>();
        for (String str : lines) {
            if (str.startsWith("usemtl")) {
                currentMaterial = str.split(" ")[1];
            }
            if (!(str.charAt(0) == 'f')) continue;
            String[] faceLine = str.split(" ");
            Integer[] face = new Integer[3];
            for (int i = 1; i <= 3; i++) {
                String[] faceStr = faceLine[i].split("/");
                face[i - 1] = Integer.parseInt(faceStr[0]);
            }
            faces.put(face, currentMaterial);
        }
        return faces;
    }
}
