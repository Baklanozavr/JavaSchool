package ru.academits.baklanov.tasks;

public class Matrix {
    private Vector[] rows;

    public Matrix(int n, int m) {
        this(new double[n][m]);
    }

    public Matrix(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Матрица не найдена! (null)");
        }

        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.rows.length; ++i) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] array2D) {
        //предполагается, что все вложенные массивы одного размера
        if (array2D == null) {
            throw new IllegalArgumentException("Массив не найден! (null)");
        }

        if (array2D.length == 0 || array2D[0].length == 0) {
            throw new IllegalArgumentException("Размерность матрицы должна быть больше ноля!");
        }

        rows = new Vector[array2D.length];

        for (int i = 0; i < array2D.length; ++i) {
            rows[i] = new Vector(array2D[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors == null) {
            throw new IllegalArgumentException("Массив не найден! (null)");
        }

        rows = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; ++i) {
            rows[i] = new Vector(vectors[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{ " + rows[0]);
        for (int i = 1; i < rows.length; ++i) {
            result.append(", ").append(rows[i]);
        }
        return result.append(" }").toString();
    }
}