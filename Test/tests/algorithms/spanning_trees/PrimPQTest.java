package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimPQ;
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

class PrimPQTest {

    private final org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim();
    private final PrimPQ myPrim = new PrimPQ();
    private static final Random random = new Random();
    private static final Generator[] generators = new Generator[] {new BarabasiAlbertGenerator(), new ChvatalGenerator(), new DorogovtsevMendesGenerator(), new RandomGenerator(1),
            new PetersenGraphGenerator()};
    private static final int nodeCount = 10;
    private static final int WEIGHT_MAX = 20;


    private static final List<Graph> testGraphs = new ArrayList<>();

    @BeforeEach
    void setUp() {

        for (Generator generator : generators) {
            Graph graph = new MultiGraph(generator.toString());
            generator.addSink(graph);
            generator.begin();
            for (int i = 0; i < nodeCount; i++)
                generator.nextEvents();
            generator.end();


            for (Edge edge : graph.getEachEdge()) {
                edge.addAttribute("weight", random.nextInt(WEIGHT_MAX));
            }

            testGraphs.add(graph);

        }
    }

    @Test
    void testCorrectness() {

        for (Graph graph : testGraphs) {
            gsPrim.init(graph);
            gsPrim.compute();

            myPrim.init(graph);
            myPrim.compute();

            assertEquals(gsPrim.getTreeWeight(), myPrim.getTreeWeight());
            System.out.println(graph.toString() + " done");
            gsPrim.clear();
            myPrim.clear();
        }
    }


}