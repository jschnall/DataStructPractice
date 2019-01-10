package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRUCache<K, V> {
    public static class Node<K, V> {
        K key;
        V value;

        Node prev;
        Node next;

        public Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Map<K, Node<K, V>> mMap = new HashMap<>();
    private Node mHead;
    private Node mTail;
    private int mCapacity = 100;

    public LRUCache() {

    }

    public LRUCache(int capacity) {
        mCapacity = capacity;
    }

    private void remove(Node node) {
        if (node == null) {
            // nothing to remove;
            return;
        }
        Node prev = node.prev;
        if (prev != null) {
            prev.next = null;
        }
        mMap.remove(node.key);
        mTail = prev;
    }

    public void add(Node node) {
        node.next = mHead;
        mHead = node;
        if (mMap.size() <= 1) {
            mTail = node;
        }
    }

    public void put(K key, V value) {
        Node node = mMap.get(key);
        if (node == null) {
            node = new Node(key, value);
            mMap.put(key, node);
            if (mMap.size() > mCapacity) {
                remove(mTail);
            }
        }
        add(node);
    }

    public V get(K key) {
        Node<K, V> node = mMap.get(key);
        if (node == null) {
            return null;
        }
        remove(node);
        add(node);

        return node.value;
    }
}