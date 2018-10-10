package ru.academits.baklanov.tasks;

public class Matrix {
    private Vector[] rows;

    public Matrix(int n, int m) {
        this(new double[n][m]);
    }

    public Matrix(double[][] array2D) {
        this(getVectors(array2D));
    }

    public Matrix(Vector[] vectors) {
        if (vectors == null) {
            throw new IllegalArgumentException("Массив не найден! (null)");
        }

        int maxRowLength = 0;

        for (Vector vector : vectors) {
            if (vector == null) {
                throw new IllegalArgumentException("Вектор не найден! (null)");
            }

            if (maxRowLength < vector.getSize()) {
                maxRowLength = vector.getSize();
            }
        }

        if (vectors.length == 0) {
            throw new IllegalArgumentException("Размерность матрицы должна быть больше ноля!");
        }

        rows = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; ++i) {
            rows[i] = Vector.sumOf(new Vector(maxRowLength), vectors[i]);
        }
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

    private static Vector[] getVectors(double[][] array2D) {
        if (array2D == null) {
            throw new IllegalArgumentException("Массив не найден! (null)");
        }

        Vector[] vectors = new Vector[array2D.length];

        for (int i = 0; i < array2D.length; ++i) {
            vectors[i] = new Vector(array2D[i]);
        }

        return vectors;
    }

    public int[] getSize() {
        int[] size = new int[2];
        size[0] = rows.length;
        size[1] = rows[0].getSize();

        return size;
    }

    public Vector getRow(int index) {
        return rows[index];
    }

    public void setRow(int index, Vector vector) {
        if (this.getSize()[1] < vector.getSize()) {
            throw new IllegalArgumentException("Размер вектора больше размера матрицы!");
        }
        rows[index] = Vector.sumOf(new Vector(this.getSize()[1]), vector);
    }

    public Vector getColumn(int index) {
        double[] tempArray = new double[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            tempArray[i] = rows[i].getCoordinate(index);
        }

        return new Vector(tempArray);
    }

    public void scalarMultiply(double scalar) {
        for (Vector row : rows) {
            row.multiply(scalar);
        }
    }

    public Matrix transpose() {
        Matrix transposedMatrix = new Matrix(this.getSize()[1], this.getSize()[0]);

        for (int i = 0; i < this.getSize()[1]; ++i) {
            transposedMatrix.setRow(i, this.getColumn(i));
        }

        return transposedMatrix;
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