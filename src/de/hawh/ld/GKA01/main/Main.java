package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.util.Stopwatch;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;



public class Main {

    public static void main(String[] args) {
        int nodeCount = 6;
        for (int i = 0; i < 999999; i++) {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();
            Generator generator = new RandomEulerianGenerator(nodeCount);
            Graph graph = new SingleGraph("bigEulerianGraph (" + nodeCount + " nodes)"   , false, true);
            generator.addSink(graph);
            generator.begin();
            generator.end();
            //nodeCount += 2;
            stopwatch.stop();
            System.out.println("Graph with " + graph.getNodeCount() + " nodes created in " + stopwatch.elapsedTime() + ".");
            stopwatch.reset();
            stopwatch.resetRounds();
            //graph.display();


        }

    }
}
