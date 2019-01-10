package com.company;

import java.util.*;

// Lyft interview: Asked for LRU cache based on ArrayList
public class LRUCacheLyft<K, V> {
    public static class Pair<K, V> {
        K key;
        V value;

        public Pair(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Map<K, Pair<K, V>> mMap = new HashMap<>();
    private List<Pair<K, V>> mList = new ArrayList<>();
    private int mCapacity;

    public LRUCacheLyft(int capacity) {
        mCapacity = capacity;
    }

    public void put(K key, V value) {
        if (mList.size() >= mCapacity) {
            Pair<K, V> oldItem = mList.remove(mList.size() - 1);
            mMap.remove(oldItem.key);
        }
        Pair<K, V> newItem = new Pair(key, value);
        mList.add(0, newItem);
        mMap.put(key, newItem);
    }

    public V get(K key) {
        Pair<K, V> item = mMap.get(key);
        if (item == null) {
            return null;
        }
        mList.remove(item); // <-- this is inefficient, better to keep our own linked list of nodes with prev/next ptrs
        mList.add(0, item);
        return item.value;
    }
}