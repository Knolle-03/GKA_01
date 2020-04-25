package de.hawh.ld.GKA01.algorithms.spanning_trees;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.*;

public class PrimPQ extends AbstractSpanningTree {

    private final String VISITED = "visited";
    private final String USED = "used";
    private final String IN_QUEUE = "inQueue";

    private final Set<Edge> spanningTree = new HashSet<>();
    private final PriorityQueue<Edge> edgesToChooseFrom = new PriorityQueue<>(Comparator.comparingInt(PrimPQ::getWeight));

    private int treeWeight;

    private Node source;




    @Override
    protected void makeTree() {

        source = graph.getNode(0);
        edgesToChooseFrom.addAll(source.getEdgeSet());                                                                  // s*log(n) (edges of source)
        for (Edge edge : source) {
            edge.addAttribute(IN_QUEUE);
        }
        source.addAttribute(VISITED);

        while (spanningTree.size() < graph.getNodeCount() - 1 && !edgesToChooseFrom.isEmpty()) {                        // v (nodes in graph)
                                                                                                                        //      *
            Edge minEdge = edgesToChooseFrom.poll();                                                                    //      log(n)

            if (isStillRelevant(minEdge)) {
                Node newNode = getNewlyAddedNode(minEdge);
                spanningTree.add(minEdge);
                newNode.addAttribute(VISITED);
                minEdge.addAttribute(USED);
                addRelevantEdges(newNode);                                                                              //      e*(log(n))
                treeWeight += getWeight(minEdge);
            }
        }
    }

    @Override
    public <T extends Edge> Iterator<T> getTreeEdgesIterator() {
        return null;
    }

    @Override
    public void clear() {
        for (Node node : graph) {
            node.removeAttribute(VISITED);
        }
        for (Edge edge : graph.getEdgeSet()) {
            edge.removeAttribute(USED);
        }
        treeWeight = 0;
        spanningTree.clear();
        edgesToChooseFrom.clear();
        this.graph = null;
        this.source = null;
    }
                                                                                                                        // v = nodes in graph
                                                                                                                        // e = edges of current node
                                                                                                                        // m = edges in PQ
                                                                                                                        // O(v*(log(n) + log(n^e)))
                                                                                                                        // O(v * log(n^(e + 1))







    private boolean isStillRelevant(Edge edge) {
        return !(edge.getNode0().hasAttribute(VISITED) && edge.getNode1().hasAttribute(VISITED));                       // 1
    }                                                                                                                   // --> O(1)


    private Node getNewlyAddedNode(Edge edge) {
        if (edge.getNode0().hasAttribute(VISITED)) return edge.getNode1();                                              // 1
        return edge.getNode0();                                                                                         // --> O(1)
    }

    private void addRelevantEdges(Node node) {
        for (Edge edge : node) {                                                                                        // e (edges of the node)
            if (!edge.hasAttribute(USED) && !edge.hasAttribute(IN_QUEUE)) edgesToChooseFrom.add(edge);                  // 1 + 1 + log(n)
        }                                                                                                               // --> O(e*(log(n)))
    }


    public static int getWeight(Edge edge) {
        return edge.getAttribute("weight");                                                                         // 1
    }                                                                                                                   // --> O(1)

    public int getTreeWeight() {
        return treeWeight;
    }
}
