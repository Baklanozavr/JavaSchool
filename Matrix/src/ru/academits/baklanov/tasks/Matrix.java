package ru.academits.baklanov.tasks;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Размерность матрицы должна быть больше ноля!");
        }

        rows = new Vector[n];
        for (int i = 0; i < n; ++i) {
            rows[i] = new Vector(m);
        }
    }

    public Matrix(double[][] array2D) {
        if (array2D == null) {
            throw new IllegalArgumentException("Массив не найден! (null)");
        }
        if (array2D.length == 0) {
            throw new IllegalArgumentException("Размерность матрицы должна быть больше ноля!");
        }

        int maxRowLength = 0;
        for (double[] array : array2D) {
            if (array == null) {
                throw new IllegalArgumentException("Вложенный массив не найден! (null)");
            }
            if (maxRowLength < array.length) {
                maxRowLength = array.length;
            }
        }
        if (maxRowLength == 0) {
            throw new IllegalArgumentException("Размерность матрицы должна быть больше ноля!");
        }

        rows = new Vector[array2D.length];
        for (int i = 0; i < array2D.length; ++i) {
            rows[i] = new Vector(maxRowLength, array2D[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors == null) {
            throw new IllegalArgumentException("Массив не найден! (null)");
        }
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Размерность матрицы должна быть больше ноля!");
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

    public int getRowsNumber() {
        return rows.length;
    }

    public int getColumnsNumber() {
        return rows[0].getSize();
    }

    public Vector getRow(int index) {
        return rows[index];
    }

    public void setRow(int index, Vector vector) {
        if (this.getColumnsNumber() < vector.getSize()) {
            throw new IllegalArgumentException("Размер вектора больше размера матрицы!");
        }
        rows[index] = Vector.sumOf(new Vector(this.getColumnsNumber()), vector);
    }

    public Vector getColumn(int index) {
        double[] tempArray = new double[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            tempArray[i] = rows[i].getCoordinate(index);
        }

        return new Vector(tempArray);
    }

    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiply(scalar);
        }
    }

    public Vector multiplyByVector(Vector vector) {
        if (this.getColumnsNumber() != vector.getSize()) {
            throw new IllegalArgumentException("Несовпадение размерностей!");
        }

        Vector resultVector = new Vector(vector.getSize());

        for (int i = 0; i < vector.getSize(); ++i) {
            resultVector.setCoordinate(i, Vector.scalarProduct(rows[i], vector));
        }

        return resultVector;
    }

    public Matrix transpose() {
        Matrix transposedMatrix = new Matrix(this.getColumnsNumber(), this.getRowsNumber());

        for (int i = 0; i < this.getColumnsNumber(); ++i) {
            transposedMatrix.setRow(i, this.getColumn(i));
        }

        return transposedMatrix;
    }

    public void plus(Matrix matrix) {
        if (rows.length < matrix.rows.length) {
            int delta = matrix.rows.length - rows.length;

            rows = Arrays.copyOf(rows, matrix.rows.length);

            for (int i = delta; i < matrix.rows.length; ++i) {
                rows[i] = new Vector(this.getColumnsNumber());
            }
        }

        for (int i = 0; i < matrix.rows.length; ++i) {
            rows[i].plus(matrix.rows[i]);
        }
    }

    public void minus(Matrix matrix) {
        if (rows.length < matrix.rows.length) {
            int delta = matrix.rows.length - rows.length;

            rows = Arrays.copyOf(rows, matrix.rows.length);

            for (int i = delta; i < matrix.rows.length; ++i) {
                rows[i] = new Vector(this.getColumnsNumber());
            }
        }

        for (int i = 0; i < matrix.rows.length; ++i) {
            rows[i].minus(matrix.rows[i]);
        }
    }

    public static Matrix sumOf(Matrix matrix1, Matrix matrix2) {
        Matrix tempMatrix = new Matrix(matrix1);
        tempMatrix.plus(matrix2);
        return tempMatrix;
    }

    public static Matrix difference(Matrix matrix1, Matrix matrix2) {
        Matrix tempMatrix = new Matrix(matrix1);
        tempMatrix.minus(matrix2);
        return tempMatrix;
    }

    public static Matrix matrixMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsNumber() != matrix2.getRowsNumber()) {
            throw new IllegalArgumentException("Данные матрицы нельзя перемножить!");
        }

        double[][] resultArray = new double[matrix1.getRowsNumber()][matrix2.getColumnsNumber()];

        for (int i = 0; i < matrix1.getRowsNumber(); ++i) {
            for (int j = 0; j < matrix2.getColumnsNumber(); ++j) {
                resultArray[i][j] = Vector.scalarProduct(matrix1.getRow(i), matrix2.getColumn(j));
            }
        }

        return new Matrix(resultArray);
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