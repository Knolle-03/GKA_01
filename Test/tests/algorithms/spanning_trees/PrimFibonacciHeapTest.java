package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFibonacciHeap;
import de.hawh.ld.GKA01.util.Stopwatch;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimFibonacciHeapTest {

    private final org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim();
    private final PrimFibonacciHeap myPrim = new PrimFibonacciHeap();

    private final Stopwatch stopwatch = new Stopwatch();
    private static final int REPS = 10;
    private static final int WEIGHT_MAX = 20;


    private static final List<Graph> testGraphs = new ArrayList<>();

    @BeforeAll
    static void setUp() {

        int nodeCount = 500_000;

        for (int j = 0; j < REPS; j++) {
//            System.out.println("initializing graph...");
            Graph graph = new MultiGraph("rndGraph");
            Generator gen = new DorogovtsevMendesGenerator();
            gen.addSink(graph);
            gen.begin();
            for (int i = 0; i < nodeCount; i++)
                gen.nextEvents();
            gen.end();


            Random random = new Random();

            for (Edge edge : graph.getEachEdge()) {
                edge.addAttribute("weight", random.nextInt(WEIGHT_MAX));
            }

            testGraphs.add(graph);

            System.out.println("built graph " + (j + 1));


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