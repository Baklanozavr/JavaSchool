package ru.academits.baklanov.tasks;

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
        int capacityMultiplier = 2;

        if (length == items.length) {
            items = Arrays.copyOf(items, length * capacityMultiplier);
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

    public void trimToSize() {
        items = Arrays.copyOf(items, length);
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public void clear() {
        length = 0;
        Arrays.fill(items, null);
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
    public int indexOf(Object o) {
        for (int i = 0; i < length; ++i) {
            if (items[i] == null ? o == null : items[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length - 1; i >= 0; --i) {
            if (items[i] == null ? o == null : items[i].equals(o)) {
                return i;
            }
        }

        return -1;
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
    public void add(int index, E element) {
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс за пределами допустимого диапазона!");
        }

        checkCapacity();

        System.arraycopy(items, index, items, index + 1, length - index);
        items[index] = element;
        ++length;
    }

    @Override
    public E get(int index) {
        if (index >= length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс за пределами допустимого диапазона!");
        }

        return items[index];
    }

    @Override
    public E set(int index, E element) {
        if (index >= length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс за пределами допустимого диапазона!");
        }

        E previousElement = items[index];

        items[index] = element;

        return previousElement;
    }

    @Override
    public E remove(int index) {
        if (index >= length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс за пределами допустимого диапазона!");
        }

        E element = get(index);

        System.arraycopy(items, index + 1, items, index, length - 1 - index);
        --length;

        return element;
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

        if (index >= length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс за пределами допустимого диапазона!");
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
            } else {
                ++i;
            }
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
    public Iterator<E> iterator() {
        return new MyArrayListIterator<>(this);
    }

    /**
     * @noinspection ConstantConditions
     */
    @Override //реализация не обязательна
    public ListIterator<E> listIterator() {
        return null;
    }

    /**
     * @noinspection ConstantConditions
     */
    @Override //реализация не обязательна
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    /**
     * @noinspection ConstantConditions
     */
    @Override //реализация не обязательна
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    class MyArrayListIterator<T> implements Iterator<T> {
        private int index;
        private MyArrayList<T> myArrayListCopy;

        MyArrayListIterator(MyArrayList<T> myArrayList) {
            if (myArrayList == null) {
                throw new IllegalArgumentException("Список не найден! (null)");
            }

            index = -1;
            this.myArrayListCopy = myArrayList;
        }

        @Override
        public boolean hasNext() {
            return index < myArrayListCopy.size() - 1;
        }

        @Override
        public T next() {
            ++index;

            if (index > myArrayListCopy.size()) {
                throw new IndexOutOfBoundsException("Следующего элемента нет!");
            }

            return myArrayListCopy.get(index);
        }
    }
}