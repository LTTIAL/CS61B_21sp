package deque;

import java.util.Iterator;

public class ArrayDeque<T> {
    int size;
    int nextItem;
    int firstItem;
    T[] items;

    public ArrayDeque() {
        size = 0;
        firstItem = 0;
        nextItem = firstItem + 1;
        items = (T[]) new Object[8];
    }

    public int size() {
        return size;
    }

    private void reSize(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            newArr[i] = get(i);
        }
        firstItem = newArr.length - 1;
        nextItem = size;
        items = newArr;
    }

    public void addFirst(T item) {
        if (nextItem == firstItem) {
            reSize(items.length * 2);
        }
        items[firstItem] = item;
        firstItem = (firstItem + items.length - 1) % items.length;
        size++;
    }

    public void addLast(T item) {
        if (nextItem == firstItem) {
            reSize(items.length * 2);
        }
        items[nextItem] = item;
        nextItem = (nextItem + 1) % items.length;
        size++;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void printDeque() {
        int index = (firstItem + 1) % items.length;
        int count = size;
        for (int i = count; i > 0; i--) {
            System.out.print(items[index] + " ");
            index = (index + 1) % items.length;
        }
        System.out.println("");
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextItem = (nextItem - 1 + items.length) % items.length;
        T tmp = items[nextItem];
        items[nextItem] = null;
        size--;

        if (items.length >= 16 && size < (int) (Math.round(items.length / 4.0))) {
            reSize((int) (Math.round(items.length / 4.0)));
        }

        return tmp;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        firstItem = (firstItem + 1) % items.length;
        T tmp = items[firstItem];
        items[firstItem] = null;
        size--;

        if (items.length >= 16 && size < (int) (Math.round(items.length / 4.0))) {
            reSize((int) (Math.round(items.length / 4.0)));
        }

        return tmp;
    }

    public T get(int offset) {
        int trueIndex = (firstItem + 1) % items.length;
        trueIndex = (trueIndex + offset) % items.length;
        return items[trueIndex];
    }

    private class ADIterator implements Iterator<T>{
        int index;

        public ADIterator() {
            index = 0;
        }

        public boolean hasNext() {
            if (index < size) {
                return true;
            } else {
                return false;
            }
        }

        public T next() {
            T tmp = get(index);
            index++;
            return tmp;
        }
    }

    public Iterator<T> iterator() {
        return new ADIterator();
    }

    public boolean equals(Object o) {
        if (o instanceof ArrayDeque) {
            return ADequals((ArrayDeque) o);
        } else {
            return false;
        }
    }

    private boolean ADequals(ArrayDeque compare) {
        if (this.size != compare.size) {
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            if (!this.get(i).equals(compare.get(i))) {
                return false;
            }
        }
        return true;
    }
}