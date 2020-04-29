package tests.algorithms.superclasses;

import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GraphTest {

    protected static final List<Graph> testGraphs = new ArrayList<>();
    protected static final Random random = new Random();
    protected static final int nodeCount = 100_000;
    protected static final List<Generator> generators = Arrays.asList(new DorogovtsevMendesGenerator(), new RandomGenerator(5));


    public static void generateNonWeightedTestGraphs() {
        for (Generator generator : generators) {
            Graph graph = new SingleGraph("" + generator);
            generator.addSink(graph);
            generator.begin();
            for (int i = 0; i < nodeCount; i++) {
                generator.nextEvents();
                if (i % 50_000 == 0) System.out.println(i + " nodes created.");
            }
            generator.end();

            testGraphs.add(graph);
        }
    }



    public static void generateEulerianTestGraphs() {
        Generator generator = new RandomEuclideanGenerator();
        Graph graph = new SingleGraph("" + generator);
        generator.addSink(graph);
        generator.begin();
        for (int i = 0; i < nodeCount; i++) {
            generator.nextEvents();
            if (i % 50_000 == 0) System.out.println(i + " nodes created.");
        }
        generator.end();
        testGraphs.add(graph);
    }

}
