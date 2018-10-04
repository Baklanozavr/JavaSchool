package ru.academits.baklanov.tasks;

public class Rectangle implements Shape {
    private double height;
    private double width;

    public Rectangle(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }

    public String toString() {
        return String.format("Rectangle with width %s and height %s", width, height);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || o.getClass() != this.getClass()) {
            return false;
        } else {
            Rectangle r = (Rectangle) o;
            return height == r.height && width == r.width;
        }
    }

    public int hashCode() {
        return Double.hashCode(width + height);
    }
}