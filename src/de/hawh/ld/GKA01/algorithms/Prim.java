package de.hawh.ld.GKA01.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.util.HashSet;
import java.util.Set;

public class Prim implements Algorithm {

    private Graph graph;
    private final Set<Edge> edgesInTree = new HashSet<>();


    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {
        FibonacciHeap<Integer, Edge> minEdgeHeap = new FibonacciHeap<>();

    }
}
