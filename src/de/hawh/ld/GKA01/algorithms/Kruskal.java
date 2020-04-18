package de.hawh.ld.GKA01.algorithms;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.util.DisjointSets;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;


public class Kruskal extends AbstractSpanningTree {

    private static final String DEFAULT_ATTR = "weight";

    private final String attr;


    List<Edge> edges = new ArrayList<>();
    DisjointSets<Node> forest;
    Graph graph;
    int treeWeight = 0;
    List<Edge> treeEdges = new LinkedList<>();


    public Kruskal() {
        this(DEFAULT_ATTR, null);
    }

    public Kruskal(String attr, String flag) {
        this(attr, flag, true, false);
    }

    public Kruskal(String attr, String flag, Object used, Object unused) {
        super(flag, used, unused);
        this.attr = attr;
    }


    public void init(Graph graph) {
        this.graph = graph;
        edges.addAll(graph.getEdgeSet());
        edges.sort(Comparator.comparingInt(this::getEdgeWeight));
        forest = new DisjointSets<>(graph.getNodeCount());


        for (Node node : graph) {
            forest.add(node);
        }

    }

    @Override
    protected void makeTree() {

    }


    public void compute() {
        for (Edge edge : edges) {
            if (forest.union(edge.getNode0(), edge.getNode1())) {
                treeWeight += getEdgeWeight(edge);
                treeEdges.add(edge);
                if (treeEdges.size() == graph.getNodeCount() - 1) {
                    break;
                }
            }
        }
    }

    //TODO write Iterator
    @Override
    public <T extends Edge> Iterator<T> getTreeEdgesIterator() {
        return null;
    }


    private int getEdgeWeight(Edge edge) {
        if (!edge.hasAttribute(attr)) {
            return 1;
        }

        return edge.getAttribute("weight");
    }



    public int getTreeWeight() {
        return treeWeight;
    }



}
