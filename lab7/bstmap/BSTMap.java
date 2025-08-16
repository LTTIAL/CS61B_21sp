package bstmap;

import java.util.*;

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

    private V getHelper(K key, BSTNode bstNode) {
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
        System.out.println(getIterationString(root));
    }

    private String getIterationString(BSTNode node) {
        if (node == null) {
            return " ";
        } else {
            return getIterationString(node.left) + node.val.toString() + getIterationString(node.right);
        }

    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public V remove(K key) {
        V val = get(key);
        root = remove(key, root);
        return val;
    }



    private BSTNode remove(K key, BSTNode node) {
        if (node == null) {
            return null;
        }

        int compare = node.key.compareTo(key);
        if (compare > 0) {
            node.size--;
            node.left = remove(key, node.left);
        } else if (compare < 0) {
            node.size--;
            node.right = remove(key, node.right);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                BSTNode inorderSuccessor = findInorderSuccessor(node.right);
                node.key = inorderSuccessor.key;
                node.val = inorderSuccessor.val;
                node.right = remove(key, node.right);
            }
        }
        return node;
    }

    private BSTNode findInorderSuccessor(BSTNode rightNode) {
        while (rightNode.left != null) {
            rightNode = rightNode.left;
        }
        return rightNode;
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private class MapIterator implements Iterator<K> {
        Stack<K> stack;

        public MapIterator() {
            BSTNode node = root;

            stack = new Stack<>();

            while (node != null) {
                stack.push(node.key);
                node = node.left;
            }
        }

        public boolean hasNext() {
            return !stack.empty();
        }

        public BSTNode getNode(K key) {
            if (root == null) {
                return null;
            }
            return null;
        }

        public K next() {
            return null;
        }
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }


}
