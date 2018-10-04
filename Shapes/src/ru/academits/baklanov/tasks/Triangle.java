package ru.academits.baklanov.tasks;

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
        double a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double b = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        double c = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));

        return a + b + c;
    }

    public String toString() {
        double a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double b = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        double c = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));

        return String.format("Triangle with sides: %s, %s, %s", a, b, c);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || o.getClass() != this.getClass()) {
            return false;
        } else {
            Triangle t = (Triangle) o;

            double aThis = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            double bThis = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
            double cThis = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));

            double a = Math.sqrt(Math.pow(t.x2 - t.x1, 2) + Math.pow(t.y2 - t.y1, 2));
            double b = Math.sqrt(Math.pow(t.x3 - t.x1, 2) + Math.pow(t.y3 - t.y1, 2));
            double c = Math.sqrt(Math.pow(t.x2 - t.x3, 2) + Math.pow(t.y2 - t.y3, 2));

            return aThis == a && bThis == b && cThis == c;
        }
    }

    public int hashCode() {
        return Double.hashCode(x1 + x2 + x3 + y1 + y2 + y3);
    }
}