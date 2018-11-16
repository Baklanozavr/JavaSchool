package ru.academits.baklanov.tasks;

import java.util.*;

public class MyArrayList<E> implements List<E> {
    private E[] items;
    private int length;
    private int modCounter;

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
        modCounter = 0;
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
        if (items.length != length) {
            items = Arrays.copyOf(items, length);
        }
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public void clear() {
        length = 0;
        ++modCounter;
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
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length - 1; i >= 0; --i) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, length);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не найден! (null)");
        }

        if (array.length < length) {
            //noinspection unchecked
            array = (T[]) Arrays.copyOf(items, length, array.getClass());
        } else {
            //noinspection SuspiciousSystemArraycopy
            System.arraycopy(items, 0, array, 0, length);

            if (array.length > length) {
                array[length] = null;
            }
        }

        return array;
    }

    @Override
    public boolean add(E e) {
        checkCapacity();

        items[length] = e;
        ++length;
        ++modCounter;

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
        ++modCounter;
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
        ++modCounter;

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
            throw new NullPointerException("Коллекция не найдена! (null)");
        }

        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException("Коллекция не найдена! (null)");
        }

        if (c.size() == 0) {
            return false;
        }

        ensureCapacity(length + c.size());

        int innerIndex = length;

        for (E element : c) {
            items[innerIndex] = element;
            ++innerIndex;
        }

        length += c.size();
        ++modCounter;

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException("Коллекция не найдена! (null)");
        }
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс за пределами допустимого диапазона!");
        }
        if (c.size() == 0) {
            return false;
        }

        ensureCapacity(length + c.size());

        System.arraycopy(items, index, items, index + c.size(), length - index);

        int innerIndex = index;

        for (E element : c) {
            items[innerIndex] = element;
            ++innerIndex;
        }

        length += c.size();
        ++modCounter;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Коллекция не найдена! (null)");
        }

        int initialModCount = modCounter;

        for (int i = 0; i < length; ) {
            if (c.contains(items[i])) {
                remove(i);
            } else {
                ++i;
            }
        }

        return initialModCount != modCounter;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Коллекция не найдена! (null)");
        }

        int initialModCount = modCounter;

        for (int i = 0; i < length; ) {
            if (c.contains(items[i])) {
                ++i;
                continue;
            }
            remove(i);
        }

        return initialModCount != modCounter;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public String toString() {
        if (length == 0) {
            return "This list is empty!";
        }

        StringBuilder bufferString = new StringBuilder();

        for (int i = 0; i < length; ++i) {
            bufferString.append(items[i].toString()).append(System.lineSeparator());
        }

        return bufferString.toString();
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

    private class MyArrayListIterator implements Iterator<E> {
        private int index;
        private int currentModCount;

        MyArrayListIterator() {
            index = -1;
            currentModCount = modCounter;
        }

        @Override
        public boolean hasNext() {
            return index < length - 1;
        }

        @Override
        public E next() {
            if (currentModCount != modCounter) {
                throw new ConcurrentModificationException("Произошло изменение коллекции!");
            }

            if (index == length - 1) {
                throw new NoSuchElementException("Следующего элемента нет!");
            }

            ++index;
            return items[index];
        }
    }
}