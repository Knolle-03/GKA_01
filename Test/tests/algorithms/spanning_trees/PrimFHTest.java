package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFH;
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

class PrimFHTest {

    private final org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim();
    private final PrimFH myPrim = new PrimFH();
    private static final Generator[] generators = new Generator[] {new BarabasiAlbertGenerator(), new ChvatalGenerator(), new DorogovtsevMendesGenerator(), new RandomGenerator(5)};
    private static final int nodeCount = 100_000;
    private static final int WEIGHT_MAX = 20;


    private static final List<Graph> testGraphs = new ArrayList<>();

    @BeforeEach
    void setUp() {

        for (Generator generator : generators) {
            System.out.println("initializing " + generator.toString() + " graph...");
            Graph graph = new MultiGraph("testGraph");
            generator.addSink(graph);
            generator.begin();
            for (int i = 0; i < nodeCount; i++){
                generator.nextEvents();
                if (i % 1000 == 0) System.out.println(i);
            }
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
            gsPrim.init(graph);
            gsPrim.compute();

            myPrim.init(graph);
            myPrim.compute();

            assertEquals(gsPrim.getTreeWeight(), myPrim.getTreeWeight());

            gsPrim.clear();
            myPrim.clear();
        }
    }



}