package de.hawh.ld.GKA01.algorithms.eulerian_circuits;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class Fleury implements EulerianCircuitAlgorithm {

    private final ConnectedComponents connectedComponents = new ConnectedComponents();
    private Graph graph;
    private final List<Edge> eulerianCircuit = new ArrayList<>();
    private Node currNode;
    private Node startNode;


    @Override
    public void init(Graph graph) {
        if (graph.getNodeCount() < 1 || !isEulerian(graph)) throw new IllegalArgumentException("The given graph is not eulerian.");

        // init instance variables
        connectedComponents.init(graph);
        connectedComponents.setCutAttribute("used");
        startNode = graph.getNode(0);
        this.graph = graph;
    }

    @Override
    public void compute() {
        // start with an arbitrary node
        currNode = startNode;
        // while not all edges are used
        while (usableEdges(currNode).size() > 0) {
            // list of all edges that were not used yet.
            List<Edge> usableEdges = usableEdges(currNode);
            Edge nextEdge;
            // if there is only one edge left to use
            if (usableEdges.size() == 1) {
                nextEdge = usableEdges.get(0);
                nextEdge.addAttribute("used");
            }
            // if there are more
            else {
                // find a non bridge edge
                nextEdge = getNonBridgeEdge(usableEdges);
                assert nextEdge != null;
            }
            // use it
            useEdge(nextEdge);
        }

    }

    // returns a List of all usable Edges
    private List<Edge> usableEdges(Node node) {
        List<Edge> usableEdges = new ArrayList<>();
        // look at each edge of the node
        for (Edge edge : node.getEdgeSet()) {
            // if it wasn't used yet add it to the list
            if (!edge.hasAttribute("used")) usableEdges.add(edge);
        }

        return usableEdges;
    }


    private void useEdge(Edge edge) {
        // jump to adjacent node using the edge
        currNode = edge.getOpposite(currNode);
        // add edge to eulerian circuit
        eulerianCircuit.add(edge);
    }


    // finds a non bridge edge for nodes that have more than one edge left to use
    //
    private Edge getNonBridgeEdge(List<Edge> usableEdges) {
        assert connectedComponents.getConnectedComponentsCount(2) == 1;

        // try each edge
        for (Edge edge : usableEdges) {
            // "delete" edge
            edge.addAttribute("used");
            // recalculate component count
            connectedComponents.compute();
            // if component count is still one the edge is not a bridge
            if (connectedComponents.getConnectedComponentsCount(2) == 1 && edge.getOpposite(currNode) != startNode) {
                assert connectedComponents.getConnectedComponentsCount(2) == 1;
                return edge;
            }
            // else it is a bridge.
            else {
                // therefore "add" the edge back in
                edge.removeAttribute("used");
                connectedComponents.compute();
                assert connectedComponents.getConnectedComponentsCount(2) == 1;
            }
        }


        // if we get here there is no non bridge edge left
        // which in reality can't happen, since we only call this method
        // if we have two or more edges available

        return null;
    }

    public List<Edge> getEulerianCircuit() {
        return eulerianCircuit;
    }

    public void clear() {
        eulerianCircuit.clear();
        for (Edge edge : graph.getEdgeSet()) {
            edge.removeAttribute("used");
        }
    }

    // checks if the given graph is eulerian
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
