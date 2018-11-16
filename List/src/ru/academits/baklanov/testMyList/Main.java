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

        System.out.println(testListCopy);

        int index = 4;
        String string = "000";

        System.out.printf("Первый элемент: %s%n", testList.getFirst());
        System.out.printf("Элемент с индексом %s: %s%n", index, testList.set(index, string));
        System.out.printf("Заменён на элемент %s: %s%n", string, testList.get(index));
        System.out.printf("Количество элементов: %s%n", testList.size());
        System.out.println(testList);

        System.out.printf("Теперь элемента %s по индексу %s нет%n", testList.remove(index), index);
        System.out.println(testList);

        testList.add(index, string);
        System.out.printf("Вернули элемент %s обратно по индексу %s%n", string, index);
        System.out.println(testList);

        if (testList.remove(string)) {
            System.out.printf("Снова удалили элемент %s%n", string);
            System.out.println(testList);
        }

        System.out.printf("Удалили первый элемент: %s%n", testList.removeFirst());
        System.out.println(testList);

        System.out.println("Развернём список:");
        testList.reverse();
        System.out.println(testList);

        System.out.println("Начальная копия списка");
        System.out.println(testListCopy);

        System.out.println("Удалим её");
        testListCopy.clear();
        System.out.println(testListCopy);
    }
}