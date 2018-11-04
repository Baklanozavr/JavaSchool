package ru.academits.baklanov.testMyArrayList;

import ru.academits.baklanov.tasks.MyArrayList;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> testList = new MyArrayList<>(10);
        MyArrayList<String> testList2 = new MyArrayList<>();

        if (testList2.isEmpty()) {
            testList2.add("test");
            testList2.add("7");
        }

        testList.ensureCapacity(20);
        testList.trimToSize(4);

        testList.add("123");
        testList.add("45");
        testList.add(1, "000");

        for (String s : testList) {
            System.out.println(s);
        }

        System.out.println(testList.remove(2));

        testList.addAll(1, testList2);

        for (String s : testList) {
            System.out.println(s);
        }

        testList.set(0, "33");

        testList.remove("000");

        for (String s : testList) {
            System.out.println(s);
        }

        System.out.println(testList.size());

        testList.clear();

        System.out.println(testList.size());
    }
}