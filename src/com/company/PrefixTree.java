package com.company;

import java.util.*;


public class PrefixTree {
    public static class Node<T> implements Comparable<Node> {
        String id;
        String path;
        T value;
        Node parent;
        Map<String, Node> children = new HashMap<>();
        Set<UpdateListener> listeners = new HashSet<>();

        public Node(String id, String path, T value) {
            this.id = id;
            this.path = path;
            this.value = value;
        }

        @Override
        public int compareTo(Node node) {
            return id.compareTo(node.id);
        }

        public Node getChild(String id) {
            return children.get(id);
        }

        public void addChild(Node node) {
            children.put(node.id, node);
        }
    }

    public static interface UpdateListener {
        void onUpdate(Node node);
    }
    HashMap<UpdateListener, Set<String>> listeners = new HashMap<>(); // UpdateListener to path

    Node root = new Node("", "", null);


    public <T> void addNode(String path, T value) throws IndexOutOfBoundsException {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        int slashIndex = path.lastIndexOf("/");
        String parentPath = path.substring(0, slashIndex);
        List<UpdateListener> listeners = new LinkedList<>();
        Node parent = findNode(parentPath, listeners);
        Node node = new Node(path.substring(slashIndex + 1), path, value);
        node.parent = parent;
        parent.addChild(node);
        for (UpdateListener listener : listeners) {
            listener.onUpdate(node);
        }
    }

    public <T> Node<T> findNode(String path) {
        return findNode(path, null);
    }

    private <T> Node<T> findNode(String path, List<UpdateListener> listenerList) {
        String[] segments = path.split("/");
        Node node = root;
        try {
            if (listenerList != null) {
                listenerList.addAll(node.listeners);
            }
            for (int i = 0; i < segments.length - 1; i++) {
                node = node.getChild(segments[i]);
            }
        } catch (NullPointerException npe) {
            System.err.println("Path does not exist: " + path);
            return null;
        }
        return node;
    }

    public <T> Node updateNode(String path, T value) throws IndexOutOfBoundsException {
        List<UpdateListener> listenerList = new LinkedList<>();
        Node node = findNode(path, listenerList);
        node.value = value;
        for (UpdateListener listener : listenerList) {
            listener.onUpdate(node);
        }
        return node;
    }

    public void addListener(UpdateListener listener, String path) {
        Node node = findNode(path);
        node.listeners.add(listener);
        Set<String> set = listeners.get(listener);
        if (set == null) {
            set = new HashSet<>();
            listeners.put(listener, set);
        }
        set.add(path);
    }

    // Unsubscribe this listener from all events
    public void removeListener(UpdateListener listener) {
        Set<String> set = listeners.get(listener);
        for (String nodeId : set) {
            Node node = findNode(nodeId);
            node.listeners.remove(listener);
        }
        listeners.remove(listener);
    }

    // Unsubscribe this listener from events on this particular path
    public void removeListener(UpdateListener listener, String path) {
        Node node = findNode(path);
        if (node.listeners.remove(listener)) {
            listeners.get(listener).remove(path);
        }
    }
}
