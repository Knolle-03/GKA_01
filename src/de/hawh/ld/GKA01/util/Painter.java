package de.hawh.ld.GKA01.util;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.util.List;

public class Painter {

    Graph graph;
    ColoringType coloringType;

    public void attach(Graph graph, ColoringType coloringType) {
        this.graph = graph;
        this.coloringType = coloringType;
    }

    public void detach() {
        removeColor();
        this.graph = null;
        this.coloringType = null;
    }


    public void colorGraph(List<Edge> edgesToColor) {
        graph.addAttribute("ui.stylesheet", "url(" + coloringType.getPath() + ")");
        for (Edge edge : graph.getEachEdge()) {
            if (edgesToColor.contains(edge)) edge.addAttribute("ui.class", "used");
            else edge.addAttribute("ui.class", "unused");
        }
    }

    public void removeColor() {
        for (Edge edge : graph.getEachEdge()) {
            edge.removeAttribute("ui.class");
        }
        graph.removeAttribute("ui.stylesheet");
    }
}
