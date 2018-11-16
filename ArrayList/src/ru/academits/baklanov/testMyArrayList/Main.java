package ru.academits.baklanov.testMyArrayList;

import ru.academits.baklanov.tasks.MyArrayList;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> testList = new MyArrayList<>(3);
        MyArrayList<String> testList2 = new MyArrayList<>();

        testList.add("111");
        testList.add("222");
        testList.add("333");

        testList2.add("333");
        testList2.add("bbb");

        System.out.printf("Первый список размером %s%n", testList.size());
        System.out.println(testList);

        System.out.printf("Второй список размером %s%n", testList2.size());
        System.out.println(testList2);

        if (testList2.isEmpty()) {
            testList2.ensureCapacity(13);

            testList2.add("000");
        }

        testList.add(1, "000");
        testList.add(3, "000");

        testList.trimToSize();

        System.out.println(testList.set(0, "5"));
        System.out.println();

        testList2.set(0, "777");
        testList.addAll(testList2);
        System.out.println(testList);

        if (testList.containsAll(testList2)) {
            System.out.println(testList);
        }

        testList2.clear();

        if (testList.removeAll(testList2)) {
            String[] strings = new String[2];
            for (String s : testList.toArray(strings)) {
                System.out.println(s);
            }
        }
    }
}