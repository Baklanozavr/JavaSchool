package ru.academits.baklanov.tasks;

import java.util.Arrays;

public class Vector {
    private double[] coordinates;

    public Vector(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть больше ноля!");
        }
        coordinates = new double[dimension];
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Вектор не найден! (null)");
        }
        coordinates = Arrays.copyOf(vector.coordinates, vector.coordinates.length);
    }

    public Vector(double[] coordinates) {
        this(coordinates.length);
        System.arraycopy(coordinates, 0, this.coordinates, 0, coordinates.length);
    }

    public Vector(int dimension, double[] coordinates) {
        this(dimension);

        if (coordinates.length > dimension) {
            throw new IllegalArgumentException("Размерность вектора не может быть меньше размерности массива координат!");
        }
        System.arraycopy(coordinates, 0, this.coordinates, 0, coordinates.length);
    }

    public int getSize() {
        return coordinates.length;
    }

    public double getLength() {
        double squaredLength = 0;

        for (double x : coordinates) {
            squaredLength += x * x;
        }

        return Math.sqrt(squaredLength);
    }

    public double getCoordinate(int position) {
        return coordinates[position];
    }

    public void setCoordinate(int position, double newCoordinate) {
        coordinates[position] = newCoordinate;
    }

    public void plus(Vector vector) {
        Vector maxVector = (this.getSize() >= vector.getSize()) ? this : vector;
        Vector minVector = (this.getSize() < vector.getSize()) ? this : vector;

        coordinates = Arrays.copyOf(maxVector.coordinates, maxVector.coordinates.length);

        for (int i = 0; i < minVector.getSize(); ++i) {
            coordinates[i] += minVector.coordinates[i];
        }
    }

    public void minus(Vector vector) {
        Vector maxVector = (this.getSize() >= vector.getSize()) ? this : vector;

        coordinates = Arrays.copyOf(coordinates, maxVector.coordinates.length);

        for (int i = 0; i < vector.getSize(); ++i) {
            coordinates[i] -= vector.coordinates[i];
        }
    }

    public void multiply(double scalar) {
        for (int i = 0; i < coordinates.length; ++i) {
            coordinates[i] *= scalar;
        }
    }

    public void reverse() {
        this.multiply(-1);
    }

    public static Vector sumOf(Vector vector1, Vector vector2) {
        Vector tempVector = new Vector(vector1);
        tempVector.plus(vector2);
        return tempVector;
    }

    public static Vector difference(Vector vector1, Vector vector2) {
        Vector tempVector = new Vector(vector1);
        tempVector.minus(vector2);
        return tempVector;
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        Vector minVector = (vector1.getSize() < vector2.getSize()) ? vector1 : vector2;

        double scalar = 0;

        for (int i = 0; i < minVector.getSize(); ++i) {
            scalar += vector1.coordinates[i] * vector2.coordinates[i];
        }

        return scalar;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{ " + coordinates[0]);
        for (int i = 1; i < coordinates.length; ++i) {
            result.append(", ").append(coordinates[i]);
        }
        return result.append(" }").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Vector v = (Vector) o;
        return Arrays.equals(this.coordinates, v.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.coordinates);
    }
}