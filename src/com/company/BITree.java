package com.company;

/**
 *  "Fenwick" or "Binary Indexed" Tree
 *  Useful for prefix sum, where insertion would require recalculating existing sums
 *  log(n) for both update, and sum calculation
 */
public class BITree {
    int[] tree;
    int[] a;

    public BITree(int size, int[] a) {
        this.a = a;
        tree = new int[size];
    }

    public void init() {
        for(int i = 0; i < a.length; i++) {
            add(i, a[i]);
        }
    }

    public int lsb(int index) {
        // remove last set bit to get index of parent
        return index & (-index);
    }

    // Adds k to element with index i
    public void add(int i, int k) {
        while (i < tree.length) {
            tree[i] += k;
            i += lsb(i);
        }
    }

    public int getSum(int i) {
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= lsb(i);
        }
        return sum;
    }
}
