package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal2;
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Kruskal2Test {


    private final org.graphstream.algorithm.Kruskal gsKruskal = new org.graphstream.algorithm.Kruskal();
    private final Kruskal2 myKruskal2 = new Kruskal2();
    private static final Generator[] generators = new Generator[] {new BarabasiAlbertGenerator(), new ChvatalGenerator(), new DorogovtsevMendesGenerator(), new RandomGenerator(5),
            new PetersenGraphGenerator()};
    private static final int WEIGHT_MAX = 20;
    private static final int nodeCount = 1_000_000;

    private static final List<Graph> testGraphs = new ArrayList<>();


    @BeforeEach
    void setUp() {

        for (Generator generator : generators) {

//          System.out.println("initializing graph...");
            Graph graph = new MultiGraph("testGraph");
            generator.addSink(graph);
            generator.begin();
            for (int i = 0; i < nodeCount; i++)
                generator.nextEvents();
            generator.end();

            Random random = new Random();

            for (Edge edge : graph.getEachEdge()) {
                edge.addAttribute("weight", random.nextInt(WEIGHT_MAX));
            }

            testGraphs.add(graph);

        }


    }

    @Test
    void testCorrectness() {

        for (Graph graph : testGraphs) {
            gsKruskal.init(graph);
            gsKruskal.compute();

            myKruskal2.init(graph);
            myKruskal2.compute();


            assertEquals(gsKruskal.getTreeWeight(), myKruskal2.getTreeWeight());
            gsKruskal.clear();
            myKruskal2.clear();
        }
    }

}