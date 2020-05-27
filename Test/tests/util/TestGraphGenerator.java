package tests.util;

import de.hawh.ld.GKA01.util.Stopwatch;
import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestGraphGenerator {




    private final Random rng = new Random();
    private final int nodeCount;
    private int numberOfEachType = 1;
    private boolean doubleNodeCountInLoop = false;
    private final List<Generator> generators;
    private int increment;

    public TestGraphGenerator(int nodeCount, Generator ... generators) {
        this.nodeCount = nodeCount;
        this.generators = Arrays.asList(generators);
    }

    public TestGraphGenerator(int nodeCount,int numberOfEachType, Generator ... generators) {
        this(nodeCount, generators);
        this.numberOfEachType = numberOfEachType;
    }

    public TestGraphGenerator(int nodeCount, int numberOfEachType, boolean doubleNodeCountInLoop, Generator ... generators) {
        this(nodeCount, numberOfEachType, generators);
        this.doubleNodeCountInLoop = doubleNodeCountInLoop;
    }

    public TestGraphGenerator(int nodeCount, int numberOfEachType, int increment, Generator ... generators) {
        this(nodeCount, numberOfEachType, generators);
        this.increment = increment;
    }

    public List<Graph> generateEulerianGraphs() {
        List<Graph> testGraphs = new ArrayList<>();
        Stopwatch stopwatch = new Stopwatch();
        int currentNodeCount = nodeCount;
        for (int i = 0; i < numberOfEachType; i++) {
            stopwatch.start();
            Generator generator = new RandomEulerianGenerator(currentNodeCount);
            Graph graph = new SingleGraph("testGraph", false, true);
            generator.addSink(graph);
            generator.begin();
            generator.end();
            testGraphs.add(graph);
            stopwatch.stop();
            //System.out.println("graph with " + currentNodeCount + " nodes created in " + stopwatch.elapsedTime() + ".");
            stopwatch.reset();

            if (doubleNodeCountInLoop) currentNodeCount *= 2;
            else if (increment != 0) currentNodeCount += increment;
        }

        return testGraphs;
    }



    public List<Graph> generateNonWeightedTestGraphs() {
        List<Graph> testGraphs = new ArrayList<>();
        for (Generator generator : generators) {

            int currentNodeCount = nodeCount;
            for (int i = 0; i < numberOfEachType; i++) {

                if (generator.getClass() == OwnRandomGenerator.class) generator = new OwnRandomGenerator(nodeCount, nodeCount * 2);

                Graph graph = new SingleGraph("" + generator, false, true);
                generator.addSink(graph);
                generator.begin();
                for (int j = 0; j < currentNodeCount; j++) {
                    if (!generator.nextEvents()) break;
                }
                generator.end();
                testGraphs.add(graph);
                System.out.println("Graph with " + currentNodeCount + " nodes created.");
                if (doubleNodeCountInLoop) currentNodeCount *= 2;
                else if (increment != 0) currentNodeCount += increment;
            }
        }

        return testGraphs;
    }


    public List<Graph> generateWeightedTestGraphs(int lowerBound, int upperBound) {
        List<Graph> testGraphs = generateNonWeightedTestGraphs();
        for (Graph graph : testGraphs) {
            for (Edge edge : graph.getEdgeSet()) {
                edge.addAttribute("weight" , rng.nextInt(upperBound - lowerBound) + lowerBound);
            }
        }

        return testGraphs;
    }

    public List<Graph> readTestGraphsFromFolder(File folder) {
        return GraphReader.getGraphsInDirectory(folder);
    }



}
