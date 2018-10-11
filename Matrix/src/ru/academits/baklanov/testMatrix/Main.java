package ru.academits.baklanov.testMatrix;

import ru.academits.baklanov.tasks.Matrix;
import ru.academits.baklanov.tasks.Vector;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double[][] testArray2D = {{1, 2}, {2, 3, 4}, {5, 6}};

        double[] testArray1 = {1, 2};
        double[] testArray2 = {5, 3, 1};

        Vector[] vectors = new Vector[2];
        vectors[0] = new Vector(testArray1);
        vectors[1] = new Vector(testArray2);

        System.out.println("Введите размерность матрицы (два целых числа):");
        Matrix matrix1 = new Matrix(scanner.nextInt(), scanner.nextInt());
        System.out.printf("Тест инициализации матрицы1 через размерность: %s%n", matrix1);
        System.out.printf("Размер этой матрицы: %s на %s%n", matrix1.getRowsNumber(), matrix1.getColumnsNumber());

        Matrix matrix2 = new Matrix(matrix1);
        System.out.printf("Тест инициализации матрицы2 через копирование матрицы 1: %s%n", matrix2);

        Matrix matrix3 = new Matrix(testArray2D);
        System.out.printf("Тест инициализации матрицы3 через двумерный массив: %s%n", matrix3);

        Matrix matrix4 = new Matrix(vectors);
        System.out.printf("Тест инициализации матрицы4 через массив векторов: %s%n", matrix4);

        System.out.printf("Второй столбец матрицы3: %s%n", matrix3.getColumn(1));

        matrix1.setRow(0, vectors[1]);
        System.out.printf("Новая строка в матрице1: %s%n", matrix1);
        System.out.printf("Вот она: %s%n", matrix1.getRow(0));

        matrix4.multiplyByScalar(2);
        System.out.printf("Скалярное умножение матрицы4 на 2: %s%n", matrix4);

        System.out.printf("Транспонирование матрицы4: %s%n", matrix4.transpose());

        matrix1.plus(matrix4.transpose());
        System.out.printf("Матрица1 плюс транспонированная матрица4: %s%n", matrix1);

        matrix1.minus(matrix4.transpose());
        System.out.printf("Матрица1 минус транспонированная матрица4: %s%n", matrix1);
    }
}