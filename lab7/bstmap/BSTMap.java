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
        return containKeyHelper(key, root);
    }

    private boolean containKeyHelper(K key, BSTNode bstNode) {
       if (bstNode == null) {
           return false;
       }

       if (bstNode.key.equals(key)) {
           return true;
       }

       return containKeyHelper(key, bstNode.left) || containKeyHelper(key, bstNode.right);
    }

    public V get(K key) {
        return getHelper(key, root);
    }

    public V getHelper(K key, BSTNode bstNode) {
        if (bstNode == null) {
            return null;
        }

        if (bstNode.key.compareTo(key) > 0) {
            return getHelper(key, bstNode.left);
        } else if (bstNode.key.compareTo(key) < 0) {
            return getHelper(key, bstNode.right);
        } else {
            return bstNode.val;
        }
    }

    public int size() {
        if (root != null) {
            return root.size;
        }
        return 0;
    }

    public void put(K key, V value) {
        if (root == null) {
            root = new BSTNode(key, value, 1);
            return;
        }

        putHelper(key, value, root);
    }

    private void putHelper(K key, V value, BSTNode bstNode) {

        if (bstNode.key.compareTo(key) > 0) {
            bstNode.size++;
            if (bstNode.left != null) {
                putHelper(key, value, bstNode.left);
            } else {
                bstNode.left = new BSTNode(key, value, 1);
            }
        } else if (bstNode.key.compareTo(key) == 0) {
            bstNode.val = value;
        } else {
            bstNode.size++;
            if (bstNode.right != null) {
                putHelper(key, value, bstNode.right);
            } else {
                bstNode.right = new BSTNode(key, value, 1);
            }

        }
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

    private class MapIterator implements Iterator<K> {
        int size;

        public MapIterator() {
            size = size();
        }

        public boolean hasNext() {
            return size != 0;
        }

        public K next() {
            return null;
        }
    }

    public Iterator<K> iterator() {
        return new MapIterator();
    }


}