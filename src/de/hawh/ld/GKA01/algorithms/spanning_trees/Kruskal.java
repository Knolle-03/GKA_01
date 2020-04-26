package de.hawh.ld.GKA01.algorithms.spanning_trees;


import de.hawh.ld.GKA01.util.UnionFind;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.graph.Edge;

import java.util.*;


public class Kruskal extends AbstractSpanningTree {

    //PriorityQueue<Edge> graphEdges;

    List<Edge> edges;
    UnionFind unionFind;
    int treeWeight = 0;
    List<Edge> treeEdges;

    @Override
    protected void makeTree() {
        treeEdges = new ArrayList<>();
        unionFind = new UnionFind(graph.getNodeCount());
        edges = new ArrayList<>();
        edges.addAll(graph.getEdgeSet());
        edges.sort(Comparator.comparingInt(Kruskal::getEdgeWeight));

        for (Edge edge : edges) {                                                                                       // e
            if (!unionFind.connected(edge.getNode0().getIndex(), edge.getNode1().getIndex())) {
                unionFind.union(edge.getNode0().getIndex(), edge.getNode1().getIndex());
                treeWeight += getEdgeWeight(edge);
                treeEdges.add(edge);
                if (treeEdges.size() == graph.getNodeCount() - 1) {
                    break;
                }
            }
        }
    }

    @Override
    public <T extends Edge> Iterator<T> getTreeEdgesIterator() {
        return null;
    }

    @Override
    public void clear() {
        edges.clear();
        unionFind = null;
        treeWeight = 0;
        treeEdges.clear();

    }


    private static int getEdgeWeight(Edge edge) {
        if (!edge.hasAttribute("weight")) {
            return 1;
        }

        return edge.getAttribute("weight");
    }



    public int getTreeWeight() {
        return treeWeight;
    }



}
