package fr.enderstevegamer.lidar.utils;

import java.util.Arrays;

public class MatrixUtils {
    public static final double[][] IDENTITY = new double[][] {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    };

    public static double[] multiplyMatrix(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            double sum = 0.0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }

        return result;
    }

    public static double[][] multiplyMatrix(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = 0.0;
                for (int k = 0; k < colsA; k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    public static double[][] scale(double[][] matrix, double scale) {
        double[][] newMatrix = Arrays.copyOf(matrix, matrix.length);
        for (int y = 0; y < newMatrix.length; y++) {
            for (int x = 0; x < newMatrix[y].length; x++) {
                newMatrix[y][x] *= scale;
            }
        }
        return newMatrix;
    }

    public static String matrixToString(double[][] matrix) {
        StringBuilder str = new StringBuilder("[\n");
        for (int y = 0; y < matrix.length; y++) {
            str.append("\t[");
            for (int x = 0; x < matrix[y].length; x++) {
                str.append(matrix[y][x]);
                if (x != matrix[y].length - 1) str.append(", ");
            }
            str.append("]");
            if (y != matrix.length - 1) str.append(",");
            str.append("\n");
        }
        str.append("]");
        return str.toString();
    }
}
