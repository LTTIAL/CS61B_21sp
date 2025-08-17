package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Tau
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }

        public int hashCode() {
            return key.hashCode();
        }

        public boolean equals(Object obj) {
            return key.equals(obj);
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private final int defaultSize = 0;
    private final int defaultItems = 0;
    private final double defaultFactor = 1.5;
    private final int defaultBucketLength = 10;
    private final long UNSIGNED_MASK_64_BITS = 0x00000000FFFFFFFFL;
    private int size;
    private int items;
    private double resizeFactor;


    /** Constructors */
    public MyHashMap() {
        size = defaultSize;
        items = defaultItems;
        resizeFactor = defaultFactor;
        buckets = createTable(defaultBucketLength);
    }

    public MyHashMap(int initialSize) {
        size = defaultSize;
        items = defaultItems;
        resizeFactor = defaultFactor;
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = defaultSize;
        items = defaultItems;
        resizeFactor = maxLoad;
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] collection = new Collection[tableSize];
        for (int i = 0; i < collection.length; i++) {
            collection[i] = createBucket();
        }
        return collection;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            buckets[i] = null;
        }
        size = 0;
        items = 0;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V get(K key) {
        if (size == 0) {
            return null;
        }

        Node node = getNode(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    public int size() {
        return items;
    }

    private Node getNode(K key) {
        long hash = key.hashCode();
        hash = hash & UNSIGNED_MASK_64_BITS;
        long index = hash % buckets.length;
        Collection<Node> collection = buckets[(int) index];
        for (Node node: collection) {
            if (node.equals(key)) {
                return node;
            }
        }
        return null;
    }

    private void update(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
        }
    }

    public void put(K key, V value) {

        long hash = key.hashCode();
        hash = hash & UNSIGNED_MASK_64_BITS;
        long index = hash % buckets.length;
        Collection<Node> bucket = buckets[(int) index];
        if (bucket.size() == 0) {
            size++;
        }

        if (containsKey(key)) {
            update(key, value);
        } else {
            bucket.add(createNode(key, value));
            items++;
        }


        double factor = (double) items / buckets.length;
        if (factor >= resizeFactor) {
            int newSize = (int) Math.round(factor * resizeFactor);
            resize(newSize);
        }

    }

    private void resize(int newSize) {

        Collection<Node>[] newBuckets = createTable(newSize);
        size = 0;

        for (Collection<Node> collection: buckets) {
            for (Node node: collection) {
                long hash = node.hashCode();
                hash = hash & UNSIGNED_MASK_64_BITS;
                long index = hash % newBuckets.length;
                if (newBuckets[(int) index].size() == 0) {
                    size++;
                }
                newBuckets[(int) index].add(node);
            }
        }

        buckets = newBuckets;
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Collection<Node> collection: buckets) {
            for (Node node: collection) {
                set.add(node.key);
            }
        }
        return set;
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
