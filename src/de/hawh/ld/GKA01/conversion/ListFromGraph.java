package de.hawh.ld.GKA01.conversion;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class ListFromGraph {

    /**
     * Creates a graph from a list
     *
     * @param graph
     *            Graph to be stored as a List.

     *
     * @return List of all Nodes and Edges in the format:
     *
     * ["#directed;"]
     * node1,[":"attr1],[","node2,[" :"attr2],[" ("edge")"],[" :: "weight]];
     */

    public static List<String> getFileLines (Graph graph) {

        List<String> lines = new ArrayList<>();


        if (graph.getEdgeCount() > 0 && graph.getEdge(0).isDirected()) lines.add("#directed;");

        List<Node> allNodes = new ArrayList<>();


        for (Edge edge : graph.getEachEdge()) {


            Node node0 = edge.getNode0();
            Node node1 = edge.getNode1();

            if (!allNodes.contains(node0)) allNodes.add(node0);
            if (!allNodes.contains(node1)) allNodes.add(node1);

            StringBuilder line;
            StringBuilder part0 = new StringBuilder(node0.toString());
            StringBuilder part1 = new StringBuilder(node1.toString());

            if (node0.hasAttribute("attr1")) part0.append(":").append(node0.getAttribute("attr1").toString());

            if (node1.hasAttribute("attr2")) part1.append(":").append(node0.getAttribute("attr2").toString());

            if (edge.hasAttribute("name")) part1.append(" (").append(edge.getAttribute("name").toString()).append(")");

            if (edge.getAttribute("weight") != null) part1.append(" :: ").append(edge.getAttribute("weight").toString());

            line = part0.append(",").append(part1).append(";");

            lines.add(line.toString());

        }


        // collect nodes without edges if there are any
        if (graph.getNodeCount() != allNodes.size()) {
            for (Node node : graph.getEachNode()) {
                if (node.getDegree() == 0) {
                    StringBuilder line = new StringBuilder(node.toString());
                    if (node.hasAttribute("attr1")) line.append(":").append(node.getAttribute("attr1").toString());
                    line.append(";");
                    lines.add(line.toString());
                }
            }
        }


        return lines;

    }






}
