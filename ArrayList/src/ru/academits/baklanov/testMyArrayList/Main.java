package ru.academits.baklanov.testMyArrayList;

import ru.academits.baklanov.tasks.MyArrayList;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> testList = new MyArrayList<>(10);
        MyArrayList<String> testList2 = new MyArrayList<>();

        if (testList2.isEmpty()) {
            testList2.add("123");
            testList2.add("123");
            testList2.add("45");
        }

        testList.ensureCapacity(20);
        testList.trimToSize(4);

        testList.add("123");
        testList.add("45");
        testList.add(1, "000");

        for (String s : testList) {
            System.out.println(s);
        }
        System.out.println();

        //TODO надо исправить метод toArray
        if (testList.containsAll(testList2)) {
            for (String s : testList.toArray()) {
                System.out.println(s);
            }
        }
    }
}