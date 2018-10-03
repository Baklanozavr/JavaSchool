package ru.academits.baklanov.tasks;

import java.util.Arrays;

public class Vector {
    private double[] coordinates;

    public Vector(int dimension) {
        if (dimension > 0) {
            coordinates = new double[dimension];
        } else {
            throw new IllegalArgumentException("Размерность вектора должна быть больше ноля!");
        }
    }

    public Vector(Vector vector) {
        coordinates = new double[vector.coordinates.length];
        System.arraycopy(vector.coordinates, 0, this.coordinates, 0, vector.coordinates.length);
    }

    public Vector(double[] coordinates) {
        this.coordinates = new double[coordinates.length];
        System.arraycopy(coordinates, 0, this.coordinates, 0, coordinates.length);
    }

    public Vector(int dimension, double[] coordinates) {
        if (coordinates.length <= dimension) {
            this.coordinates = new double[dimension];
            System.arraycopy(coordinates, 0, this.coordinates, 0, coordinates.length);
        } else {
            throw new IllegalArgumentException("Размерность вектора не может быть меньше размерности массива координат!");
        }

    }


    public int getSize() {
        return coordinates.length;
    }

    public double getLength() {
        double squaredLength = 0;

        for (double x : this.coordinates) {
            squaredLength += x * x;
        }

        return Math.sqrt(squaredLength);
    }

    public double getCoordinate(int position) {
        return coordinates[position - 1];
    }

    public void setCoordinate(int position, double newCoordinate) {
        coordinates[position - 1] = newCoordinate;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("{ " + coordinates[0]);
        for (int i = 1; i < coordinates.length; ++i) {
            result.append(", ").append(coordinates[i]);
        }
        return result.append(" }").toString();
    }

    public Vector plus(Vector vector) {
        Vector maxVector = (this.getSize() >= vector.getSize()) ? this : vector;
        Vector minVector = (this.getSize() < vector.getSize()) ? this : vector;

        Vector tempVector = new Vector(maxVector.coordinates);

        for (int i = 0; i < minVector.getSize(); ++i) {
            tempVector.coordinates[i] += minVector.coordinates[i];
        }

        return tempVector;
    }

    public Vector minus(Vector vector) {
        Vector maxVector = (this.getSize() >= vector.getSize()) ? this : vector;

        Vector tempVector = new Vector(maxVector.getSize(), this.coordinates);

        for (int i = 0; i < vector.getSize(); ++i) {
            tempVector.coordinates[i] -= vector.coordinates[i];
        }

        return tempVector;
    }

    public Vector multiply(double scalar) {
        Vector tempVector = new Vector(this.getSize());

        for (int i = 0; i < tempVector.getSize(); ++i) {
            tempVector.coordinates[i] = this.coordinates[i] * scalar;
        }

        return tempVector;
    }

    public void reverse() {
        for (int i = 0; i < this.getSize(); ++i) {
            this.coordinates[i] *= -1;
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || o.getClass() != this.getClass()) {
            return false;
        } else {
            Vector v = (Vector) o;
            return Arrays.equals(this.coordinates, v.coordinates);
        }
    }

    public int hashCode() {
        return Arrays.hashCode(this.coordinates);
    }


    public static Vector sumOf(Vector vector1, Vector vector2) {
        Vector maxVector = (vector1.getSize() >= vector2.getSize()) ? vector1 : vector2;
        Vector minVector = (vector1.getSize() < vector2.getSize()) ? vector1 : vector2;

        Vector tempVector = new Vector(maxVector.coordinates);

        for (int i = 0; i < minVector.getSize(); ++i) {
            tempVector.coordinates[i] += minVector.coordinates[i];
        }

        return tempVector;
    }

    public static Vector difference(Vector vector1, Vector vector2) {
        Vector maxVector = (vector1.getSize() >= vector2.getSize()) ? vector1 : vector2;

        Vector tempVector = new Vector(maxVector.getSize(), vector1.coordinates);

        for (int i = 0; i < vector2.getSize(); ++i) {
            tempVector.coordinates[i] -= vector2.coordinates[i];
        }

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
}