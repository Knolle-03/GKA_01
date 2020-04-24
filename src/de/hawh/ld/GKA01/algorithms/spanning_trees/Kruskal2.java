package de.hawh.ld.GKA01.algorithms.spanning_trees;


import de.hawh.ld.GKA01.util.UnionFind;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.graph.Edge;

import java.util.*;


public class Kruskal2 extends AbstractSpanningTree {

    List<Edge> graphEdges;
    UnionFind unionFind;
    int treeWeight = 0;
    List<Edge> treeEdges;

    @Override
    protected void makeTree() {
        treeEdges = new LinkedList<>();
        unionFind = new UnionFind(graph.getNodeCount());
        graphEdges = new ArrayList<>();
        graphEdges.addAll(graph.getEdgeSet());
        graphEdges.sort(Comparator.comparingInt(Kruskal2::getEdgeWeight));

        for (Edge edge : graphEdges) {
            int node0Index = edge.getNode0().getIndex();
            int node1Index = edge.getNode1().getIndex();
            if (!unionFind.connected(node0Index, node1Index)) {
                unionFind.union(node0Index, node1Index);
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
        graphEdges.clear();
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
