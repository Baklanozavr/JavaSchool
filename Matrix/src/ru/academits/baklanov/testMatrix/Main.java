package ru.academits.baklanov.testMatrix;

import ru.academits.baklanov.tasks.Matrix;
import ru.academits.baklanov.tasks.Vector;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Matrix testMatrix;

        double[][] testArray2D = {{1, 2}, {2, 3}, {4, 5}};

        double[] testArray1 = {1, 2};
        double[] testArray2 = {5, 3, 1};

        Vector[] vectors = new Vector[2];
        vectors[0] = new Vector(testArray1);
        vectors[1] = new Vector(testArray2);
/*
        System.out.println("Введите размерность матрицы (два целых числа):");
        Matrix matrix = new Matrix(scanner.nextInt(), scanner.nextInt());
        System.out.printf("Тест инициализации матрицы нулей через размерность: %s%n", matrix);
        System.out.printf("Размер этой матрицы: %s на %s%n", matrix.getRowsNumber(), matrix.getColumnsNumber());
*/
        Matrix matrix1 = new Matrix(testArray2D);
        System.out.printf("Тест инициализации матрицы1 через двумерный массив: %s%n", matrix1);

        Matrix matrix2 = new Matrix(vectors);
        System.out.printf("Тест инициализации матрицы2 через массив векторов: %s%n", matrix2);
/*
        testMatrix = new Matrix(matrix1);
        testMatrix.plus(matrix2);
        System.out.printf("Сложение матриц: %s%n", testMatrix);
        System.out.println(Matrix.sumOf(matrix1, matrix2));

        testMatrix = new Matrix(matrix1);
        testMatrix.minus(matrix2);
        System.out.printf("Вычитание матриц: %s%n", testMatrix);
        System.out.println(Matrix.difference(matrix1, matrix2));
*/
        System.out.printf("Умножение матриц: %s%n", Matrix.matrixMultiplication(matrix1, matrix2));

        System.out.println("Умножение матрицы1 на столбец1 матрицы2");
        System.out.println(matrix1.multiplyByVector(matrix2.getColumn(0)));
/*
        testMatrix = new Matrix(matrix1);
        testMatrix.setRow(0, matrix2.getRow(0));
        System.out.println("Замена первой строки матрицы1 на строку1 матрицы2");
        System.out.println(matrix1.multiplyByVector(matrix2.getRow(0)));
*/
        testMatrix = new Matrix(matrix1);
        testMatrix.multiplyByScalar(2);
        System.out.printf("Скалярное умножение матрицы на 2: %s%n", testMatrix);

        testMatrix.transpose();
        System.out.printf("Транспонирование матрицы: %s%n", testMatrix);
    }
}