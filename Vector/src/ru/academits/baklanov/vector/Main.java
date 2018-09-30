package ru.academits.baklanov.vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(3);
        System.out.println(vector1.toString());

        double[] testCoordinates1 = { 0, 0, 1, 2, 2 };
        Vector vector2 = new Vector(testCoordinates1);
        System.out.println(vector2.toString());

        Vector vector3 = new Vector(vector2);
        System.out.println(vector3.toString());

        double[] testCoordinates2 = { 1, 1, 2 };
        Vector vector4 = new Vector(4, testCoordinates2);
        System.out.println(vector4.toString());


        System.out.println(vector4.plus(vector2).toString());
        System.out.println(vector4.minus(vector2).toString());
        System.out.println(vector2.minus(vector4).toString());
        System.out.println(vector4.multiply(5).toString());
        vector4.reverse();
        System.out.println(vector4.getLength());
    }
}