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

        if (node.left == null && node.right == null) {
            if (parent == null) {
                root = null;
            } else {
                parent.setNext(null, direction);
            }
        } else if (node.left == null || node.right == null) {
            TreeNode<E> child = node.left == null ? node.right : node.left;;

            if (parent == null) {
                root = child;
            } else {
                parent.setNext(child, direction);
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
                parent.setNext(minChild, direction);
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
        for (int j = 0; j < i; j++) {
            s.append(" ");
        }

        System.out.printf("%s%s%n", s.toString(), node.data);
        ++i;

        if (node.left != null) {
            System.out.print("-1");
            printNode(i, node.left);
        }

        if (node.right != null) {
            System.out.print("+1");
            printNode(i, node.right);
        }
    }
}