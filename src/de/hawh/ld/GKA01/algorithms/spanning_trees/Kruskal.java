package de.hawh.ld.GKA01.algorithms.spanning_trees;


import de.hawh.ld.GKA01.util.UnionFind;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.graph.Edge;

import java.util.*;




public class Kruskal extends AbstractSpanningTree {

    List<Edge> graphEdges = new ArrayList<>();                                                                          // stores all edges of the graph
    UnionFind unionFind;                                                                                                // keeps track of components
    int treeWeight = 0;                                                                                                 // weight of spanning tree
    List<Edge> treeEdges = new ArrayList<>();                                                                           // stores edges of spanning tree




    @Override
    protected void makeTree() {
        unionFind = new UnionFind(graph.getNodeCount());                                                                // init UnionFind object with each node's ID representing a single node component
        graphEdges.addAll(graph.getEdgeSet());                                                                          // add all edges to the list
        graphEdges.sort(Comparator.comparingInt(this::getEdgeWeight));                                                  // sort the edges in ascending order by weight
        for (Edge edge : graphEdges) {                                                                                  // look at each edge
            if (!unionFind.connected(edge.getNode0().getIndex(), edge.getNode1().getIndex())) {                         // if the incident nodes are not connected
                unionFind.union(edge.getNode0().getIndex(), edge.getNode1().getIndex());                                // unite the two components to one via the current edge
                treeWeight += getEdgeWeight(edge);                                                                      // adjust tree weight
                treeEdges.add(edge);                                                                                    // add edge to spanning tree
                if (treeEdges.size() == graph.getNodeCount() - 1) {                                                     // break if all edges are in one component  (|E| = |V| - 1)
                    break;
                }
            }
        }
    }

    @Override
    public <T extends Edge> Iterator<T> getTreeEdgesIterator() {
        return new TreeIterator<>();
    }


    @Override                                                                                                           // reset the spanning tree
    public void clear() {
        graphEdges.clear();
        unionFind = null;
        treeWeight = 0;
        treeEdges.clear();

    }



    private int getEdgeWeight(Edge edge) {
        if (!edge.hasAttribute("weight"))  return 1;                                                                // returns 1 as default value if no weight attribute is given.
        return edge.getAttribute("weight");                                                                         // Otherwise the value stored in the attribute is returned.
    }




    public int getTreeWeight() {                                                                                        // returns tree weight
        return treeWeight;
    }


    protected class TreeIterator<T extends Edge> implements Iterator<T> {

        protected Iterator<Edge> it = treeEdges.iterator();

        public boolean hasNext() {
            return it.hasNext();
        }

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
