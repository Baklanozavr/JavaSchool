package ru.academits.baklanov.testTree;

import ru.academits.baklanov.tasks.BinaryTree;

public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer> myTree = new BinaryTree<>();

        int nodesNumber = 10;
        int maxElement = 20;

        for (int i = 0; i < nodesNumber; ++i) {
            myTree.add((int) (Math.random() * maxElement));
        }

        myTree.printTree();
        System.out.println("Размер: " + myTree.getSize());
        System.out.println("Обход в ширину: " + myTree.toStringByWidth());
        System.out.println("Обход в глубину: " + myTree.toStringByDepth());

        int sample = (int) (Math.random() * maxElement);

        if (myTree.findNode(sample) != null) {
            System.out.printf("Удаляем элемент %s %n", sample);
            if (myTree.remove(sample)) {
                myTree.printTree();
            }
        } else {
            System.out.printf("Элемента %s нет в дереве", sample);
        }
    }
}