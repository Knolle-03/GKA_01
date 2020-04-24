package de.hawh.ld.GKA01.algorithms.spanning_trees;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimFibonacciHeap extends AbstractSpanningTree {

    private List<Edge> spanningTree;
    private int treeWeight = 0;


    protected static class Data {
        Edge cheapestEdgeToUse = null;
        FibonacciHeap<Integer, Node>.Node node;
    }



    @Override
    protected void makeTree() {
        spanningTree = new ArrayList<>();
        FibonacciHeap<Integer, Node> heap = new FibonacciHeap<>();                                                  // 1
        Data[] nodeData = new Data[graph.getNodeCount()];                                                           // 1

        for (int i = 0; i < graph.getNodeCount() ; i++) {                                                           // v (v = nodes in graph)
            nodeData[i] = new Data();                                                                               // 1
            nodeData[i].node = heap.add(Integer.MAX_VALUE, graph.getNode(i));                                       // 1
        }
        // visit all nodes of the graph
        while (!heap.isEmpty()) {                                                                                   // n (n = nodes in the heap)
            //get current min
            Node currNode = heap.extractMin();                                                                      // log(n) (n = nodes in the heap)

            //get data of current min
            Data minData = nodeData[currNode.getIndex()];                                                           // 1

            //mark node as visited
            nodeData[currNode.getIndex()] = null;                                                                   // 1


            if (minData.cheapestEdgeToUse != null){                                                                 // 1
                spanningTree.add(minData.cheapestEdgeToUse);                                                        // 1
                treeWeight += minData.node.getKey();                                                                // 1
                minData.cheapestEdgeToUse = null;                                                                   // 1
            }


            // bubble up low cost edges in heap
            for (Edge edge : currNode) {                                                                            // e (edges of currNode)
                // skip edges to visited nodes                                                                      // e << n
                if (nodeData[edge.getOpposite(currNode).getIndex()] != null) {                                      // 1
                    Node oppositeNode = edge.getOpposite(currNode);                                                 // 1
                    Data oppositeNodeData = nodeData[oppositeNode.getIndex()];                                      // 1
                    // replace Integer.MAX_VALUE with actual edge weight if edge is viewed for the first time or
                    // adjust the cost for reaching that node if it is cheaper from another node.
                    int weight = edge.getAttribute("weight");                                                  // 1
                    if (weight < oppositeNodeData.node.getKey()) {                                                  // 1
                        heap.decreaseKey(oppositeNodeData.node, weight);                                            // 1
                        oppositeNodeData.cheapestEdgeToUse = edge;                                                  // 1
                    }
                }
                // O(v + n*(log(n) + e) )
                // O(v + (n*log(n))


            }
        }
    }

    @Override
    public <T extends Edge> Iterator<T> getTreeEdgesIterator() {
        return null;
    }

    public void clear() {
        treeWeight = 0;
        spanningTree.clear();
    }

    public List<Edge> getSpanningTree() {
            return spanningTree;
        }

    public int getTreeWeight() {
            return treeWeight;
        }

}
