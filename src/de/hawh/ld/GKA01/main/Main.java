package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.eulerian.circuit.Fleury;
import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;
import de.hawh.ld.GKA01.util.ColoringType;
import de.hawh.ld.GKA01.util.Painter;
import org.graphstream.graph.Graph;

import java.util.List;


public class Main {

    public static void main(String[] args) {


        String path = "resources\\smallEulerianTestGraphs\\6._16_graph.graph";
        List<String> lines = FileReader.readLines(path);

        Graph graph = GraphFromList.populateGraph(lines, path);
        Fleury fleury = new Fleury();
        fleury.init(graph);
        fleury.compute();
        System.out.println(fleury.getEulerianTour());
        graph.addAttribute("ui.antialias");
        Painter painter = new Painter();
        graph.display();
        painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
        painter.colorGraph(fleury.getEulerianTour());


    }
}
