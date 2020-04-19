package de.hawh.ld.GKA01.algorithms.spanning_trees;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class PrimPQ {
    //Comparator.comparingInt(this::getWeight)
    private final String VISITED = "visited";
    private final String USED ="used";

    private final Set<Edge> spanningTree = new HashSet<>();
    private final PriorityQueue<Edge> edgesToChooseFrom = new PriorityQueue<>(Comparator.comparingInt(this::getWeight));

    private int treeWeight;



    public void getMinSpanTree(Graph graph, Node source) {

        edgesToChooseFrom.addAll(source.getEdgeSet());                                                                  // e*log(n) (edges of source)
        source.addAttribute(VISITED);                                                                                   // 1
                                                                                                                        // +
        while (spanningTree.size() < graph.getNodeCount() - 1 && !edgesToChooseFrom.isEmpty()) {                        // v - 1 (nodes in graph)
                                                                                                                        // *
            Edge minEdge = edgesToChooseFrom.poll();                                                                    // log(n)
                                                                                                                        // +
            if (isStillRelevant(minEdge)) {                                                                             // 1
                Node newNode = getNewlyAddedNode(minEdge);                                                              // 1
                spanningTree.add(minEdge);                                                                              // 1
                newNode.addAttribute(VISITED);                                                                          // 1
                minEdge.addAttribute(USED);                                                                             // 1
                addRelevantEdges(newNode);                                                                              // e*(n + log(n))
                treeWeight += getWeight(minEdge);                                                                       // 1
            }
        }
    }
                                                                                                                        // O(e*log(n) + 1 + (v - 1) * (log(n) + 2 + 1 + 1 + 1 + 1 + (e*(n + log(n))) + 2))
                                                                                                                        // O(e*log(n) + 1 + (v - 1) * (log(n) + 8 + e*(n + log(n)))
                                                                                                                        // O(e*log(n) + v*(log(n)+ e*(n + log(n)))
                                                                                                                        // first e << n
                                                                                                                        // O(v*(log(n) + e * (n + log(n)))
                                                                                                                        // v = nodes in graph
                                                                                                                        // e = edges of current node
                                                                                                                        // m = edges in PQ





    private boolean isStillRelevant(Edge edge) {
        return !(edge.getNode0().hasAttribute(VISITED) && edge.getNode1().hasAttribute(VISITED));                       // 1
    }                                                                                                                   // --> O(1)


    private Node getNewlyAddedNode(Edge edge) {
        if (edge.getNode0().hasAttribute(VISITED)) return edge.getNode1();                                              // 1
        return edge.getNode0();                                                                                         // --> O(1)
    }

    private void addRelevantEdges(Node node) {
        for (Edge edge : node) {                                                                                        // e (edges of the node)
            if (!edge.hasAttribute(USED) && !edgesToChooseFrom.contains(edge)) edgesToChooseFrom.add(edge);             // 1 + n + log(n)
        }                                                                                                               // --> O(e*(n + log(n)))
    }


    private int getWeight(Edge edge) {
        return edge.getAttribute("weight");                                                                        // 1
    }                                                                                                                   // --> O(1)

    public int getTreeWeight() {
        return treeWeight;
    }
}
