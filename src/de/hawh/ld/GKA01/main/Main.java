package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.conversion.ListFromGraph;
import de.hawh.ld.GKA01.io.FileWriter;
import de.hawh.ld.GKA01.util.Stopwatch;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        int nodeCount = 8_000_000;
        for (int i = 0; i < 2 ; i++) {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();
            Generator generator = new RandomEulerianGenerator(nodeCount);
            Graph graph = new SingleGraph("bigEulerianGraph (" + nodeCount + " nodes)"   , false, true);
            generator.addSink(graph);
            generator.begin();
            generator.end();
            nodeCount += 1;
            stopwatch.stop();
            System.out.println("Graph with " + graph.getNodeCount() + " nodes created in " + stopwatch.elapsedTime() + ".");
            stopwatch.reset();
            stopwatch.resetRounds();
            //graph.display();
//
//            Hierholzer hierholzer = new Hierholzer();
//            hierholzer.init(graph);
//            hierholzer.compute();
//
//            System.out.println(hierholzer.getEulerianCircuit());
//            Painter painter = new Painter();
//            painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
//            painter.colorGraph(hierholzer.getEulerianCircuit());
            stopwatch.start();
            String path = "Test\\testResources\\bigEulerianTestGraphs\\" + graph.getId() + ".graph";
            List<String> lines = ListFromGraph.getFileLines(graph);
            FileWriter.writeLines(lines, path);
            stopwatch.stop();
            System.out.println("Graph with " + graph.getNodeCount() + " nodes written to file in " + stopwatch.elapsedTime());
            stopwatch.resetRounds();
            stopwatch.reset();

        }

    }
}
