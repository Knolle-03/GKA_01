package tests.algorithms.spanning_trees;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpanningTreeTest {
    protected static final List<Generator> generators = new ArrayList<>();
    protected static final int WEIGHT_MAX = 20;
    protected static final int nodeCount = 1_000_000;
    protected static final Random random = new Random();
    protected static final List<Graph> testGraphs = new ArrayList<>();


    void generateTestGraphs() {
        List<Generator> generatorList = Arrays.asList(new BarabasiAlbertGenerator(), new DorogovtsevMendesGenerator(), new RandomGenerator(5));
        generators.addAll(generatorList);
        for (Generator generator : generators) {
            //System.out.println(generator.toString());
            Graph graph = new MultiGraph("" + generator);
            generator.addSink(graph);
            generator.begin();
            for (int i = 0; i < nodeCount; i++) {
                generator.nextEvents();
                //if (i % 50_000 == 0) System.out.println(i + " nodes created.");
            }
            generator.end();
            int counter = 0;
            for (Edge edge : graph.getEachEdge()) {
                edge.addAttribute("weight", random.nextInt(WEIGHT_MAX));
                //if (counter++ % 50_000 == 0) System.out.println(counter + " edges weighted.");
            }

            testGraphs.add(graph);

        }
    }

    void resetGenerators() {
        generators.clear();
        testGraphs.clear();
    }


    void testEachGraph(AbstractSpanningTree referenceAST , AbstractSpanningTree ownAST) {

        for (Graph graph : testGraphs) {
            referenceAST.init(graph);
            referenceAST.compute();

            ownAST.init(graph);
            ownAST.compute();

            int referenceWeight = 0;
            for (Edge edge : referenceAST.getTreeEdges()) {
                int weight = edge.getAttribute("weight");
                referenceWeight += weight;
            }

            int ownWeight = 0;
            for (Edge edge : ownAST.getTreeEdges()) {
                int weight = edge.getAttribute("weight");
                ownWeight += weight;
            }




            assertEquals(referenceWeight, ownWeight);

            referenceAST.clear();
            ownAST.clear();
        }

    }

}
