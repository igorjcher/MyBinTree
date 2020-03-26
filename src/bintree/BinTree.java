package bintree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class BinTree<K extends Comparable<K>, V> {

    private int size = 0;
    private Node<K, V> root = null;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V get(K key) {
        if (root == null) {
            return null;
        }

        Node<K, V> current = root;
        while (true) {
            if (key.compareTo(current.key) == 0) {
                return current.value;
            } else if (key.compareTo(current.key) < 0) {
                if (current.left == null) {
                    return null;
                } else {
                    current = current.left;
                }
            } else { // if (key.compareTo(current.key) > 0)
                if (current.right == null) {
                    return null;
                } else {
                    current = current.right;
                }
            }
        }
    }

    public void put(K key, V value) {
        if (root == null) {
            root = new Node<K, V>(key, value);
            size++;
            return;
        }

        Node<K, V> current = root;
        while (true) {
            if (key.compareTo(current.key) == 0) {
                current.value = value;
                break;
            } else if (key.compareTo(current.key) < 0) {
                if (current.left == null) {
                    current.left = new Node<K, V>(key, value, current);
                    size++;
                    break;
                } else {
                    current = current.left;
                }
            } else { // if (key.compareTo(key) > 0)
                if (current.right == null) {
                    current.right = new Node<K, V>(key, value, current);
                    size++;
                    break;
                } else {
                    current = current.right;
                }
            }
        }
    }

    public V remove(K key) {
        if (root == null) {
            return null;
        }

        Node<K, V> current = root;
        while (true) {
            if (key.compareTo(current.key) == 0) {
                V value = current.value;
                remove(current);
                size--;
                return current.value;
            } else if (key.compareTo(current.key) < 0) {
                if(current.left == null) {
                    return null;
                } else {
                    current = current.left;
                }
            } else { // if (ey.compareTo(current.key) > 0)
                if(current.right == null) {
                    return null;
                } else {
                    current = current.right;
                }    
            }
        }
    }

    private void remove(Node<K, V> current) {
        if (current.isRoot() && current.isLeaf()) {
            root = null;
            return;
        } else if (current.isRoot() && current.hasOnlyLeftChild()) {
            root = current.left;
            root.parent = null;
        } else if (current.isRoot() && current.hasOnlyRightChild()) {
            root = current.right;
            root.parent = null;
        } else if (current.isRoot() && current.hasBothChildren()) {
             if (current.left.isRightChildEmpty()) {
                 current.left.right = current.right;
                 current.right.parent = current.left;
                 root = current.left;
                 root.parent = null;
             } else if (current.right.isLeftChildEmpty()) {
                 current.right.left = current.left;
                 current.left.parent = current.right;
                 root = current.right;
                 root.parent = null;
             } else {
                 Node<K, V> temp = current.right.left;
                 current.right.left = current.left;
                 current.left = current.right;
                 root = current.right;
                 root.parent = null;
                 insert(temp);
             }
                 
            
        } 
    }
    
    public void insert(Node<K, V> temp) {
        Node<K, V> current = root;
        while(true) {
            if (temp.key.compareTo(current.key) == 0) {
                throw new IllegalStateException("Duplicate key in tree: " + temp.key);
            } else if (temp.key.compareTo(current.key) < 0) {
                if (current.isLeftChildEmpty()) {
                    current.left = temp;
                    temp.parent = current;
                } else {
                    current = current.left;
                }
            } else { // if (temp.key.compareTo(current.key) > 0)
                if (current.isRightChildEmpty()) {
                    current.right = temp;
                    temp.parent = current;
                } else {
                    current = current.right;
                }
            }
        }
    }

    public void depthFirstSearch(Visitor<K, V> visitor) {
        if (root == null) {
            return;
        }

        Deque<Node<K, V>> stack = new ArrayDeque<>();
        stack.addFirst(root);
        Node<K, V> current = root.left;
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                if (current.left != null) {
                    stack.addFirst(current);
                    current = current.left;
                } else {
                    visitor.visit(current.key, current.value);
                    current = current.right;
                }
            } else {
                Node<K, V> temp = stack.removeFirst();
                visitor.visit(temp.key, temp.value);
                current = temp.right;
            }
        }
    }

    public void widthFisrtSearch(Visitor<K, V> visitor) {
        if (root == null) {
            return;
        }

        Queue<Node<K, V>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<K, V> temp = queue.remove();
            visitor.visit(temp.key, temp.value);
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
        }
    }

    public static class Node<K, V> {
        K key;
        V value;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;

        public Node() {}

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
        
        public boolean isRoot() {
            return parent == null;
        }
        
        public boolean isLeaf() {
            return left == null && right == null;
        }
        
        public boolean hasOnlyLeftChild() {
            return left != null && right == null;
        }
        
        public boolean hasOnlyRightChild() {
            return left == null && right != null;
        }
        
        public boolean isLeftChild() {
            return this == parent.left;
        }
        
        public boolean isRightChild() {
            return this == parent.right;
        }
        
        public boolean hasBothChildren() {
            return left != null && right != null;
        }
        
        public boolean isLeftChildEmpty() {
            return left == null;
        }
        
        public boolean isRightChildEmpty() {
            return right == null;
        }
    }

    public static interface Visitor<K, V> {

        void visit(K key, V value);
    }
}
