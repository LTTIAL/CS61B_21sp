package deque;

import org.junit.Test;

import java.util.Iterator;
import static org.junit.Assert.*;


public class ArrayDequeTest {

    @Test
    public void iteratorTest() {

        ArrayDeque<Integer> AD = new ArrayDeque<>();
        int size = 21;

        for (int i = 0; i < size; i++) {
            AD.addLast(i);
        }

        Iterator iterator = AD.iterator();
        for (int i = 0; i < size; i++) {
            assertEquals(iterator.next(), AD.get(i));
        }
    }

    @Test
    public void printTest() {

        ArrayDeque<Integer> AD = new ArrayDeque<>();
        int size = 22;

        for (int i = 0; i < size; i++) {
            AD.addLast(i);
        }

        AD.printDeque();
        System.out.print(AD.size());
    }

    @Test
    public void removeTest() {

        ArrayDeque<Integer> AD = new ArrayDeque<>();
        int size = 16;

        for (int i = 0; i < size; i++) {
            AD.addLast(i);
        }
        AD.printDeque();
        System.out.println(AD.size());

        int removeCount = 13;
        for (int i = 0; i < removeCount; i++) {
            AD.removeFirst();
        }

        AD.printDeque();
        System.out.println(AD.size());
    }

    @Test
    public void removeNullArrTest() {

        ArrayDeque<Integer> AD = new ArrayDeque<>();
        AD.removeFirst();
        AD.removeFirst();

        AD.addLast(1);

        AD.printDeque();

    }

}