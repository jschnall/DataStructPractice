package com.company;

public class Main {

    public static void main(String[] args) {
        //testTrie();
        testLRUCache();
    }

    public static void testLRUCache() {
        //LRUCacheLyft<String, Integer> cache = new LRUCacheLyft<>(2);
        LRUCache<String, Integer> cache = new LRUCache<>(2);
        cache.put("a", 1);
        cache.put("b", 2);
        System.out.println(cache.get("a"));
        System.out.println(cache.get("b"));
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        System.out.println(cache.get("a"));
    }

    public static void testTrie() {
        PrefixTree.UpdateListener listener = new PrefixTree.UpdateListener() {
            @Override
            public void onUpdate(PrefixTree.Node node) {
                System.out.println("Added " + node.path);
            }
        };

        PrefixTree.UpdateListener listener2 = new PrefixTree.UpdateListener() {
            @Override
            public void onUpdate(PrefixTree.Node node) {
                System.out.println("Added2 " + node.path);
            }
        };

        PrefixTree trie = new PrefixTree();
        trie.addNode("/test", 0);
        trie.addListener(listener,"/test");
        trie.addNode("/foo", 1);
        trie.addNode("/test/foo", 2);
        trie.addNode("/test/bar", 3);
        trie.addNode("/test/foo/bar", 4);
        trie.addNode("/bar", 5);
        trie.addListener(listener,"/bar");
        trie.addNode("/bar/foo", 6);
        trie.addListener(listener2,"/bar/foo");
        //trie.removeListener(listener, "/");
        //trie.removeListener(listener);
        trie.addNode("/bar/foo/blah", 6);
    }
}
