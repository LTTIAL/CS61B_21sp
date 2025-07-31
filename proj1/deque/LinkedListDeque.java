package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T> {

    private class LNode<T> {
        private T item;
        private LNode<T> previous;
        private LNode<T> next;

        public LNode() {
            this.item = null;
            this.previous = this;
            this.next = this;
        }
    }

    private int size;
    private LNode<T> sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new LNode();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T get(int index) {
        LNode<T> lNode;
        lNode = this.sentinel;
        lNode = lNode.next;
        while (index != 0) {
            index -= 1;
            lNode = lNode.next;
        }
        return lNode.item;
    }

    private LNode getLastNode() {
        return this.sentinel.previous;
    }

    private LNode getFirstNode() {
        return this.sentinel.next;
    }

    /** Adds an item of type T to the front of the deque. You can assume that item is never null.*/
    @Override
    public void addFirst(T item) {
        this.size += 1;

        LNode tmp = new LNode();
        LNode first = this.getFirstNode();

        tmp.item = item;
        tmp.next = first;
        tmp.previous = this.sentinel;

        first.previous = tmp;
        this.sentinel.next = tmp;

    }
    /** Adds an item of type T to the back of the deque. You can assume that item is never null.*/
    @Override
    public void addLast(T item) {
        this.size += 1;

        LNode tmp = new LNode();
        LNode last = this.getLastNode();

        tmp.item = item;
        tmp.next = this.sentinel;
        tmp.previous = last;

        last.next = tmp;
        this.sentinel.previous = tmp;
    }

    @Override
    public T removeFirst() {
        if (this.size > 0) {
            this.size -= 1;
        }
        LNode<T> removed = this.getFirstNode();
        T tmp = removed.item;

        this.sentinel.next = removed.next;
        removed.next.previous = this.sentinel;

        removed.item = null;

        return tmp;
    }

    @Override
    public T removeLast() {
        if (this.size > 0) {
            this.size -= 1;
        }
        LNode<T> removed = this.getLastNode();
        T tmp = removed.item;

        this.sentinel.previous = removed.previous;
        removed.previous.next = this.sentinel;

        removed.item = null;

        return tmp;
    }


    @Override
    public void printDeque() {
        LNode startNode = this.sentinel.next;
        while (startNode != this.sentinel) {
            System.out.print(startNode.item + " ");
            startNode = startNode.next;
        }
        System.out.println("");
    }

    public T getRecursive(int index) {
        return helperGetRecu(index, this.sentinel.next);
    }

    private T helperGetRecu(int index, LNode<T> lNode) {
        if (index == 0) {
            return lNode.item;
        } else {
            return helperGetRecu(index - 1, lNode.next);
        }
    }

    private class ListIterator<T> implements Iterator<T> {
        int index;
        LNode<T> lNode;

        public ListIterator() {
            this.index = 0;
            this.lNode = getFirstNode();
        }

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            this.index++;
            T tmp = lNode.item;
            lNode = lNode.next;
            return tmp;
        }
    }

    public Iterator iterator() {
        return new ListIterator();
    }

    public boolean equals(Object o) {
        if (o instanceof Deque) {
            return helperEquals((Deque<T>) o);
        } else {
            return false;
        }
    }

    public boolean helperEquals(Deque<T> deque) {
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
