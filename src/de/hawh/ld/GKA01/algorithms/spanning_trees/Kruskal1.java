package de.hawh.ld.GKA01.algorithms.spanning_trees;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.util.DisjointSets;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;


public class Kruskal1 extends AbstractSpanningTree {

    List<Edge> edges;
    DisjointSets<Node> forest;
    List<Edge> treeEdges;
    int treeWeight = 0;

    @Override
    protected void makeTree() {
        edges = new ArrayList<>();
        treeEdges = new LinkedList<>();
        edges.addAll(graph.getEdgeSet());                                                                               // e ( e = number of edges in the graph)
        edges.sort(Comparator.comparingInt(this::getEdgeWeight));                                                       // e lg(e)
        forest = new DisjointSets<>(graph.getNodeCount());


        for (Node node : graph) {
            forest.add(node);                                                                                           // n (n = number of nodes in the graph)
        }

        for (Edge edge : edges) {                                                                                       // e
            if (forest.union(edge.getNode0(), edge.getNode1())) {
                treeWeight += getEdgeWeight(edge);
                treeEdges.add(edge);
                if (treeEdges.size() == graph.getNodeCount() - 1) {
                    break;
                }
            }
        }
    }




    public void clear() {
        treeWeight = 0;
    }

    //TODO write Iterator
    @Override
    public <T extends Edge> Iterator<T> getTreeEdgesIterator() {
        return null;
    }


    private int getEdgeWeight(Edge edge) {
        if (!edge.hasAttribute("weight")) {
            return 1;
        }

        return edge.getAttribute("weight");
    }



    public int getTreeWeight() {
        return treeWeight;
    }



}
