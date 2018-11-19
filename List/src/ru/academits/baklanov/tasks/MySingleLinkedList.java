package ru.academits.baklanov.tasks;

import java.util.NoSuchElementException;
import java.util.Objects;

public class MySingleLinkedList<E> {
    private MyListItem<E> head;
    private int size;

    public MySingleLinkedList() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public E getFirst() {
        throwIfEmpty();

        return head.getData();
    }

    public E get(int index) {
        throwIfEmpty();
        checkIndex(index);

        return getItem(index).getData();
    }

    public E set(int index, E element) {
        throwIfEmpty();
        checkIndex(index);

        MyListItem<E> currentItem = getItem(index);
        E tempItem = currentItem.getData();
        currentItem.setData(element);

        return tempItem;
    }

    public E remove(int index) {
        throwIfEmpty();
        checkIndex(index);

        MyListItem<E> tempItem;

        if (index == 0) {
            tempItem = head;
            head = head.getNext();
        } else {
            MyListItem<E> prevItem = getItem(index - 1);

            tempItem = prevItem.getNext();
            prevItem.setNext(tempItem.getNext());
        }

        --size;
        return tempItem.getData();
    }

    public void addFirst(E element) {
        head = new MyListItem<>(element, head);
        ++size;
    }

    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Индекс за пределами допустимого диапазона!");
        }

        if (index == 0) {
            head = new MyListItem<>(element, head);
        } else {
            MyListItem<E> prevItem = getItem(index - 1);
            prevItem.setNext(new MyListItem<>(element, prevItem.getNext()));
        }
        ++size;
    }

    public boolean remove(Object o) {
        MyListItem<E> tempItem = head;
        MyListItem<E> prevItem = null;

        for (int i = 0; i < size; ++i) {
            if (Objects.equals(tempItem.getData(), o)) {
                if (i == 0) {
                    head = head.getNext();
                } else {
                    prevItem.setNext(tempItem.getNext());
                }
                --size;
                return true;
            }

            prevItem = tempItem;
            tempItem = tempItem.getNext();
        }

        return false;
    }

    public E removeFirst() {
        throwIfEmpty();

        MyListItem<E> tempItem = head;

        head = head.getNext();
        --size;

        return tempItem.getData();
    }

    public void reverse() {
        MyListItem<E> temp = head;
        head = null;

        while (temp != null) {
            MyListItem<E> next = temp.getNext();
            temp.setNext(head);
            head = temp;
            temp = next;
        }
    }

    public MySingleLinkedList<E> copy() {
        MySingleLinkedList<E> blankList = new MySingleLinkedList<>();

        if (head != null) {
            MyListItem<E> temp = new MyListItem<>(head.getData(), null);
            blankList.head = temp;

            for (MyListItem<E> runner = head.getNext(); runner != null; runner = runner.getNext()) {
                temp.setNext(new MyListItem<>(runner.getData(), null));
                temp = temp.getNext();
            }

            blankList.size = size;
        }

        return blankList;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder bufferString = new StringBuilder();
        bufferString.append('[');

        for (MyListItem<E> p = head; p != null; p = p.getNext()) {
            if (p.getData() == null) {
                bufferString.append("null, ");
            } else {
                bufferString.append(p.getData().toString()).append(", ");
            }
        }

        bufferString.replace(bufferString.length() - 2, bufferString.length(), "]");

        return bufferString.toString();
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Индекс за пределами допустимого диапазона!");
        }
    }

    private void throwIfEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("This list is empty!");
        }
    }

    private MyListItem<E> getItem(int index) {
        MyListItem<E> tempItem = head;

        for (int i = 0; i < index; ++i) {
            tempItem = tempItem.getNext();
        }

        return tempItem;
    }

    class MyListItem<T> {
        private T data;
        private MyListItem<T> next;

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
    }
}