package ru.academits.baklanov.testVector;

import ru.academits.baklanov.tasks.Vector;

import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double[] testArray1 = {1, 2};
        double[] testArray2 = {5, 3, 1};

        System.out.println("Введите размерность первого вектора:");
        Vector vector1 = new Vector(scanner.nextInt());
        System.out.printf("Тест инициализации экземпляра1 через размерность: %s%n", vector1);

        Vector vector2 = new Vector(testArray1);
        System.out.printf("Тест инициализации экземпляра2 через массив: %s %s%n", Arrays.toString(testArray1), vector2);

        Vector vector3 = new Vector(vector2);
        System.out.print("Тест инициализации экземпляра3 через копирование экземпляра2: ");
        System.out.println(vector3);
        System.out.println("Введите новую первую координату для экземпляра2:");
        vector2.setCoordinate(0, scanner.nextDouble());
        System.out.printf("Первая координата вектора3: %s, ", vector3.getCoordinate(0));
        System.out.printf("вектор2: %s%n", vector2);

        System.out.printf("Тест инициализации экземпляра4 через массив %s и выбранную размерность%n", Arrays.toString(testArray2));
        System.out.println("Введите размерность нового вектора:");
        Vector vector4 = new Vector(scanner.nextInt(), testArray2);
        System.out.println(vector4);

        System.out.println();
        System.out.println("вектор4 плюс вектор2 двумя способами:");
        System.out.println(Vector.sumOf(vector4, vector2));
        vector4.plus(vector2);
        System.out.println(vector4);

        System.out.println("вектор4 минус вектор3 двумя способами:");
        System.out.println(Vector.difference(vector4, vector3));
        vector4.minus(vector3);
        System.out.println(vector4);

        System.out.println("Скалярное произведение вектора4 и вектора3:");
        System.out.println(Vector.scalarProduct(vector4, vector3));

        vector4.reverse();
        System.out.printf("разворот вектора4: %s%n", vector4);

        System.out.printf("длинна вектора4: %s%n", vector4.getLength());

        vector4.multiply(5);
        System.out.printf("умножение вектора4 на 5: %s%n", vector4);

        System.out.printf("размерность вектора4: %s%n", vector4.getSize());
    }
}