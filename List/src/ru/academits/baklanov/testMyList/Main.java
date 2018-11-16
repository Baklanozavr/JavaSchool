package ru.academits.baklanov.testMyList;

import ru.academits.baklanov.tasks.MySingleLinkedList;

public class Main {
    public static void main(String[] args) {
        MySingleLinkedList<String> testList = new MySingleLinkedList<>();

        testList.addFirst("555");
        testList.addFirst("444");
        testList.addFirst("333");
        testList.addFirst("222");
        testList.addFirst("111");

        MySingleLinkedList<String> testListCopy = testList.copy();

        testListCopy.print();
        System.out.println();

        int index = 4;
        String string = "000";

        System.out.printf("Первый элемент: %s%n", testList.getFirst());
        System.out.printf("Элемент с индексом %s: %s%n", index, testList.set(index, string));
        System.out.printf("Заменён на элемент %s: %s%n", string, testList.get(index));
        System.out.printf("Количество элементов: %s%n", testList.size());
        testList.print();
        System.out.println();

        System.out.printf("Теперь элемента %s по индексу %s нет%n", testList.remove(index), index);
        testList.print();
        System.out.println();

        testList.add(index, string);
        System.out.printf("Вернули элемент %s обратно по индексу %s%n", string, index);
        testList.print();
        System.out.println();

        if (testList.remove(string)) {
            System.out.printf("Снова удалили элемент %s%n", string);
            testList.print();
            System.out.println();
        }

        System.out.printf("Удалили первый элемент: %s%n", testList.removeFirst());
        testList.print();
        System.out.println();

        System.out.println("Развернём список:");
        testList.reverse();
        testList.print();
        System.out.println();

        System.out.println("Начальная копия списка");
        testListCopy.print();
        System.out.println();

        System.out.println("Удалим её");
        testListCopy.clear();
        testListCopy.print();
    }
}