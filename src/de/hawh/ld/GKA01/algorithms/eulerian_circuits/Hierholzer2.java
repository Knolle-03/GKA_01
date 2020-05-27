package de.hawh.ld.GKA01.algorithms.eulerian_circuits;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class Hierholzer2 implements EulerianCircuitAlgorithm {


    private Graph graph;
    private NodeInfo[] nodeInfos;
    private final List<Edge> eulerianTour = new ArrayList<>();
    private static class NodeInfo {
        LinkedList<Edge> incidentNonUsedEdges = new LinkedList<>();
    }






    @Override
    public void init(Graph graph) {
        if (graph.getNodeCount() < 1 || !isEulerian(graph)) throw new IllegalArgumentException("The given graph is not eulerian.");
        // init instance variables
        this.graph = graph;
        nodeInfos = new NodeInfo[graph.getNodeCount()];

        // init array with info about each nodes edges
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Node currNode = graph.getNode(i);
            nodeInfos[i] = new NodeInfo();
            nodeInfos[i].incidentNonUsedEdges.addAll(currNode.getEdgeSet());
        }
    }

    @Override
    public void compute() {
        // start with arbitrary node
        Node currNode = graph.getNode(0);
        // form first cycle
        Deque<Edge> newCycle = formCircuit(currNode);
        // add it to the circuit
        eulerianTour.addAll(newCycle);

    }


    // form a new cycle for the circuit
    private Deque<Edge> formCircuit(Node start) {

        Deque<Edge> cycle = new ArrayDeque<>();
        Node currNode = start;
        Edge nextEdge;

        do {
            while (!nodeInfos[currNode.getIndex()].incidentNonUsedEdges.isEmpty()) {
                nextEdge = nodeInfos[currNode.getIndex()].incidentNonUsedEdges.pollFirst();
                assert nextEdge != null;
                nextEdge.addAttribute("used");
                cycle.addFirst(nextEdge);
                currNode = nextEdge.getOpposite(currNode);
                removeLeadingUsedEdges(currNode);
            }

            if (cycle.size() < graph.getEdgeCount()) currNode = unstuck(cycle, currNode);



        } while (cycle.size() < graph.getEdgeCount());


        // return the cycle
        return cycle;
    }


    private Node unstuck(Deque<Edge> circuit, Node stuckOn) {
        NodeInfo nodeInfo = nodeInfos[stuckOn.getIndex()];
        LinkedList<Edge> edges = nodeInfo.incidentNonUsedEdges;

        while (edges.isEmpty()) {
            Edge lastAdded = circuit.removeFirst();
            stuckOn = lastAdded.getOpposite(stuckOn);
            circuit.addLast(lastAdded);
            nodeInfo = nodeInfos[stuckOn.getIndex()];
            edges = nodeInfo.incidentNonUsedEdges;
            removeLeadingUsedEdges(stuckOn);
        }

        return stuckOn;

    }

    // remove all leading used edges from the list
    private void removeLeadingUsedEdges(Node node) {

        // reference to list
        LinkedList<Edge> edges = nodeInfos[node.getIndex()].incidentNonUsedEdges;

        // if the list is empty there is nothing to remove
        if (edges.isEmpty()) return;
        // while there are used edges at the beginning of the list
        while (edges.peekFirst().hasAttribute("used")) {
            // remove those edges
            edges.pollFirst();
            // if removed edge was the last edge stop
            if (edges.isEmpty()) return;
        }
    }

    @Override
    public List<Edge> getEulerianCircuit() {
        return eulerianTour;
    }

    public void clear() {
        eulerianTour.clear();
        nodeInfos = null;
        for (Edge edge : graph.getEdgeSet()) {
            edge.removeAttribute("used");
        }
    }

    private boolean isEulerian(Graph graph) {

        // check if all nodes have even degree
        for (Node node : graph) {
            if (node.getDegree() % 2 != 0) {
                return false;
            }
        }

        // also check if the graph is connected
        return Toolkit.isConnected(graph);
    }
}