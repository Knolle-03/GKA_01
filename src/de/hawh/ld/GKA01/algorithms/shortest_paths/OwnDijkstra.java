package de.hawh.ld.GKA01.algorithms.shortest_paths;


//          _   _       _                      _            __
//         | \ | |     | |                    | |          / _|
//         |  \| | ___ | |_   _ __   __ _ _ __| |_    ___ | |_
//         | . ` |/ _ \| __| | '_ \ / _` | '__| __|  / _ \|  _|
//         | |\  | (_) | |_  | |_) | (_| | |  | |_  | (_) | |
//         |_| \_|\___/ \__| | .__/ \__,_|_|   \__|  \___/|_|
//                           | |
//                           |_|
//
//          _   _                                   _
//         | | | |                                 (_)
//         | |_| |__   ___    _____  _____ _ __ ___ _ ___  ___
//         | __| '_ \ / _ \  / _ \ \/ / _ \ '__/ __| / __|/ _ \
//         | |_| | | |  __/ |  __/>  <  __/ | | (__| \__ \  __/
//         \__|_| |_|\___|  \___/_/\_\___|_|  \___|_|___/\___|


import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OwnDijkstra {

    public static class Data {
        FibonacciHeap<Double, Node>.Node firstNode;
        double distance;
        Node predecessor = null;
        boolean OK = false;

        public double getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "firstNode=" + firstNode +
                    ", distance=" + distance +
                    ", predecessor=" + predecessor +
                    ", OK=" + OK +
                    '}';
        }
    }

    private Node source;
    private final Graph graph;
    private final String DATA = "data";



    public OwnDijkstra(Graph graph) {
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
                //TODO change to use int instead of double
                int intWeight = connection.getAttribute("weight");
                double weight = Double.parseDouble(String.valueOf(intWeight));

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
