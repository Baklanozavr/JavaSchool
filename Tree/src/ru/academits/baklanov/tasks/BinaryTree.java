package ru.academits.baklanov.tasks;

import java.util.*;

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

        void setNext(TreeNode<T> node, Direction direction) {
            if (direction == Direction.LEFT) {
                left = node;
            } else {
                right = node;
            }
        }

        TreeNode<T> getNext(Direction where) {
            if (where == Direction.LEFT) {
                return left;
            }
            return right;
        }
    }

    private enum Direction {
        LEFT, RIGHT;

        static Direction get(int biggerOrLesser) {
            if (biggerOrLesser < 0) {
                return LEFT;
            } else {
                return RIGHT;
            }
        }
    }

    private TreeNode<E> excludeMinRightChild(TreeNode<E> node) {
        if (node == null) {
            throw new NullPointerException("Узел не найден!");
        }
        if (node.right == null) {
            throw new IllegalArgumentException("У данного узла нет правых потомков!");
        }

        TreeNode<E> parentMinChild = node;
        TreeNode<E> minChild = node.right;

        if (minChild.left == null) {
            parentMinChild.setNext(minChild.right, Direction.RIGHT);
        } else {
            while (minChild.left != null) {
                parentMinChild = minChild;
                minChild = minChild.left;
            }
            parentMinChild.setNext(minChild.right, Direction.LEFT);
        }

        return minChild;
    }

    public int getSize() {
        return size;
    }

    public void add(E element) {
        if (root == null) {
            root = new TreeNode<>(element);
        } else {
            Direction direction = Direction.get(comparator.compare(element, root.data));
            TreeNode<E> parent = root;
            TreeNode<E> node = root.getNext(direction);

            while (node != null) {
                direction = Direction.get(comparator.compare(element, node.data));
                parent = node;
                node = node.getNext(direction);
            }

            parent.setNext(new TreeNode<>(element), direction);
        }
        ++size;
    }

    public boolean remove(E element) {
        if (root == null) {
            throw new NoSuchElementException("This tree is empty!");
        }

        TreeNode<E> node = root;
        TreeNode<E> parent = null;
        Direction direction = Direction.LEFT;

        while (node != null) {
            int resultOfComparison = comparator.compare(element, node.data);

            if (resultOfComparison == 0) {
                break;
            }

            direction = Direction.get(resultOfComparison);
            parent = node;
            node = node.getNext(direction);
        }

        if (node == null) {
            return false;
        }

        if (node.left == null || node.right == null) {
            node = node.left == null ? node.right : node.left;
        } else {
            TreeNode<E> minRightChild = excludeMinRightChild(node);

            minRightChild.left = node.left;
            minRightChild.right = node.right;

            node = minRightChild;
        }

        if (parent == null) {
            root = node;
        } else {
            parent.setNext(node, direction);
        }

        --size;
        return true;
    }

    public TreeNode<E> findNode(E sample) {
        if (root == null) {
            throw new NoSuchElementException("This tree is empty!");
        }

        for (TreeNode<E> node = root; node != null; ) {
            int resultOfComparison = comparator.compare(sample, node.data);

            if (resultOfComparison < 0) {
                node = node.left;
            } else if (resultOfComparison > 0) {
                node = node.right;
            } else {
                return node;
            }
        }

        return null;
    }

    public String toStringByWidth() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder bufferString = new StringBuilder();
        bufferString.append('[');

        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.add(root);

        for (TreeNode<E> node = queue.poll(); node != null; node = queue.poll()) {
            if (node.data == null) {
                bufferString.append("null, ");
            } else {
                bufferString.append(node.data.toString()).append(", ");
            }

            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        bufferString.replace(bufferString.length() - 2, bufferString.length(), "]");

        return bufferString.toString();
    }

    public String toStringByDepth() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder bufferString = new StringBuilder();
        bufferString.append('[');

        Deque<TreeNode<E>> stack = new LinkedList<>();
        stack.push(root);

        for (TreeNode<E> node = stack.poll(); node != null; node = stack.poll()) {
            if (node.data == null) {
                bufferString.append("null, ");
            } else {
                bufferString.append(node.data.toString()).append(", ");
            }

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        bufferString.replace(bufferString.length() - 2, bufferString.length(), "]");

        return bufferString.toString();
    }

    public void printTree() {
        if (root == null) {
            throw new NoSuchElementException("This tree is empty!");
        }

        printNode(1, root);
    }

    private void printNode(int i, TreeNode<E> node) {
        String stringFormat = "%" + i + "s%s%n";

        System.out.printf(stringFormat, "", node.data);

        ++i;

        if (node.left != null) {
            System.out.print("-");
            printNode(i, node.left);
        }

        if (node.right != null) {
            System.out.print("+");
            printNode(i, node.right);
        }
    }
}