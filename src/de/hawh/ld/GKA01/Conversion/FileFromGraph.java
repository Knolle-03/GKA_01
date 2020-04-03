package de.hawh.ld.GKA01.Conversion;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class FileFromGraph {

    public static List<String> getFileLines (Graph graph) {

        List<String> lines = new ArrayList<>();

        for (Edge edge : graph.getEachEdge()) {
            if (edge.isDirected()) {
                lines.add("# directed;");
                break;
            }
        }


        for (Edge edge : graph.getEachEdge()) {

            Node node0 = edge.getNode0();
            Node node1 = edge.getNode1();

            StringBuilder line;
            StringBuilder part0 = new StringBuilder(node0.toString());
            StringBuilder part1 = new StringBuilder(node1.toString());

            if (node0.hasAttribute("attr1")) {
                part0.append(":").append(node0.getAttribute("attr1").toString());
            }

            if (node1.hasAttribute("attr2")) {
                part1.append(":").append(node0.getAttribute("attr2").toString());
            }

            if (edge.hasAttribute("name")) {
                part1.append("(").append(edge.getAttribute("name").toString()).append(")");
            }

            if (edge.getAttribute("weight") != null) {
                part1.append(" :: ").append(edge.getAttribute("weight").toString());
            }

            line = part0.append(",").append(part1).append(";");

            lines.add(line.toString());

        }


        return lines;

    }






}
