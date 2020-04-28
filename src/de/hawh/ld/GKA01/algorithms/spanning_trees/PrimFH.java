package de.hawh.ld.GKA01.algorithms.spanning_trees;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PrimFH extends AbstractSpanningTree {

    private final List<Edge> spanningTree = new ArrayList<>();
    private final FibonacciHeap<Integer, Node> nodesOrderedByCost = new FibonacciHeap<>();                              // Heap storing all nodes in graph (ordered by cost to reach them)
    private NodeInfo[] nodeInfo;                                                                                        // stores nodeInfo for each node in the graph
    private int treeWeight;
    private static class NodeInfo {                                                                                     // stores info of a single node
        FibonacciHeap<Integer, Node>.Node nodeReferenceInHeap;                                                                         // reference to node in heap
        Edge cheapestEdgeToUse = null;                                                                                  // cheapest edge to get to the node


//        @Override
//        public String toString() {
//            return "NodeInfo: node = " + node + ", cheapestEdgeToUse=" + cheapestEdgeToUse;
//        }
    }

    @Override
    protected void makeTree() {
        nodeInfo = new NodeInfo[graph.getNodeCount()];                                                                  // init an array with each NodeInfo object
        for (int i = 0; i < graph.getNodeCount() ; i++) {                                                               // representing a single node component
            nodeInfo[i] = new NodeInfo();
            nodeInfo[i].nodeReferenceInHeap = nodesOrderedByCost.add(Integer.MAX_VALUE, graph.getNode(i));              // Get reference to node in heap
        }                                                                                                               // to use decreaseKey() later

        while (!nodesOrderedByCost.isEmpty()) {                                                                         // visit all nodes in the graph
            Node currNode = nodesOrderedByCost.extractMin();                                                            // get current min
            NodeInfo minNodeInfo = nodeInfo[currNode.getIndex()];                                                       // get data of current min
            nodeInfo[currNode.getIndex()] = null;                                                                       // mark node as visited
            if (minNodeInfo.cheapestEdgeToUse != null) addLowestCostEdgeToSpanningTree(minNodeInfo);                    // add lowest cost edge to spanning tree
            for (Edge edge : currNode) {                                                                                // look at each adjacent node
                if (nodeInfo[edge.getOpposite(currNode).getIndex()] != null) {                                          // skip edges to visited nodes
                    adjustCostOfReachableNodes(edge, currNode);                                                         // let node swim up that got cheaper to use
                }
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

    private void adjustCostOfReachableNodes(Edge edge, Node currNode) {
        NodeInfo oppositeNodeInfo = nodeInfo[edge.getOpposite(currNode).getIndex()];
        if (getEdgeWeight(edge) < oppositeNodeInfo.nodeReferenceInHeap.getKey()) {                                          // if currently looked at edge is cheaper to use than best known edge
            nodesOrderedByCost.decreaseKey(oppositeNodeInfo.nodeReferenceInHeap, getEdgeWeight(edge));                // let Node swim up in heap by decreasing the key
            oppositeNodeInfo.cheapestEdgeToUse = edge;                                                                  // replace last best edge with current edge
        }
    }

    private void addLowestCostEdgeToSpanningTree(NodeInfo minNodeInfo) {
        spanningTree.add(minNodeInfo.cheapestEdgeToUse);                                                                   // add cheapest edge to spanning tree
        treeWeight += minNodeInfo.nodeReferenceInHeap.getKey();                                                         // adjust tree weight
    }

    public void clear() {                                                                                               // rest spanning tree
        treeWeight = 0;
        spanningTree.clear();
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
