package de.hawh.ld.GKA01.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShortestPath {


    /**
     * Uses BFS to test if there is a way between the nodes
     *
     * Find the/a shortest Path.
     *
     * @param source
     *            node to start from
     *
     * @param target
     *              node to get to
     *
     * @return A List of Nodes which represent the shortest way between source and target. If there is no such way null is returned.
     */

    public static List<Node> shortestPath (Node source, Node target) {
        //check if nodes are valid
        if (source == null || target == null) throw new IllegalArgumentException("One or both nodes are null");


        // test if target is reachable from source
        if (!BFS.breadthFirstSearch(source, target)){
            System.out.println("There is no path between " + source + " and " + target + ".");
            return new ArrayList<>();
        } else {
            // trivial case (source == target)
            if (source.equals(target)) {
                List<Node> list = new ArrayList<>();
                list.add(source);
                return list;
            }


            System.out.println(target + " is reachable in " + target.getAttribute("step") + (target.getAttribute("step") == Integer.valueOf(1) ?" step" : " steps")  + " from " + source + ".");

            // list of nodes used for shortest Path

            LinkedList<Node> list = new LinkedList<>();

            list.add(target);


            while (!list.contains(source)){

                Node currentNode = list.get(0);
                //if (currentNode == null) throw new NullPointerException("null node in graph: " + target.getGraph());
                // TODO:: figure out in which case currentNode can be null
                //only one node

                Node reachableNode = getNodeWithCurrentStepSizeMinusOne(currentNode);
                list.addFirst(reachableNode);
            }
            // print shortest Path to console for debugging
//            System.out.print("start: " + list.get(list.size() - 1));
//            for (int i = list.size() - 2 ; i >= 0 ; i--) {
//                System.out.print(" --> " + list.get(i));
//            }
//            System.out.println();

            return list;
        }
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
