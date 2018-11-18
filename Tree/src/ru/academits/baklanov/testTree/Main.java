package ru.academits.baklanov.testTree;

import ru.academits.baklanov.tasks.BinaryTree;

public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer> myTree = new BinaryTree<>();

        /*int nodesNumber = 10;
        int maxElement = 20;

        for (int i = 0; i < nodesNumber; ++i) {
            myTree.add((int) (Math.random() * maxElement));
        }*/

        myTree.add(6);
        myTree.add(6);
        myTree.add(10);
        myTree.add(8);
        myTree.add(17);
        myTree.add(7);
        myTree.add(13);
        myTree.add(6);
        myTree.add(12);
        myTree.add(15);

        myTree.printTree();
    }
}