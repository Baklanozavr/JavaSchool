package ru.academits.baklanov.tasks.iterators;

import ru.academits.baklanov.tasks.MyArrayList;

import java.util.Iterator;

public class MyArrayListIterator<T> implements Iterator<T> {
    private int index;
    private MyArrayList myArrayList;

    public MyArrayListIterator(MyArrayList<T> myArrayList) {
        if (myArrayList == null) {
            throw new IllegalArgumentException("Список не найден! (null)");
        }

        index = -1;
        this.myArrayList = myArrayList;
    }

    @Override
    public boolean hasNext() {
        return index < myArrayList.size() - 1;
    }

    @Override
    public T next() {
        ++index;

        if (index > myArrayList.size()) {
            throw new IndexOutOfBoundsException("Следующего элемента нет!");
        }

        return (T) myArrayList.get(index);
    }
}