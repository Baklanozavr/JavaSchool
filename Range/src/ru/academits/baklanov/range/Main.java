package ru.academits.baklanov.range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите границы первого диапазона: ");
        Range range1 = new Range(scanner.nextDouble(),scanner.nextDouble());
        System.out.println("Длина первого диапазона равна " + range1.getLength());

        System.out.println("Введите границы второго диапазона: ");
        Range range2 = new Range(scanner.nextDouble(),scanner.nextDouble());
        System.out.println("Длина второго диапазона равна " + range2.getLength());

        if (range1.getIntersection(range2) != null) {
            Range range3 = range1.getIntersection(range2);
            System.out.printf("Результат пересечение данных диапазонов: [%f, %f]%n", range3.getFrom(), range3.getTo());
        } else {
            System.out.println("Данные диапазоны не пересекаются");
        }

        System.out.println("Результат объединения данных диапазонов: ");
        for (Range e : range1.getUnion(range2)) {
            System.out.printf("[%f, %f]%n", e.getFrom(), e.getTo());
        }

        System.out.println("Результат вычитания второго диапазона из первого: ");
        if (range1.minus(range2) != null) {
            for (Range e : range1.minus(range2)) {
                System.out.printf("[%f, %f]%n", e.getFrom(), e.getTo());
            }
        } else {
            System.out.println("Пустое множество");
        }
    }
}