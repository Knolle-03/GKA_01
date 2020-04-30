package de.hawh.ld.GKA01.algorithms.spanning_trees;


import de.hawh.ld.GKA01.util.UnionFind;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.graph.Edge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;



public class OwnKruskal extends AbstractSpanningTree {
    private final List<Edge> spanningTree = new ArrayList<>();
    private final List<Edge> graphEdges = new ArrayList<>();                                                            // stores all edges of the graph
    private UnionFind unionFind;                                                                                        // keeps track of components
    private int treeWeight = 0;

    @Override
    protected void makeTree() {
        unionFind = new UnionFind(graph.getNodeCount());                                                                // init UnionFind object with each node's ID representing a single node component
        graphEdges.addAll(graph.getEdgeSet());                                                                          // add all edges to the list
        graphEdges.sort(Comparator.comparingInt(this::getEdgeWeight));                                                  // sort the edges in ascending order by weight
        for (Edge edge : graphEdges) {                                                                                  // look at each edge
            int indexOfNode0 = edge.getNode0().getIndex();
            int indexOfNode1 = edge.getNode1().getIndex();
            if (!unionFind.connected(indexOfNode0,indexOfNode1)) {                                                      // if the incident nodes are not connected
                unionFind.union(indexOfNode0, indexOfNode1);                                                            // unite the two components to one via the current edge
                treeWeight += getEdgeWeight(edge);                                                                      // adjust tree weight
                spanningTree.add(edge);                                                                                 // add edge to spanning tree
                if (spanningTree.size() == graph.getNodeCount() - 1) break;                                             // break if all edges are in one component  (|E| = |V| - 1)
            }
        }
    }

    private int getEdgeWeight(Edge edge) {
        if (!edge.hasAttribute("weight")) return 1;
        return edge.getAttribute("weight");
    }


    @Override
    public <T extends Edge> Iterator<T> getTreeEdgesIterator() {
        return new SpanningTreeIterator<>();
    }

    @Override                                                                                                           // reset the spanning tree
    public void clear() {
        graph = null;
        spanningTree.clear();
        graphEdges.clear();
        unionFind = null;
        treeWeight = 0;
    }

    public int getTreeWeight() {
        return treeWeight;
    }

    protected class SpanningTreeIterator<T extends Edge> implements Iterator<T> {
        protected Iterator<Edge> it = spanningTree.iterator();
        public boolean hasNext() { return it.hasNext(); }

        @SuppressWarnings("unchecked")
        public T next() {
            return (T) it.next();
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "This iterator does not support remove.");
        }
    }
}
