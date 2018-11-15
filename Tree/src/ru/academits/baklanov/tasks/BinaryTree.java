package ru.academits.baklanov.tasks;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class BinaryTree<E extends Integer> {
    private TreeNode<E> root;
    private Comparator<E> comparator;
    private int size;

    public BinaryTree() {
        root = null;
        this.comparator = new TreeComparator();
        size = 0;
    }

    public E getRoot() {
        return root.data;
    }

    public int getSize() {
        return size;
    }

    public void add(E element) {
        if (root == null) {
            root = new TreeNode<>(element);
            ++size;
            return;
        }

        for (TreeNode<E> node = root; ; ) {
            if (comparator.compare(element, node.data) < 0) {
                if (node.left != null) {
                    node = node.left;
                } else {
                    node.left = new TreeNode<>(element);
                    ++size;
                    return;
                }
            } else {
                if (node.right != null) {
                    node = node.right;
                } else {
                    node.right = new TreeNode<>(element);
                    ++size;
                    return;
                }
            }
        }
    }

    public TreeNode<E> findNode(E sample) {
        if (root == null) {
            throw new NoSuchElementException("This tree is empty!");
        }

        for (TreeNode<E> node = root; node != null; ) {
            if (comparator.compare(sample, node.data) < 0) {
                node = node.left;
            } else if (comparator.compare(sample, node.data) > 0) {
                node = node.right;
            } else {
                return node;
            }
        }

        return null;
    }

    public boolean remove(E sample) {
        if (root == null) {
            throw new NoSuchElementException("This tree is empty!");
        }

        TreeNode<E> node = root;
        TreeNode<E> parent = null;
        boolean isLeft = false;

        while (node != null) {
            if (comparator.compare(sample, node.data) < 0) {
                parent = node;
                node = node.left;
                isLeft = true;
            } else if (comparator.compare(sample, node.data) > 0) {
                parent = node;
                node = node.right;
                isLeft = false;
            } else {
                break;
            }
        }

        if (node == null) {
            return false;
        }

        if (node.left == null && node.right == null) {
            if (parent == null) {
                root = null;
            } else {
                if (isLeft) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        } else if (node.left == null || node.right == null) {
            if (parent == null) {
                root = node.left == null ? node.right : node.left;
            } else {
                if (isLeft) {
                    parent.left = node.left == null ? node.right : node.left;
                } else {
                    parent.right = node.left == null ? node.right : node.left;
                }
            }
        } else {
            TreeNode<E> parentMinChild = node.right;
            TreeNode<E> minChild = parentMinChild.left;

            if (minChild == null) {
                minChild = parentMinChild;
                node.right = minChild.right;
            } else {
                while (minChild.left != null) {
                    parentMinChild = minChild;
                    minChild = minChild.left;
                }

                parentMinChild.left = minChild.right;
            }

            minChild.left = node.left;
            minChild.right = node.right;

            if (parent == null) {
                root = minChild;
            } else {
                if (isLeft) {
                    parent.left = minChild;
                } else {
                    parent.right = minChild;
                }
            }
        }

        --size;
        return true;
    }

    public void printTree() {
        if (root == null) {
            throw new NoSuchElementException("This tree is empty!");
        }

        printNode(0, root);
    }

    private void printNode(int i, TreeNode<E> node) {
        StringBuilder s = new StringBuilder();
        for (int j = 0; j < i; j++){
            s.append(" ");
        }

        System.out.printf("%s%s%n", s.toString(), node.data);
        ++i;

        if (node.left != null) {
            System.out.print("-1 ");
            printNode(i, node.left);
        }

        if (node.right != null) {
            System.out.print("+1");
            printNode(i, node.right);
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
    }
}