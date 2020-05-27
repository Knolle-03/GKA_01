package de.hawh.ld.GKA01.algorithms.eulerian_circuits;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fleury2 implements EulerianCircuitAlgorithm {

    private int vertices; // No. of vertices
    private ArrayList<Integer>[] adj; // adjacency list
    private Graph graph;
    private final List<Edge> eulerTour = new ArrayList<>();

    // for test
    private Node first;
    private Node last;



    @Override
    public void init(Graph graph) {
        this.graph = graph;
        vertices = graph.getNodeCount();
        if (!isEulerian(graph) || graph.getNodeCount() < 1) throw new IllegalArgumentException("blaa");
        adj = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adj[i] = new ArrayList<>();
            for (Edge edge : graph.getNode(i).getEachEdge()) {
                adj[i].add(edge.getOpposite(graph.getNode(i)).getIndex());
            }
        }

        System.out.println("test " + Arrays.toString(adj));
    }


    private void addEdge(Integer nodeIndex_1, Integer nodeIndex_2) {
        adj[nodeIndex_1].add(nodeIndex_2);
        adj[nodeIndex_2].add(nodeIndex_1);
    }

    private void removeEdge(Integer nodeIndex_1, Integer nodeIndex_2) {
        adj[nodeIndex_1].remove(nodeIndex_2);
        adj[nodeIndex_2].remove(nodeIndex_1);
    }

    @Override
    public void compute() {
        if (graph.getNodeCount() == 1) return;

        int startNode = graph.getNode(0).getIndex();


//        for (int i = 0; i < vertices; i++) {
//            if (adj[i].size() % 2 == 1) {
//                startNode = i;
//                break;
//            }
//        }

        printEulerUtil(startNode);
        first = graph.getNode(startNode);
        System.out.println();
    }

    private void printEulerUtil(int currentNodeIndex) {

        graph.getNode(currentNodeIndex).setAttribute("visited", true);
        last = graph.getNode(currentNodeIndex);
        for (int i = 0; i < adj[currentNodeIndex].size(); i++) {

            int oppositeNode = adj[currentNodeIndex].get(i);


            if (isValidNextEdge(currentNodeIndex, oppositeNode)) {
                // get Edge between current und opposite Node
                Edge edge = graph.getNode(currentNodeIndex).getEdgeBetween(oppositeNode);
                eulerTour.add(edge);
                edge.setAttribute("visited", true);

                //System.out.println(currentNodeIndex + "-" + oppositeNode + " ");

                removeEdge(currentNodeIndex, oppositeNode);
                // continue
                printEulerUtil(oppositeNode);
            }
        }
    }


    private boolean isValidNextEdge(Integer nodeIndex_1, Integer nodeIndex_2) {



        if (adj[nodeIndex_1].size() == 1) {
            return true;
        }


        boolean[] isVisited = new boolean[this.vertices];
        int count1 = nodesCounter(nodeIndex_1, isVisited);
//        System.out.println("Before Remove");
//        System.out.println("counter = " + count1);

        removeEdge(nodeIndex_1, nodeIndex_2);
        isVisited = new boolean[this.vertices];
        int count2 = nodesCounter(nodeIndex_1, isVisited);
//        System.out.println("After Remove");
//        System.out.println("counter = " + count2);

        addEdge(nodeIndex_1, nodeIndex_2);
        return count1 <= count2;
    }


    private int nodesCounter(Integer nodeIndex, boolean[] isVisited) {
        isVisited[nodeIndex] = true;
        int count = 1;
        for (int adj : adj[nodeIndex]) {
            if (!isVisited[adj]) {
                count = count + nodesCounter(adj, isVisited);
            }
        }

        return count;

//        Iterator<Node> graphIterator = graph.getNode(nodeIndex).getBreadthFirstIterator(false);
//        int visitedNodes = 1;
//        while (graphIterator.hasNext()) {
//            Node currNode = graphIterator.next();
//            if (!currNode.hasAttribute("visited")) {
//                visitedNodes++;
//            }
//        }
//        return visitedNodes;
    }

    public boolean isEulerian(Graph graph) {
        boolean allEven = true;
        for (Node node : graph) {
            if (node.getDegree() % 2 != 0) {
                allEven = false;
                break;
            }
        }
        return allEven && Toolkit.isConnected(graph);
    }


    public boolean isAllNodesUsed() {
        boolean allVisited = true;
        for (Node node : graph.getEachNode()) {

            if (!((boolean) node.getAttribute("visited"))) {
                allVisited = false;
                break;
            }
        }
        return allVisited;
    }

    public boolean isAllEdgesVisited() {
        boolean allVisited = true;
        for (Edge edge : graph.getEachEdge()) {

            if (!((boolean) edge.getAttribute("visited"))) {
                allVisited = false;
                break;
            }
        }
        return allVisited;
    }

    public boolean isCircle() {
        return first == last;
    }

    @Override
    public List<Edge> getEulerianCircuit() {
        return eulerTour;
    }

    @Override
    public void clear() {
        graph = null;
        eulerTour.clear();
    }

}
