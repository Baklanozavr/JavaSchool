package ru.academits.baklanov.tasks;

import java.util.Comparator;

public class BinaryTree<E extends Integer> {
    private TreeNode<E> root;
    private Comparator<E> comparator;

    public BinaryTree() {
        root = null;
        this.comparator = new TreeComparator();
    }

    public E getRoot() {
        return root.getData();
    }

    public void add(E element) {
        for (TreeNode<E> node = root; node != null; ) {
            if (comparator.compare(element, node.getData()) < 0) {
                if (node.left != null) {
                    node = node.left;
                } else {
                    node.left = new TreeNode<>(element);
                }
            } else {
                if (node.right != null) {
                    node = node.right;
                } else {
                    node.right = new TreeNode<>(element);
                }
            }
        }
    }


    private class TreeComparator implements Comparator<E> {
        @Override
        public int compare(E n1, E n2) {
            return Integer.compare(n1, n2);
        }
    }

    private class TreeNode<T> {
        private T data;
        private TreeNode<T> left;
        private TreeNode<T> right;

        TreeNode(T data) {
            this.data = data;
            left = null;
            right = null;
        }

        void paste(E element) {

        }

        T getData() {
            return data;
        }

        boolean hasLeft() {
            return left != null;
        }

        boolean hasRight() {
            return right != null;
        }

        void setData(T data) {
            this.data = data;
        }

        TreeNode<T> getLeft() {
            return left;
        }

        void setLeft(TreeNode<T> node) {
            left = node;
        }

        TreeNode<T> getRight() {
            return right;
        }

        void setRight(TreeNode<T> node) {
            right = node;
        }
    }
}