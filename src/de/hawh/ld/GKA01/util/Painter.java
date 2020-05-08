package de.hawh.ld.GKA01.util;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
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



        graph.getNode(0).addAttribute("ui.class", "source");
        for (Edge edge : edgesToColor) {
            try {
                Thread.sleep(1000);
                if (edgesToColor.contains(edge)) edge.addAttribute("ui.class", "used");
                if (!edge.getNode0().hasAttribute("ui.class")) edge.getNode0().addAttribute("ui.class", "visited");
                if (!edge.getNode1().hasAttribute("ui.class")) edge.getNode1().addAttribute("ui.class", "visited");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Edge edge : graph.getEdgeSet()) {
            if (!edge.hasAttribute("ui.class")) {
                edge.removeAttribute("ui.label");
                edge.addAttribute("ui.class", "unused");
            }
        }

    }

    public void removeColor() {
        for (Element element : graph) {
            element.removeAttribute("ui.class");
        }
        graph.removeAttribute("ui.stylesheet");
    }
}
