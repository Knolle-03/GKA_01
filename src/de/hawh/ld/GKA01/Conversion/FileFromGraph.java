package de.hawh.ld.GKA01.Conversion;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class FileFromGraph {

    public static List<String> linesFromGraph(Graph graph) {

        List<String> lines = new ArrayList<>();




        for (Edge edge : graph.getEachEdge()) {
            if (edge.isDirected()) {
                lines.add("# directed;");
                break;
            }
        }

        for (Edge edge : graph.getEachEdge()) {

            Node node1 = edge.getNode0();
            Node node2 = edge.getNode1();





            String line;
            String part1 = node1.toString();
            if (node1.hasAttribute("attributeOne")) {
                part1 = part1 + ":" + edge.getNode0().getAttribute("attributeOne");
            }

            String part2 = node2.toString();
            if (node2.hasAttribute("attributeTwo")) {
                part2 = part2 + ":" + edge.getNode1().getAttribute("attributeTwo");
            }

            if (edge.hasAttribute("edge.name")) {
                part2 = part2 + " :: " + edge.getAttribute("edge.name");
            }

            if (edge.getAttribute("weight") != null) {
                part2 = part2 + " :: " + edge.getAttribute("weight");
            }

            line = part1 + "," + part2 + ";";
            lines.add(line);
        }


        return lines;
    }




}
