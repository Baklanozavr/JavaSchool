package ru.academits.baklanov.tasks;

import ru.academits.baklanov.tasks.iterators.MyArrayListIterator;

import java.util.*;

public class MyArrayList<E> implements List<E> {
    private E[] items;
    private int length;

    public MyArrayList() {
        this(8);
    }

    public MyArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость должна быть больше ноля!");
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
        length = 0;
    }

    private void checkCapacity() {
        int capacityAddition = 8;

        if (length == items.length) {
            items = Arrays.copyOf(items, length + capacityAddition);
        }
    }

    public void ensureCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Значение не может быть отрицательным!");
        }

        if (capacity > items.length) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    public void trimToSize(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Значение не может быть отрицательным!");
        }

        items = Arrays.copyOf(items, capacity);
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyArrayListIterator<>(this);
    }

    @Override
    public E[] toArray() {
        return Arrays.copyOf(items, length);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не найден! (null)");
        }

        if (array.length < length) {
            throw new IllegalArgumentException("Недостаточный размер массива!");
        }

        array = (T[]) Arrays.copyOf(items, array.length);

        return array;
    }

    @Override
    public boolean add(E e) {
        checkCapacity();

        items[length] = e;
        ++length;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index != -1) {
            remove(index);
        }

        return index != -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("Коллекция не найдена! (null)");
        }

        Iterator<?> outerIterator = c.iterator();

        int counter = 0;

        while (outerIterator.hasNext()) {
            if (contains(outerIterator.next())) {
                ++counter;
            }
        }

        return counter == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new IllegalArgumentException("Коллекция не найдена! (null)");
        }

        ensureCapacity(length + c.size());

        Iterator<? extends E> outerIterator = c.iterator();

        int counter = 0;

        while (outerIterator.hasNext()) {
            items[length + counter] = outerIterator.next();
            ++counter;
        }

        length = length + c.size();

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null) {
            throw new IllegalArgumentException("Коллекция не найдена! (null)");
        }

        if (index < 0) {
            throw new IllegalArgumentException("Индекс не может быть отрицательным!");
        }

        if (index >= length) {
            throw new IllegalArgumentException("Индекс за границей списка!");
        }

        ensureCapacity(length + c.size());

        System.arraycopy(items, index, items, index + c.size(), length - index);

        Iterator<? extends E> outerIterator = c.iterator();

        int counter = 0;

        while (outerIterator.hasNext()) {
            items[index + counter] = outerIterator.next();
            ++counter;
        }

        length = length + c.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("Коллекция не найдена! (null)");
        }

        for (int i = 0; i < length; ) {
            if (c.contains(items[i])) {
                remove(i);
            }
            ++i;
        }

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("Коллекция не найдена! (null)");
        }

        for (int i = 0; i < length; ) {
            if (c.contains(items[i])) {
                ++i;
                continue;
            }
            remove(i);
        }

        return true;
    }

    @Override
    public void clear() {
        length = 0;
        Arrays.fill(items, null);
    }

    @Override
    public E get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс не может быть отрицательным!");
        }

        if (index >= length) {
            throw new IllegalArgumentException("Индекс за границей списка!");
        }

        return items[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс не может быть отрицательным!");
        }

        if (index >= length) {
            throw new IllegalArgumentException("Индекс за границей списка!");
        }

        if (element == null) {
            throw new IllegalArgumentException("Объект для установки не найден! (null)");
        }

        items[index] = element;

        return element;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс не может быть отрицательным!");
        }

        if (index >= length) {
            throw new IllegalArgumentException("Индекс за границей списка!");
        }

        if (element == null) {
            throw new IllegalArgumentException("Объект для вставки не найден! (null)");
        }

        checkCapacity();

        System.arraycopy(items, index, items, index + 1, length - index);
        items[index] = element;
        ++length;
    }

    @Override
    public E remove(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс не может быть отрицательным!");
        }

        if (index >= length) {
            throw new IllegalArgumentException("Индекс за границей списка!");
        }

        E element = get(index);

        System.arraycopy(items, index + 1, items, index, length - index);
        --length;

        return element;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Объект для поиска не найден! (null)");
        }

        for (int i = 0; i < length; ++i) {
            if (items[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Объект для поиска не найден! (null)");
        }

        for (int i = length - 1; i >= 0; --i) {
            if (items[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override //реализация не обязательна
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override //реализация не обязательна
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override //реализация не обязательна
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}