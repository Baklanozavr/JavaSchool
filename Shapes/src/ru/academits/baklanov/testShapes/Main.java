package ru.academits.baklanov.testShapes;

import ru.academits.baklanov.tasks.*;

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    private static Shape getMaxSquareShape(Shape[] shapes) {
        Shape[] tempArray = Arrays.copyOf(shapes, shapes.length);

        Comparator<Shape> areaComparator = new ShapeAreaComparator();
        Arrays.sort(tempArray, areaComparator);

        return tempArray[shapes.length - 1];
    }

    private static Shape getSecondMaxPerimeterShape(Shape[] shapes) {
        Shape[] tempArray = Arrays.copyOf(shapes, shapes.length);

        Comparator<Shape> perimeterComparator = new ShapePerimeterComparator();
        Arrays.sort(tempArray, perimeterComparator);

        return tempArray[shapes.length - 2];
    }

    public static void main(String[] args) {
        Shape triangle1 = new Triangle(0, 0, 2, 0, 2, 2);
        Shape triangle2 = new Triangle(0, 0, 3, 0, 3, 2);
        Shape square1 = new Square(2);
        Shape rectangle1 = new Rectangle(2, 3);
        Shape rectangle2 = new Rectangle(4, 1.5);
        Shape circle1 = new Circle(1);
        Shape circle2 = new Circle(0.75);

        Shape[] shapes = {triangle1, triangle2, square1, rectangle1, rectangle2, circle1, circle2};

        System.out.println("Фигура с наибольшей площадью:");
        System.out.println(getMaxSquareShape(shapes));
        System.out.println();
        System.out.println("Высота и ширина для фигуры со вторым по величине периметром:");
        System.out.println(getSecondMaxPerimeterShape(shapes).getHeight());
        System.out.println(getSecondMaxPerimeterShape(shapes).getWidth());
    }
}