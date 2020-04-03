package de.hawh.ld.GKA01.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.*;

public class ShortestPath {

    public static List<Node> shortestPath (Node source, Node target) {
        //check if nodes are valid
        if (source == null || target == null) throw new NullPointerException("One or both nodes are null");


        // test if target is reachable from source
        if (!BFS.breadthFirstSearch(source, target)) System.out.println("There is no path between " + source + " and " + target + ".");

        else {
            // trivial case (source == target)
            if (source.equals(target)) {
                List<Node> list = new ArrayList<>();
                list.add(source);
                return list;
            }


            System.out.println(target + " is reachable in " + target.getAttribute("step") + (target.getAttribute("step") == Integer.valueOf(1) ?" step" : " steps")  + " from " + source + ".");
            List<Node> list = new ArrayList<>();
            Queue<Node> queue = new LinkedList<>();
            queue.add(target);
            list.add(target);



            while (!list.contains(source)){

                Node currentNode = queue.poll();

                List<Node> nodes = getAllNodesFromEnteringEdgesWithCurrentStepSizeMinusOne(currentNode);
                if (nodes.contains(source)) {
                    list.add(source);
                } else {
                    list.add(nodes.get(0));
                    queue.add(nodes.get(0));
                }
            }

            System.out.print("start: " + list.get(list.size() - 1));
            for (int i = list.size() - 2 ; i >= 0 ; i--) {
                System.out.print(" --> " + list.get(i));
            }
            System.out.println("\n");
            Collections.reverse(list);
            return list;
        }

        return null;
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
