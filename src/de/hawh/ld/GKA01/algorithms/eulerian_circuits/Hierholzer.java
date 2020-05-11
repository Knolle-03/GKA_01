package de.hawh.ld.GKA01.algorithms.eulerian_circuits;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class Hierholzer implements EulerianCircuitAlgorithm {


    private Graph graph;
    private NodeInfo[] nodeInfos;
    private final LinkedList<Edge> eulerianTour = new LinkedList<>();
    private final Stack<Node> visitedNodesWithUnusedEdges = new Stack<>();
    private static class NodeInfo {
        LinkedList<Edge> incidentNonUsedEdges = new LinkedList<>();
    }


    @Override
    public void init(Graph graph) {
        if (!isEulerian(graph)) throw new IllegalArgumentException("The given graph is not eulerian.");
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
        List<Edge> newCycle = formNewCycle(currNode);
        // add it to the circuit
        eulerianTour.addAll(newCycle);

        // until all edges are used
        while (eulerianTour.size() < graph.getEdgeCount()) {
            // find a node which still has unused edges
            currNode = findNextStartingNode();
            // form new cycle from that node
            newCycle = formNewCycle(currNode);
            // add the new cycle to the circuit
            combinePaths(newCycle, currNode);
        }
    }


    // finds next starting node
    private Node findNextStartingNode() {

        // pop first node from stack
        Node nextNode = visitedNodesWithUnusedEdges.pop();
        // reference to edges of the node
        LinkedList<Edge> edgesLeft = nodeInfos[nextNode.getIndex()].incidentNonUsedEdges;

        do {

            // if node has no edges left
            while (edgesLeft.size() == 0) {
                // try next node from the stack
                nextNode = visitedNodesWithUnusedEdges.pop();
                // reference to edges of the node
                edgesLeft = nodeInfos[nextNode.getIndex()].incidentNonUsedEdges;
            }

            // while node has edges left but first one is already used from the other side
            while (edgesLeft.peekFirst().hasAttribute("used")) {
                // remove the used edge
                edgesLeft.pollFirst();
                // and check if there are any left
                if (edgesLeft.size() == 0) break;
            }

            // repeat until the next node has "real" unused edges
        } while (edgesLeft.size() == 0);

        return nextNode;
    }


    // combine the eulerian circuit with a new cycle
    private void combinePaths(List<Edge> newCycle, Node start) {
        // search through eulerian circuit
        for (int i = 0; i < eulerianTour.size(); i++) {
            // until an edge is incident to the start of the new tour
            if (start.getEdgeSet().contains(eulerianTour.get(i))) {
                // add the new cycle right after the found edge
                eulerianTour.addAll(i + 1, newCycle);
                // done
                break;
            }
        }
    }

    // form a cycle for the circuit
    private List<Edge> formNewCycle(Node start) {
        List<Edge> cycle = new ArrayList<>();
        Node currNode = start;
        Edge nextEdge;

        do {
            // take next edge from the list
            nextEdge = nodeInfos[currNode.getIndex()].incidentNonUsedEdges.pollFirst();
            assert nextEdge != null;

            // mark it as used
            nextEdge.addAttribute("used");
            // add it to the cycle
            cycle.add(nextEdge);
            // if the current node still has edges left to use and isn't already on the stack
            if (!nodeInfos[currNode.getIndex()].incidentNonUsedEdges.isEmpty() && !visitedNodesWithUnusedEdges.contains(currNode)){
                // push it on the stack
                visitedNodesWithUnusedEdges.push(currNode);
            }
            // jump to the adjacent node using the edge
            currNode = nextEdge.getOpposite(currNode);
            // remove all leading edges from the list that were used to get to this node
            removeLeadingUsedEdges(currNode);
            // repeat until the start is visited and it has no edges left to use
        } while (currNode != start || !nodeInfos[currNode.getIndex()].incidentNonUsedEdges.isEmpty());

        // return the cycle
        return cycle;
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
    }

    public boolean isEulerian(Graph graph) {
        boolean allEven = true;

        // check if all nodes have even degree
        for (Node node : graph) {
            if (node.getDegree() % 2 != 0) {
                allEven = false;
                break;
            }
        }

        // also check if the graph is connected
        return allEven && Toolkit.isConnected(graph);
    }
}
