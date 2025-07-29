package deque;

import java.util.Iterator;

public class LinkedListDeque<T> {

    private class LNode {
        public T item;
        public LNode previous;
        public LNode next;

        public LNode (){
            this.item = null;
            this.previous = this;
            this.next = this;
        }
    }

    int size;
    LNode sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new LNode();
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public T get(int index) {
        LNode lNode;
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

    public T removeFirst() {
        if (this.size > 0) {
            this.size -= 1;
        }
        LNode removed = this.getFirstNode();
        T tmp = removed.item;

        this.sentinel.next = removed.next;
        removed.next.previous = this.sentinel;

        removed.item = null;

        return tmp;
    }

    public T removeLast() {
        if (this.size > 0) {
            this.size -= 1;
        }
        LNode removed = this.getLastNode();
        T tmp = removed.item;

        this.sentinel.previous = removed.previous;
        removed.previous.next = this.sentinel;

        removed.item = null;

        return tmp;
    }


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

    private T helperGetRecu(int index, LNode lNode) {
        if (index == 0) {
            return lNode.item;
        } else {
            return helperGetRecu(index - 1, lNode.next);
        }
    }

    private class ListIterator implements Iterator<T> {
        int index;
        LNode lNode;

        public ListIterator() {
            this.index = 0;
            this.lNode = getFirstNode();
        }

        public boolean hasNext() {
            if (this.index < size) {
                return true;
            } else {
                return false;
            }
        }

        public T next() {
            this.index++;
            T tmp = lNode.item;
            lNode = lNode.next;
            return tmp;
        }
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }

    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            return equalsList((LinkedListDeque)o);
        } else {
            return false;
        }
    }

    private boolean equalsList(LinkedListDeque linkedListDeque) {
        Iterator LI1 = this.iterator();
        Iterator LI2 = linkedListDeque.iterator();
        if (this.size != linkedListDeque.size) {
            return false;
        }
        while (LI1.hasNext() && LI2.hasNext()) {
            if (!LI1.next().equals(LI2.next())) {
                return false;
            }
        }
        return true;
    }
}