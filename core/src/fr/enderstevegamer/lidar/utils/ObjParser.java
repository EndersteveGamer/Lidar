package fr.enderstevegamer.lidar.utils;

import fr.enderstevegamer.lidar.objects.Wall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class ObjParser {
    public static ArrayList<Wall> parseObj(String objPath) throws IOException {
        Stream<String> stream = Files.lines(Path.of(objPath));
        Object[] objStream = stream.toArray();
        String[] lines = new String[objStream.length];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = (String) objStream[i];
        }
        HashMap<Integer, Position> vertices = getVertices(lines);
        ArrayList<Integer[]> facesNum = getFaces(lines);
        ArrayList<Wall> faces = new ArrayList<>();
        for (Integer[] faceNum : facesNum) {
            faces.add(new Wall(
                    vertices.get(faceNum[1]),
                    vertices.get(faceNum[2]),
                    vertices.get(faceNum[0])
            ));
        }
        return faces;
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

    public static ArrayList<Integer[]> getFaces(String[] lines) {
        ArrayList<Integer[]> faces = new ArrayList<>();
        for (String str : lines) {
            if (!(str.charAt(0) == 'f')) continue;
            String[] faceLine = str.split(" ");
            Integer[] face = new Integer[3];
            for (int i = 1; i <= 3; i++) {
                String[] faceStr = faceLine[i].split("/");
                face[i - 1] = Integer.parseInt(faceStr[0]);
            }
            faces.add(face);
        }
        return faces;
    }
}
