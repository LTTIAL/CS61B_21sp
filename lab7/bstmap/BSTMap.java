package bstmap;

import org.antlr.v4.runtime.tree.Tree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private BSTNode root;

    private class BSTNode {
        private K key;
        private V val;
        private int size;
        private BSTNode left, right;

        public BSTNode(K key, V value, int size) {
            this.key = key;
            this.val = value;
            this.size = size;
            left = null;
            right = null;
        }
    }


    public void clear() {
        root = null;
    }

    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        return get(key) != null;
    }
    
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode node, K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            return get(node.right, key);
        }else if (cmp < 0) {
            return get(node.left, key);
        }
        else return node.val;
    }

    public int size() {
        return size(root);
    }

    private int size(BSTNode node) {
        if (node == null) {
            return 0;
        }else return node.size;
    }

    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, val);
    }

    private BSTNode put(BSTNode x, K key, V val) {
        if (x == null) return new BSTNode(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void printInOrder() {

    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}