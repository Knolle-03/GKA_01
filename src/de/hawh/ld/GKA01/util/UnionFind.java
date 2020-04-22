package de.hawh.ld.GKA01.util;

public class UnionFind {


    private final int[] parent;
    private final int[] treeSize;


    public UnionFind(int n) {
        parent = new int[n];
        treeSize = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            treeSize[i] = 1;
        }
    }

//TODO path compression
    public int root(int i) {
        //int initI = i;
        while (parent[i] != i) {
            i = parent[i];
        }
        //parent[initI] = i;
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);

        if (treeSize[pRoot] <= treeSize[qRoot]) {
            parent[pRoot] = qRoot;
            treeSize[qRoot] += treeSize[pRoot];
        } else {
            parent[qRoot] = pRoot;
            treeSize[pRoot] += treeSize[qRoot];
        }
    }

}
