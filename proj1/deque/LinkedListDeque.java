package deque;

import java.util.LinkedList;

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
        if (this.size != 0) {
            return false;
        } else {
            return true;
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
        return this.sentinel.next;
    }

    private LNode getFirstNode() {
        return this.sentinel.previous;
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
        LNode removed = this.getFirstNode();
        T tmp = removed.item;

        this.sentinel.next = removed.next;
        removed.next.previous = this.sentinel;

        removed.item = null;

        return tmp;
    }


}