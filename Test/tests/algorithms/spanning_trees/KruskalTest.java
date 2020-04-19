package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal;
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

class KruskalTest {


    private final org.graphstream.algorithm.Kruskal gsKruskal = new org.graphstream.algorithm.Kruskal();
    private final Kruskal myKruskal = new Kruskal();

    private final Stopwatch stopwatch = new Stopwatch();
    private static final int REPS = 1;
    private static final int WEIGHT_MAX = 20;


    private static final List<Graph> testGraphs = new ArrayList<>();


    @BeforeAll
    static void setUp() {

        int nodeCount = 10_000_000;

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
            gsKruskal.init(graph);
            gsKruskal.compute();

            myKruskal.init(graph);
            myKruskal.compute();


            assertEquals(gsKruskal.getTreeWeight(), myKruskal.getTreeWeight());
            gsKruskal.clear();
            myKruskal.clear();
        }
    }

    @Test
    void measureTime() {

        for (Graph graph : testGraphs) {

            System.out.println("##################################################################");
            stopwatch.start();
            myKruskal.init(graph);
            myKruskal.compute();
            stopwatch.stop();
            System.out.printf("#  Kruskal took %s.                               #\n", stopwatch.elapsedTime());
            //System.out.println("KruskalTreeWeight: " + kruskal.getTreeWeight());

            myKruskal.clear();
            stopwatch.reset();



            stopwatch.start();
            org.graphstream.algorithm.Kruskal gsKruskal = new org.graphstream.algorithm.Kruskal();
            gsKruskal.init(graph);
            gsKruskal.compute();
            stopwatch.stop();
            System.out.printf("#  gsKruskal took %s.                             #\n", stopwatch.elapsedTime());
            //System.out.println("gsKruskalTreeWeight: " + gsKruskal.getTreeWeight());
            System.out.println("##################################################################");

            gsKruskal.clear();
            stopwatch.reset();
        }


        assertEquals( 1, 1);
    }



}