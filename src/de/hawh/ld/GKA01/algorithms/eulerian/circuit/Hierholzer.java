package de.hawh.ld.GKA01.algorithms.eulerian.circuit;

import de.hawh.ld.GKA01.util.ColoringType;
import de.hawh.ld.GKA01.util.Painter;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class Hierholzer implements EulerianCircuitAlgorithm {


    private Graph graph;
    private NodeInfo[] nodeInfos;
    private final List<Edge> allNonUsedEdges = new ArrayList<>();
    private final LinkedList<Edge> eulerianTour = new LinkedList<>();
    private final Stack<Node> visitedNodesWithUnusedEdges = new Stack<>();

    private static class NodeInfo {
        LinkedList<Edge> incidentNonUsedEdges = new LinkedList<>();
        Node node;
    }


    @Override
    public void init(Graph graph) {
        this.graph = graph;
        allNonUsedEdges.addAll(graph.getEdgeSet());
        nodeInfos = new NodeInfo[graph.getNodeCount()];
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Node currNode = graph.getNode(i);
            nodeInfos[i] = new NodeInfo();
            nodeInfos[i].incidentNonUsedEdges.addAll(currNode.getEdgeSet());
            nodeInfos[i].node = currNode;
        }
    }

    @Override
    public void compute() {

        Painter painter = new Painter();
        painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);


        Node currNode = graph.getNode(0);
        List<Edge> newCycle = formNewCycle(currNode);
        eulerianTour.addAll(newCycle);
        while (eulerianTour.size() < graph.getEdgeCount()) {
            currNode = findNextStartingNode();
            newCycle = formNewCycle(currNode);
            combinePaths(newCycle);


        }



    }



    private Node findNextStartingNode() {
        if (visitedNodesWithUnusedEdges.isEmpty()) return null;

        return visitedNodesWithUnusedEdges.pop();
    }

    private void combinePaths(List<Edge> newCycle) {
        Node node0 = newCycle.get(0).getNode0();
        Node node1 = newCycle.get(0).getNode1();

        Collection<Edge> edgeSet0 = node0.getEdgeSet();
        Collection<Edge> edgeSet1 = node1.getEdgeSet();

        int index = 0;

        for (Edge edge : eulerianTour) {
            if (edgeSet0.contains(edge) || edgeSet1.contains(edge)) {
                index = eulerianTour.indexOf(edge);
                break;
            }
        }
        eulerianTour.addAll(index + 1, newCycle);

    }


    private List<Edge> formNewCycle(Node start) {
        List<Edge> cycle = new ArrayList<>();
        Node currNode = start;
        Edge nextEdge;
        do {

            nextEdge = getNextEdge(currNode);
            if (nextEdge == null) {
                System.out.println("Oh Oh...");
                return null;
            }

            nextEdge.addAttribute("used");
            cycle.add(nextEdge);
            if (!nodeInfos[currNode.getIndex()].incidentNonUsedEdges.isEmpty() && !visitedNodesWithUnusedEdges.contains(currNode)) {
                visitedNodesWithUnusedEdges.push(currNode);
            }
            currNode = nextEdge.getOpposite(currNode);
        } while (currNode != start);
        System.out.println("Cycle formed!");
        return cycle;
    }



    private Edge getNextEdge(Node node) {
        NodeInfo nodeInfo = nodeInfos[node.getIndex()];
        if (nodeInfo.incidentNonUsedEdges.isEmpty()) System.out.println("Wrong Node!!!!");
        Edge nextEdge = nodeInfo.incidentNonUsedEdges.pollFirst();

        return nextEdge;
    }

    @Override
    public List<Edge> getEulerianTour() {
        return eulerianTour;
    }

    public void clear() {

    }
}
