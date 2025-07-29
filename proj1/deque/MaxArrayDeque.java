package deque;

import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> comparator) {
        this();
        this.comparator = comparator;
    }

    public MaxArrayDeque() {
        super();
    }

    public T max() {
        Iterator<T> iterator = this.iterator();
        T max = this.get(0);
        while (iterator.hasNext()) {
            T comparedItem = iterator.next();
            int tmp = comparator.compare(max, comparedItem);
            if (tmp < 0) {
                max = comparedItem;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        Iterator<T> iterator = this.iterator();
        T max = this.get(0);
        while (iterator.hasNext()) {
            T comparedItem = iterator.next();
            int tmp = c.compare(max, comparedItem);
            if (tmp < 0) {
                max = comparedItem;
            }
        }
        return max;
    }

}