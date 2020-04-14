package de.hawh.ld.GKA01.algorithms;

import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyDijkstra {

    protected static class Data {
        FibonacciHeap<Double, Node>.Node firstNode;
        double distance;
        Node predecessor = null;
        boolean OK = false;
    }

    private Node source;
    private final Graph graph;
    private final String DATA = "data";



    public MyDijkstra(Graph graph) {
        this.graph = graph;
    }


    public void compute(Node source) {
        this.source = source;
        FibonacciHeap<Double, Node> heap = new FibonacciHeap<>();
        for (Node node : graph) {
            Data data = new Data();
            if (node == source) {
                data.distance = 0;
                data.predecessor = node;
            } else {
                data.distance = Double.POSITIVE_INFINITY;
            }
            node.addAttribute(DATA, data);
            data.firstNode = heap.add(data.distance, node);
        }

        while (!heap.isEmpty()) {
            Node currentNode = heap.extractMin();
            Data currData = currentNode.getAttribute(DATA);
            currData.OK = true;
            List<Node> adjNodes = getAdjacentReachableNonOkNodes(currentNode);
            for (Node node : adjNodes) {
                Edge connection = node.getEdgeBetween(currentNode);
                double weight = connection.getAttribute("weight");

                Data nodeData = node.getAttribute(DATA);


                Data currNodeData = currentNode.getAttribute(DATA);
                if (nodeData.distance > (currNodeData.distance + weight)) {
                    nodeData.distance = currNodeData.distance + weight;
                    nodeData.predecessor = currentNode;
                    heap.decreaseKey(nodeData.firstNode, nodeData.distance);
                }
            }

        }
    }


    private List<Node> getAdjacentReachableNonOkNodes(Node currentNode) {
        // all "usable" edges
        Iterable<? extends Edge> iterable = currentNode.getEachLeavingEdge();

        List<Node> adjacentReachableNonOkNNodes = new ArrayList<>();
        //each reachable Node
        for (Edge edge : iterable) {
            Node node = edge.getOpposite(currentNode);
            Data data = node.getAttribute(DATA);
            boolean ok = data.OK;
            // if the node has no step attribute it was not visited yet
            if (!ok) {
                adjacentReachableNonOkNNodes.add(node);
            }
        }

        return adjacentReachableNonOkNNodes;
    }


    public List<Node> getPath(Node target) {
        LinkedList<Node> nodeList = new LinkedList<>();

        Node currentNode = target;
        nodeList.add(currentNode);
        while (!nodeList.contains(source)) {
            Data data = currentNode.getAttribute(DATA);
            nodeList.addFirst(data.predecessor);
            currentNode = data.predecessor;
        }

        return nodeList;
    }







}
