package de.hawh.ld.GKA01.Algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.*;

public class ShortestPath {

    public static void shortestPath (Node source, Node target) {
        if (!BFS.breadthFirstSearch(source, target)) System.out.println("There is no path between " + source + " and " + target + ".");

        else {
            System.out.println(target + " is reachable in " + target.getAttribute("step") + " steps");
            Deque<Node> deque = new LinkedList<>();
            Queue<Node> queue = new LinkedList<>();
            queue.add(target);
            deque.add(target);



            while (!deque.contains(source)){

                Node currentNode = queue.poll();

                List<Node> nodes = getAllNodesFromEnteringEdgesWithCurrentStepSizeMinusOne(currentNode);
                if (nodes.contains(source)) {
                    deque.add(source);
                } else {
                    deque.add(nodes.get(0));
                    queue.add(nodes.get(0));
                }
            }
            int step = 0;
            while (!deque.isEmpty()) {
                System.out.println(step++ + " : " + deque.pollLast());
            }
        }
    }


    private static List<Node> getAllNodesFromEnteringEdgesWithCurrentStepSizeMinusOne(Node node) {
        int sourceStep = node.getAttribute("step");
        Iterable<Edge> enteringEdges = node.getEachEnteringEdge();
        List<Node> nodes = new ArrayList<>();
        for (Edge edge : enteringEdges) {
            Node currentNode = edge.getOpposite(node);
            if (currentNode.hasAttribute("step")){
                int adjacentNodesStep = currentNode.getAttribute("step");
                if ( adjacentNodesStep + 1 == sourceStep) {
                    nodes.add(currentNode);
                }
            }

        }

        return nodes;
    }

}
