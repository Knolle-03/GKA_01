package de.hawh.ld.GKA01.algorithms.shortestPaths;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ShortestPath extends AbstractSpanningTree {


    private BFS bfs;
    private Node source;
    private Node target;
    private LinkedList<Node> path;


    public void setSource(Node source) {
        this.source = source;

    }

    public void setTarget(Node target) {
        this.target = target;
        bfs = new BFS(graph, source, target);
        path = new LinkedList<>();
    }

    @Override
    protected void makeTree() {
        //check if nodes are valid
        if (source == null || target == null) throw new IllegalArgumentException("One or both nodes are null");


        // test if target is reachable from source
        if (bfs.breadthFirstSearch()) {
            // trivial case (source == target)
            if (source.equals(target)) {
                path.add(source);
                return;
            }

            path.add(target);


            while (!path.contains(source)){
                Node currentNode = path.get(0);
                Node reachableNode = getNodeWithCurrentStepSizeMinusOne(currentNode);
                path.addFirst(reachableNode);
            }


        }
    }









    /**
     * Uses BFS to test if there is a way between the nodes
     *
     * Find the/a shortest Path.
     *
     * @return A List of Nodes which represent the shortest way between source and target. If there is no such way null is returned.
     */


    public List<Node> getShortestPath() {
        return path;
    }



    @Override
    public <T extends Edge> Iterator<T> getTreeEdgesIterator() {
        return null;
    }

    public void clear() {
        bfs.clear();
        path.clear();
    }


    private static Node getNodeWithCurrentStepSizeMinusOne(Node currentNode) {
        Iterable<Edge> enteringEdges = currentNode.getEachEnteringEdge();
        int currentStep = currentNode.getAttribute("step");
        for (Edge edge : enteringEdges) {
            Node nextNode = edge.getOpposite(currentNode);
            if (nextNode.hasAttribute("step")) {
                int adjacentNodeStep = edge.getOpposite(currentNode).getAttribute("step");
                if (adjacentNodeStep + 1 == currentStep) {
                    return nextNode;
                }
            }
        }
        return null;
    }
}
