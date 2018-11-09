package ru.academits.baklanov.testMyArrayList;

import ru.academits.baklanov.tasks.MyArrayList;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> testList = new MyArrayList<>(10);
        MyArrayList<String> testList2 = new MyArrayList<>();

        if (testList2.isEmpty()) {
            testList2.ensureCapacity(13);

            testList2.add("123");
            testList2.add("123");
            testList2.add("45");
        }

        testList.add("123");
        testList.add("123");
        testList.add("123");
        testList.add("45");
        testList.add(1, "000");

        testList.trimToSize();

        System.out.println(testList.set(0, "5"));
        System.out.println();

        for (String s : testList) {
            System.out.println(s);
        }

        /*
        if (testList.containsAll(testList2)) {
            for (String s : testList) {
                System.out.println(s);
            }
        }
        System.out.println();

        testList.removeAll(testList2);
        for (String s : testList) {
            System.out.println(s);
        }
        */
    }
}