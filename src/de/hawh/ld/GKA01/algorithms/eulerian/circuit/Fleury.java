package de.hawh.ld.GKA01.algorithms.eulerian.circuit;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class Fleury implements Algorithm {

    private final ConnectedComponents connectedComponents = new ConnectedComponents();
    private Graph graph;
    private final List<Edge> eulerianTour = new ArrayList<>();
    private Node currNode;
    private Node startNode;


    @Override
    public void init(Graph graph) {
        connectedComponents.init(graph);
        connectedComponents.setCutAttribute("used");
        startNode = graph.getNode(0);
        this.graph = graph;
    }

    @Override
    public void compute() {

        currNode = startNode;                                                                                           // start with an arbitrary node
        while (usableEdges(currNode).size() > 0) {                                                                      // while not all edges are used
            List<Edge> usableEdges = usableEdges(currNode);                                                             // list of all edges that were not used yet.
            if (usableEdges.size() == 1) {                                                                              // if there is only one edge left to use
                Edge nextEdge = usableEdges.get(0);                                                                     // use it
                nextEdge.addAttribute("used");
                useEdge(nextEdge);
            } else {                                                                                                    // if there are more
                Edge nextEdge = getNonBridgeEdge(usableEdges);                                                          // find a non bridge edge
                assert nextEdge != null;
                useEdge(nextEdge);                                                                                      // use it
            }
        }

    }

    // returns a List of all usable Edges
    private List<Edge> usableEdges(Node node) {
        List<Edge> usableEdges = new ArrayList<>();
        for (Edge edge : node.getEdgeSet()) {
            if (!edge.hasAttribute("used")) usableEdges.add(edge);
        }
        return usableEdges;
    }


    private void useEdge(Edge edge) {
        currNode = edge.getOpposite(currNode);
        eulerianTour.add(edge);
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
            // if component count is still one the edge is not bridge
            if (connectedComponents.getConnectedComponentsCount(2) == 1 && edge.getOpposite(currNode) != startNode) {
                assert connectedComponents.getConnectedComponentsCount(2) == 1;
                return edge;
            }
            // else it is a bridge. Therefore "add" the edge back in.
            else {
                edge.removeAttribute("used");
                connectedComponents.compute();
                assert connectedComponents.getConnectedComponentsCount(2) == 1;
            }
        }


        // if we get here there is no non bridge edge left.

        return null;
    }


    public List<Edge> getEulerianTour() {
        return eulerianTour;
    }

    public void clear() {
        eulerianTour.clear();
        for (Edge edge : graph.getEdgeSet()) {
            edge.removeAttribute("used");
        }
    }
}





//for (Edge edge : currNode.getEdgeSet()) {
//        if (edge.hasAttribute("used")) continue;
//        if (usableEdges(currNode)) {
//        edge.addAttribute("used");
//        // use it
//        eulerianTour.add(edge);
//        // set opposite node as current node
//        currNode = edge.getOpposite(currNode);
//        break;
//        }
//        // "delete" edge
//        edge.addAttribute("used");
//        // compute number of components
//        cc.compute();
//        // if edge wasn't a bridge
//        System.out.println(cc.getConnectedComponentsCount());
//        if (cc.getConnectedComponentsCount() == 1) {
//        // use it
//        eulerianTour.add(edge);
//        // set opposite node as current node
//        currNode = edge.getOpposite(currNode);
//        break;
//        // the edge was a bridge
//        } else {
//        // add bridge again
//        edge.removeAttribute("used");
//        // compute components again
//        cc.compute();
//        }
//        }
