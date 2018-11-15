package ru.academits.baklanov.testMyArrayList;

import ru.academits.baklanov.tasks.MyArrayList;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> testList = new MyArrayList<>(3);
        MyArrayList<String> testList2 = new MyArrayList<>();

        int index = 1;

        testList.add("111");
        testList.add("222");
        testList.add("333");

        testList2.add("aaa");
        testList2.add("bbb");

        System.out.printf("Первый список размером %s%n", testList.size());
        testList.print();
        System.out.println();

        System.out.printf("Второй список размером %s%n", testList2.size());
        testList2.print();
        System.out.println();
/*
        System.out.println("Добавляем в конец первого списка второй список:");
        testList.addAll(testList2);
        testList.print();
        System.out.println();

        System.out.println("Очистили второй список");
        testList2.clear();
        testList2.print();
        System.out.println();
        testList.print();
*/
        System.out.printf("Делаем вставку второго списка в первый по индексу %s%n", index);
        testList.addAll(index, testList2);
        System.out.printf("Теперь первый список размером %s%n", testList.size());
        testList.print();





        /*
        if (testList2.isEmpty()) {
            testList2.ensureCapacity(13);

            testList2.add("999");
            testList2.add("666");
        }

        testList.add("123");
        testList.add("123");
        testList.add("123");
        testList.add("45");
        testList.add(1, "000");
        testList.add(5, "000");

        testList.trimToSize();

        System.out.println(testList.set(0, "5"));
        System.out.println();

        testList.addAll(testList2);
        testList.print();
        System.out.println();

        testList2.set(0, "777");
        testList.addAll(testList2);
        testList.print();
        System.out.println();

        testList.print();
        System.out.println();

        if (testList.containsAll(testList2)) {
            testList.print();
        }
        System.out.println();

        testList.removeAll(testList2);
        String[] strings = new String[2];
        for (String s : testList.toArray(strings)) {
            System.out.println(s);
        }*/
    }
}