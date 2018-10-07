package ru.academits.baklanov.tasks.Shapes;

import ru.academits.baklanov.tasks.Shape;

public class Triangle implements Shape {
    private double x1;
    private double x2;
    private double x3;
    private double y1;
    private double y2;
    private double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    private double[] getSides() {
        double[] sides = new double[3];

        sides[0] = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        sides[1] = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        sides[2] = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));

        return sides;
    }

    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
    }

    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
    }

    public double getArea() {
        return 0.5 * Math.abs((x3 - x1) * (y3 - y2) - (x3 - x2) * (y3 - y1));
    }

    public double getPerimeter() {
        double[] sides = this.getSides();

        return sides[0] + sides[1] + sides[2];
    }

    public String toString() {
        double[] sides = this.getSides();

        return String.format("Triangle with sides: %s, %s, %s", sides[0], sides[1], sides[2]);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || o.getClass() != this.getClass()) {
            return false;
        } else {
            Triangle t = (Triangle) o;

            return t.x1 == x1 && t.x2 == x2 && t.x3 == x3 && t.y1 == y1 && t.y2 == y2 && t.y3 == y3;
        }
    }

    public int hashCode() {
        final int prime = 13;
        int hash = 1;
        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(y3);
        return hash;
    }
}