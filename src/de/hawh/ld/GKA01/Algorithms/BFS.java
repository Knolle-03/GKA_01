package de.hawh.ld.GKA01.Algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {


    public static boolean breadthFirstSearch (Node source, Node target) {


        source.setAttribute("isMarked", "marked");
        source.setAttribute("step", 0);

        Queue<Node> queue = new LinkedList<>();

        queue.add(source);

        while (!queue.isEmpty()) {

            source = queue.poll();

            List<Node> adjNodes = getAdjacentReachableNewlyMarkedNodes(source);
            if (adjNodes.contains(target)) return true;
            else queue.addAll(adjNodes);

        }


        return false;
    }


    private static List<Node> getAdjacentReachableNewlyMarkedNodes(Node source) {

        Iterable<? extends Edge> iterable = source.getEachLeavingEdge();

        List<Node> adjacentReachableMarkedNodes = new ArrayList<>();
        for (Edge edge : iterable) {
            Node node = edge.getOpposite(source);
            if (node.getAttribute("isMarked") != "marked"){
                node.setAttribute("isMarked", "marked");
                //attachSpriteToNodeInBFS(node, step);
                int sourceStep = source.getAttribute("step");
                node.setAttribute("step", sourceStep + 1);
                adjacentReachableMarkedNodes.add(node);
            }

        }

        return adjacentReachableMarkedNodes;
    }

}
