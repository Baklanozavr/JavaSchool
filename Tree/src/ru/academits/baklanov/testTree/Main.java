package ru.academits.baklanov.testTree;

import ru.academits.baklanov.tasks.BinaryTree;

public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer> myTree = new BinaryTree<>();

        int nodesNumber = 10;
        int maxElement = 50;

        for (int i = 0; i < nodesNumber; ++i) {
            myTree.add((int) (Math.random() * maxElement));
        }

        myTree.printTree();
    }
}