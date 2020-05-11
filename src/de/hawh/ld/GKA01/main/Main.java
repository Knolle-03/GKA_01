package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.eulerian_circuits.Hierholzer;
import de.hawh.ld.GKA01.conversion.ListFromGraph;
import de.hawh.ld.GKA01.io.FileWriter;
import de.hawh.ld.GKA01.util.ColoringType;
import de.hawh.ld.GKA01.util.Painter;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        for (int i = 3; i < 20 ; i++) {
            Generator generator = new RandomEulerianGenerator(i);
            Graph graph = new SingleGraph("eulerianGraph_" + (i - 2)  , false, true);
            generator.addSink(graph);
            generator.begin();
            generator.end();
            graph.display();

//
//            Hierholzer hierholzer = new Hierholzer();
//            hierholzer.init(graph);
//            hierholzer.compute();
//
//            System.out.println(hierholzer.getEulerianCircuit());
//            Painter painter = new Painter();
//            painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
//            painter.colorGraph(hierholzer.getEulerianCircuit());


            String path = "Test\\testResources\\smallEulerianTestGraphs\\" + graph.getId() + ".graph";
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
