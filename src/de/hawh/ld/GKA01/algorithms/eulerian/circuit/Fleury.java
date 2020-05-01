package de.hawh.ld.GKA01.algorithms.eulerian.circuit;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class Fleury implements Algorithm {

    private ConnectedComponents cc = new ConnectedComponents();
    private Graph graph;
    private String cutAttribute;
    private final List<Edge> eulerianTour = new ArrayList<>();


    @Override
    public void init(Graph graph) {
        cc.init(graph);
        cc.setCutAttribute("used");
        this.graph = graph;
    }

    @Override
    public void compute() {
        Node currNode = graph.getNode(0);
        // while not all edges are used
        while (eulerianTour.size() < graph.getEdgeCount()) {
            // find next edge to use


            for (Edge edge : currNode.getEdgeSet()) {
                if (edge.hasAttribute("used")) continue;
                if (onlyOnePossibleEdge(currNode)) {
                    edge.addAttribute("used");
                    // use it
                    eulerianTour.add(edge);
                    // set opposite node as current node
                    currNode = edge.getOpposite(currNode);
                    break;
                }
                // "delete" edge
                edge.addAttribute("used");
                // compute number of components
                cc.compute();
                // if edge wasn't a bridge
                System.out.println(cc.getConnectedComponentsCount());
                if (cc.getConnectedComponentsCount() == 1) {
                    // use it
                    eulerianTour.add(edge);
                    // set opposite node as current node
                    currNode = edge.getOpposite(currNode);
                    break;
                // the edge was a bridge
                } else {
                    // add bridge again
                    edge.removeAttribute("used");
                    // compute components again
                    cc.compute();
                }
            }
        }

    }


    private boolean onlyOnePossibleEdge(Node node) {
        int nonUsedEdges = 0;
        for (Edge edge : node.getEdgeSet()) {
            if (!edge.hasAttribute("used")) nonUsedEdges++;
        }
        return nonUsedEdges == 1;
    }


    public List<Edge> getEulerianTour() {
        return eulerianTour;
    }
}
