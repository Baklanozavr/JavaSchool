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

        Matrix matrix2 = new Matrix(matrix1);
        System.out.printf("Тест инициализации матрицы2 через копирование матрицы 1: %s%n", matrix2);

        Matrix matrix3 = new Matrix(testArray2D);
        System.out.printf("Тест инициализации матрицы3 через двумерный массив: %s%n", matrix3);

        Matrix matrix4 = new Matrix(vectors);
        System.out.printf("Тест инициализации матрицы4 через массив векторов: %s%n", matrix4);
    }
}