package ru.academits.baklanov.testRange;

import ru.academits.baklanov.tasks.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Range range1 = new Range(0, 10);

        System.out.println("Введите число: ");
        String isInside = " не";
        if (range1.isInside(scanner.nextDouble())) {
            isInside = "";
        }
        System.out.printf("Данное число%s лежит в диапазоне [%f, %f]%n", isInside, range1.getFrom(), range1.getTo());

        System.out.println("Введите новые границы для данного диапазона: ");
        range1.setFrom(scanner.nextDouble());
        range1.setTo(scanner.nextDouble());

        System.out.println("Длина полученного диапазона равна " + range1.getLength());

        System.out.println("Введите границы для второго диапазона: ");
        Range range2 = new Range(scanner.nextDouble(), scanner.nextDouble());

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
        if (range1.minus(range2).length != 0) {
            for (Range e : range1.minus(range2)) {
                System.out.printf("[%f, %f]%n", e.getFrom(), e.getTo());
            }
        } else {
            System.out.print("Пустое множество");
        }
    }
}