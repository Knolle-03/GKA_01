package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.eulerian.circuit.Hierholzer;
import de.hawh.ld.GKA01.conversion.ListFromGraph;
import de.hawh.ld.GKA01.io.FileWriter;
import de.hawh.ld.GKA01.util.ColoringType;
import de.hawh.ld.GKA01.util.Painter;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        for (int i = 0; i < 13 ; i++) {
            Generator generator = new RandomEulerianGenerator();
            Graph graph = new SingleGraph("nonEulerianGraph(" + (i + 3) + " nodes)" , false, true);
            generator.addSink(graph);
            generator.begin();
            generator.end();

            Hierholzer hierholzer = new Hierholzer();
            hierholzer.init(graph);
            hierholzer.compute();

            graph.display();

            Painter painter = new Painter();
            painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
            painter.colorGraph(hierholzer.getEulerianTour());


            String path = "Test\\testResources\\smallNonEulerianTestGraphs\\" + graph.getId() + ".graph";
            List<String> lines = ListFromGraph.getFileLines(graph);
            FileWriter.writeLines(lines, path);

        }






//
//        Graph graph = GraphFromList.populateGraph(lines, path);
//        Fleury fleury = new Fleury();
//        fleury.init(graph);
//        fleury.compute();
//        System.out.println(fleury.getEulerianTour());
//        graph.addAttribute("ui.antialias");
//        Painter painter = new Painter();
//        graph.display();
//        painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
//        painter.colorGraph(fleury.getEulerianTour());


    }
}
