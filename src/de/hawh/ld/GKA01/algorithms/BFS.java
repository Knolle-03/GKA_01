package de.hawh.ld.GKA01.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {


    /**
     * Test if there is a way from source to target
     *
     * @param source
     *            node to start from
     *
     * @param target
     *              node to get to
     *
     * @return true if there is a way false if not.
     */


    public static boolean breadthFirstSearch (Node source, Node target) {
        //check if nodes are valid
        if (source == null || target == null) throw new NullPointerException("One or both nodes are null");


        // trivial case (source == target)
        if (source.equals(target)) {
            System.out.println("Source and target are the same vertex.");
            return true;
        }

        // mark source node as visited
        source.setAttribute("isMarked", "marked");
        source.setAttribute("step", 0);

        Queue<Node> queue = new LinkedList<>();

        queue.add(source);

        while (!queue.isEmpty()) {

            Node currentNode = queue.poll();
            // get next chunk of nodes
            List<Node> adjNodes = getAdjacentReachableNewlyMarkedNodes(currentNode);
            // target found
            if (adjNodes.contains(target)) return true;
            // keep looking
            else queue.addAll(adjNodes);

        }


        return false;
    }


    private static List<Node> getAdjacentReachableNewlyMarkedNodes(Node source) {
        // all "usable" edges
        Iterable<? extends Edge> iterable = source.getEachLeavingEdge();

        List<Node> adjacentReachableMarkedNodes = new ArrayList<>();
        //each reachable Node
        for (Edge edge : iterable) {
            Node node = edge.getOpposite(source);
            // if not marked mark the node to avoid visiting a node twice
            if (node.getAttribute("isMarked") != "marked"){
                node.setAttribute("isMarked", "marked");
                // set number of steps to reach that node
                int sourceStep = source.getAttribute("step");
                node.setAttribute("step", sourceStep + 1);
                adjacentReachableMarkedNodes.add(node);
            }

        }

        return adjacentReachableMarkedNodes;
    }

}
