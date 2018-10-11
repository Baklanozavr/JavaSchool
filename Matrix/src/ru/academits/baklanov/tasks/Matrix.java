package ru.academits.baklanov.tasks;

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

    public Vector getColumn(int index) {
        if (index >= this.getColumnsNumber() || index < 0) {
            throw new IllegalArgumentException("Выход за размерность матрицы!");
        }

        double[] tempArray = new double[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            tempArray[i] = rows[i].getCoordinate(index);
        }

        return new Vector(tempArray);
    }

    public Vector getRow(int index) {
        if (index >= this.getRowsNumber() || index < 0) {
            throw new IllegalArgumentException("Выход за размерность матрицы!");
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index >= this.getRowsNumber() || index < 0) {
            throw new IllegalArgumentException("Выход за размерность матрицы!");
        }
        if (this.getColumnsNumber() < vector.getSize()) {
            throw new IllegalArgumentException("Размер вектора больше размера матрицы!");
        }

        rows[index] = Vector.sumOf(new Vector(this.getColumnsNumber()), vector);
    }

    public void transpose() {
        Matrix transposedMatrix = new Matrix(this.getColumnsNumber(), this.getRowsNumber());

        for (int i = 0; i < this.getColumnsNumber(); ++i) {
            transposedMatrix.setRow(i, this.getColumn(i));
        }

        rows = transposedMatrix.rows;
    }

    public void plus(Matrix matrix) {
        if (matrix.getRowsNumber() != this.getRowsNumber() || matrix.getColumnsNumber() != this.getColumnsNumber()) {
            throw new IllegalArgumentException("Несовпадение размерностей!");
        }

        for (int i = 0; i < matrix.rows.length; ++i) {
            rows[i].plus(matrix.rows[i]);
        }
    }

    public void minus(Matrix matrix) {
        if (matrix.getRowsNumber() != this.getRowsNumber() || matrix.getColumnsNumber() != this.getColumnsNumber()) {
            throw new IllegalArgumentException("Несовпадение размерностей!");
        }

        for (int i = 0; i < matrix.rows.length; ++i) {
            rows[i].minus(matrix.rows[i]);
        }
    }

    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiply(scalar);
        }
    }

    public Vector multiplyByVector(Vector vector) {
        if (this.getRowsNumber() != vector.getSize()) {
            throw new IllegalArgumentException("Несовпадение размерностей!");
        }

        Vector resultVector = new Vector(vector.getSize());

        for (int i = 0; i < vector.getSize(); ++i) {
            resultVector.setCoordinate(i, Vector.scalarProduct(rows[i], vector));
        }

        return resultVector;
    }

    public static Matrix sumOf(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsNumber() != matrix2.getRowsNumber() || matrix1.getColumnsNumber() != matrix2.getColumnsNumber()) {
            throw new IllegalArgumentException("Несовпадение размерностей!");
        }

        Matrix tempMatrix = new Matrix(matrix1);
        tempMatrix.plus(matrix2);
        return tempMatrix;
    }

    public static Matrix difference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsNumber() != matrix2.getRowsNumber() || matrix1.getColumnsNumber() != matrix2.getColumnsNumber()) {
            throw new IllegalArgumentException("Несовпадение размерностей!");
        }

        Matrix tempMatrix = new Matrix(matrix1);
        tempMatrix.minus(matrix2);
        return tempMatrix;
    }

    public static Matrix matrixMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsNumber() != matrix2.getRowsNumber()) {
            throw new IllegalArgumentException("Данные матрицы нельзя перемножить - неподходящие размерности!");
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

    private double[][] getArray() {
        double[][] arrayFromMatrix = new double[this.getRowsNumber()][this.getColumnsNumber()];

        for (int i = 0; i < this.getRowsNumber(); ++i) {
            for (int j = 0; j < this.getColumnsNumber(); ++j) {
                arrayFromMatrix[i][j] = this.getRow(i).getCoordinate(j);
            }
        }

        return arrayFromMatrix;
    }

    private static int getIndexMaxAbsElement(double[] array) {
        int indexMaxAbsElement = 0;

        for (int i = 1; i < array.length; ++i) {
            indexMaxAbsElement = (Math.abs(array[i]) > Math.abs(array[indexMaxAbsElement])) ? i : indexMaxAbsElement;
        }

        return indexMaxAbsElement;
    }

    public double getDeterminant() {
        if (this.getRowsNumber() != this.getColumnsNumber()) {
            throw new IllegalArgumentException("Определитель можно считать только от квадратной матрицы!");
        }

        double[][] matrix = this.getArray();

        double[][] triangleMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, triangleMatrix[i], 0, matrix.length);
        }

        int rowReplaceCounter = 0;

        for (int k = 0; k < matrix.length - 1; ++k) {
            double[] tempArray = new double[matrix.length - k];
            for (int i = 0; i < tempArray.length; ++i) {
                tempArray[i] = triangleMatrix[k + i][k];
            }

            int index = k + getIndexMaxAbsElement(tempArray);

            double epsilon = 1e-8;
            if (Math.abs(triangleMatrix[index][k]) <= epsilon) {
                return 0;
            }

            if (k != index) {
                System.arraycopy(triangleMatrix[k], k, tempArray, 0, tempArray.length);
                System.arraycopy(triangleMatrix[index], k, triangleMatrix[k], k, tempArray.length);
                System.arraycopy(tempArray, 0, triangleMatrix[index], k, tempArray.length);

                ++rowReplaceCounter;
            }

            for (int j = k + 1; j < matrix.length; ++j) {
                double coefficient = triangleMatrix[j][k] / triangleMatrix[k][k];

                for (int i = k; i < matrix.length; ++i) {
                    triangleMatrix[j][i] -= coefficient * triangleMatrix[k][i];
                }
            }
        }

        double determinant = 1;

        for (int i = 0; i < matrix.length; ++i) {
            determinant *= triangleMatrix[i][i];
        }

        if (rowReplaceCounter % 2 == 1) {
            determinant = -determinant;
        }

        return determinant;
    }
}