package ru.academits.baklanov.vector;

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

        double[] newCoordinates = new double[maxVector.getSize()];

        for (int i = 0; i < minVector.getSize(); ++i) {
            newCoordinates[i] = this.coordinates[i] + vector.coordinates[i];
        }
        System.arraycopy(maxVector.coordinates, minVector.getSize(), newCoordinates, minVector.getSize(), maxVector.getSize() - minVector.getSize());

        return new Vector(newCoordinates);
    }

    public Vector minus(Vector vector) {
        Vector maxVector = (this.getSize() >= vector.getSize()) ? this : vector;
        Vector minVector = (this.getSize() < vector.getSize()) ? this : vector;

        double[] newCoordinates = new double[maxVector.getSize()];

        for (int i = 0; i < minVector.getSize(); ++i) {
            newCoordinates[i] = this.coordinates[i] - vector.coordinates[i];
        }

        if (this.getSize() >= vector.getSize()){
            System.arraycopy(this.coordinates, vector.getSize(), newCoordinates, vector.getSize(), this.getSize() - vector.getSize());
        } else {
            for (int i = minVector.getSize(); i < maxVector.getSize(); ++i) {
                newCoordinates[i] = - maxVector.coordinates[i];
            }
        }

        return new Vector(newCoordinates);
    }

    public Vector multiply(double scalar) {
        double[] newCoordinates = new double[this.getSize()];

        for (int i = 0; i < newCoordinates.length; ++i) {
            newCoordinates[i] = this.coordinates[i] * scalar;
        }

        return new Vector(newCoordinates);
    }

    public void reverse() {
        for (int i = 0; i < this.getSize(); ++i) {
            this.coordinates[i] *= -1;
        }
    }

    public double getLength() {
        double squaredLength = 0;

        for (double x : this.coordinates) {
            squaredLength += x * x;
        }

        return Math.sqrt(squaredLength);
    }
}