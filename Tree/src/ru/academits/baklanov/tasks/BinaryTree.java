package ru.academits.baklanov.tasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
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

        TreeNode<T> getNext(Direction where) {
            if (where == Direction.LEFT) {
                return left;
            }
            return right;
        }

        TreeNode<T> getNext(int key) {
            if (key < 0) {
                return left;
            }
            return right;
        }

        void addNext(T element, Direction direction) {
            if (direction == Direction.LEFT) {
                left = new TreeNode<>(element);
            } else {
                right = new TreeNode<>(element);
            }
        }

        void setNext(TreeNode<T> node, Direction direction) {
            if (direction == Direction.LEFT) {
                left = node;
            } else {
                right = node;
            }
        }
    }

    private enum Direction {
        LEFT, RIGHT, ZERO;

        static Direction get(int biggerOrLesser) {
            if (biggerOrLesser < 0) {
                return LEFT;
            } else {
                return RIGHT;
            }
        }
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

        TreeNode<E> node = root;
        TreeNode<E> parent = node;
        Direction direction = Direction.ZERO;

        while (node != null) {
            direction = Direction.get(comparator.compare(element, node.data));
            parent = node;
            node = node.getNext(direction);
        }

        parent.addNext(element, direction);
        ++size;
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

    private TreeNode<E> deleteChild(TreeNode<E> parent, Direction direction) {
        if (parent == null) {
            throw new NullPointerException("Узел не найден!");
        }

        TreeNode<E> child = parent.getNext(direction);

        if (child == null) {
            throw new IllegalArgumentException("У данного узла нет детей!");
        }
        if (child.left != null && child.right != null) {
            throw new IllegalArgumentException("У данного узла больше одного внука!");
        }

        TreeNode<E> grandChild = child.left == null ? child.right : child.left;

        parent.setNext(grandChild, direction);

        return child;
    }

    public boolean remove(E element) {
        if (root == null) {
            throw new NoSuchElementException("This tree is empty!");
        }

        TreeNode<E> node = root;
        TreeNode<E> parent = null;
        Direction direction = Direction.ZERO;

        while (node != null) {
            direction = Direction.get(comparator.compare(element, node.data));

            if (direction == Direction.ZERO) {
                break;
            }

            parent = node;
            node = node.getNext(direction);
        }

        if (node == null) {
            return false;
        }

        if (node.left == null || node.right == null) {
            TreeNode<E> child = node.left == null ? node.right : node.left;

            if (parent == null) {
                root = child;
            } else {
                parent.setNext(child, direction);
            }
        } else {
            TreeNode<E> parentMinChild = node.right;

            while (parentMinChild.left != null) {
                if (parentMinChild.left.left != null) {
                    parentMinChild = parentMinChild.left;
                }
            }

            node = deleteChild(parentMinChild, Direction.LEFT);

            if (parent == null) {
                root = node;
            } else {
                parent.setNext(node, direction);
            }
        }

        --size;
        return true;
    }

    public void printTree() {
        if (root == null) {
            throw new NoSuchElementException("This tree is empty!");
        }

        int treeDepth = countDepth();
        int leftGap = 8;
        int mainGap = 8;
        int levelCounter = 1;
        int elementPerLevelCounter = 0;
        int nextLevelElementsCounter = 0;

        String formatString = "%" + mainGap + "s";
        String formatNewString = "%n%" + leftGap + "s";

        LinkedList<TreeNode<E>> queue = new LinkedList<>();

        queue.addLast(root);
        ++elementPerLevelCounter;

        while (queue.size() > 0) {
            TreeNode<E> node = queue.pop();
            --elementPerLevelCounter;

            System.out.printf(formatString, node.data);

            if (node.left != null) {
                queue.addLast(node.left);
                ++nextLevelElementsCounter;
            }
            if (node.right != null) {
                queue.addLast(node.right);
                ++nextLevelElementsCounter;
            }

            if (elementPerLevelCounter == 0) {
                System.out.printf(formatNewString, " ");
                ++levelCounter;
                elementPerLevelCounter = nextLevelElementsCounter;
                nextLevelElementsCounter = 0;
            }
        }
    }

    private int countDepth() {
        if (root == null) {
            return 0;
        }

        int levelCounter = 1;
        int elementPerLevelCounter = 0;
        int nextLevelElementsCounter = 0;

        LinkedList<TreeNode<E>> queue = new LinkedList<>();

        queue.addLast(root);
        ++elementPerLevelCounter;

        while (queue.size() > 0) {
            TreeNode<E> node = queue.pop();
            --elementPerLevelCounter;

            if (node.left != null) {
                queue.addLast(node.left);
                ++nextLevelElementsCounter;
            }
            if (node.right != null) {
                queue.addLast(node.right);
                ++nextLevelElementsCounter;
            }

            if (elementPerLevelCounter == 0) {
                ++levelCounter;
                elementPerLevelCounter = nextLevelElementsCounter;
                nextLevelElementsCounter = 0;
            }
        }

        return levelCounter;
    }
}