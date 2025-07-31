
package deque;

import java.util.Iterator;


public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private int nextItem;
    private int firstItem;
    private T[] items;
    private final int shrinkThreshold = 16;


    public ArrayDeque() {
        size = 0;
        firstItem = 0;
        nextItem = firstItem + 1;
        items = (T[]) new Object[8];
    }

    @Override
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

    @Override
    public void addFirst(T item) {
        if (nextItem == firstItem) {
            reSize(items.length * 2);
        }
        items[firstItem] = item;
        firstItem = (firstItem + items.length - 1) % items.length;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (nextItem == firstItem) {
            reSize(items.length * 2);
        }
        items[nextItem] = item;
        nextItem = (nextItem + 1) % items.length;
        size++;
    }

    @Override
    public void printDeque() {
        int index = (firstItem + 1) % items.length;
        int count = size;
        for (int i = count; i > 0; i--) {
            System.out.print(items[index] + " ");
            index = (index + 1) % items.length;
        }
        System.out.println();
    }

    @Override
    public T removeLast() {

        if (size == 0) {
            return null;
        }
        nextItem = (nextItem - 1 + items.length) % items.length;
        T tmp = items[nextItem];
        items[nextItem] = null;
        size--;

        if (items.length >= shrinkThreshold && size < (int) (Math.round(items.length / 4.0))) {
            reSize((int) (Math.round(items.length / 4.0)));
        }

        return tmp;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        firstItem = (firstItem + 1) % items.length;
        T tmp = items[firstItem];
        items[firstItem] = null;
        size--;

        if (items.length >= shrinkThreshold && size < (int) (Math.round(items.length / 4.0))) {
            reSize((int) (Math.round(items.length / 4.0)));
        }

        return tmp;
    }

    @Override
    public T get(int offset) {
        int trueIndex = (firstItem + 1) % items.length;
        trueIndex = (trueIndex + offset) % items.length;
        return items[trueIndex];
    }

    private class ADIterator implements Iterator<T> {
        int index;

        public ADIterator() {
            index = 0;
        }

        public boolean hasNext() {
            return index < size;
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
        if (o instanceof Deque) {
            return helperEquals((Deque) o);
        } else {
            return false;
        }
    }

    private boolean helperEquals(Deque deque) {
        int dequeSize = deque.size();
        int thisSize = size();
        if (dequeSize != thisSize) {
            return false;
        } else {
            for (int i = 0; i < thisSize; i++) {
                if (!deque.get(i).equals(this.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}
