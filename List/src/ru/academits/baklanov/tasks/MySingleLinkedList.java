package ru.academits.baklanov.tasks;

public class MySingleLinkedList<E> {
    private MyListItem<E> head;
    private int size;

    public int size() {
        return size;
    }

    public E getFirst() {
        return head.getData();
    }

    private MyListItem<E> getItem(int index) {
        MyListItem<E> tempItem = new MyListItem<>();

        for (int i = 0; i < index; ++i) {
            tempItem = head.getNext();
        }

        return tempItem;
    }

    public E get(int index) {
        return getItem(index).getData();
    }

    public E set(int index, E element) {
        MyListItem<E> tempItem = getItem(index).getCopy();

        getItem(index).setData(element);

        return tempItem.getData();
    }

    public E remove(int index) {
        MyListItem<E> tempItem = getItem(index).getCopy();

        getItem(index - 1).setNext(getItem(index + 1));

        return tempItem.getData();
    }

    public void addFirst(E element) {
        head = new MyListItem<> (element, head);
    }

    public void add(int index, E element) {
        MyListItem<E> tempItem = new MyListItem<> (element, head);
        getItem(index).setNext(getItem(index));
        getItem(index).setData(element);
    }

    public boolean remove(Object o) {
        return false;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public boolean contains(Object o) {
        return false;
    }

    class MyListItem<T> {
        private T data;
        private MyListItem<T> next;

        MyListItem() {
            data = null;
            next = null;
        }

        MyListItem(T data) {
            this.data = data;
            next = null;
        }

        MyListItem(T data, MyListItem<T> next) {
            this.data = data;
            this.next = next;
        }

        T getData() {
            return data;
        }

        void setData(T data) {
            this.data = data;
        }

        MyListItem<T> getNext() {
            return next;
        }

        void setNext(MyListItem<T> next) {
            this.next = next;
        }

        MyListItem<T> getCopy() {
            return new MyListItem<T>(data, next);
        }
    }
}