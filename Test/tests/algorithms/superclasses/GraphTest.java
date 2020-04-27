package tests.algorithms.superclasses;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GraphTest {


    protected static final List<Graph> testGraphs = new ArrayList<>();
    protected static final List<Generator> generators = new ArrayList<>();
    protected static final Random random = new Random();
    protected static final int nodeCount = 100_000;


    public void generateNonWeightedTestGraphs() {
        if (testGraphs.size() == 0) {
            List<Generator> generators = Arrays.asList(new DorogovtsevMendesGenerator(), new RandomGenerator(5));

            for (Generator generator : generators) {
                System.out.println(generator.toString());
                Graph graph = new MultiGraph("" + generator);
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
    }

//    public void resetGenerators() {
//        generators.clear();
//        testGraphs.clear();
//    }

}
