package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimPQ;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimPQTest extends SpanningTreeTest {

    private final org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim();
    private final PrimPQ myPrim = new PrimPQ();

    @BeforeEach
    void setUp() {
        super.generateWeightedTestGraphs();
    }

//    @AfterEach
//    void resetLists() {
//        super.resetGenerators();
//    }

    @Test
    void testCorrectness() {

        for (Graph graph : testGraphs) {
            if (graph.toString().contains("RandomGenerator")) continue;
            gsPrim.init(graph);
            gsPrim.compute();

            myPrim.init(graph);
            myPrim.compute();

            assertEquals(gsPrim.getTreeWeight(), myPrim.getTreeWeight(), "" + graph + " done");
            gsPrim.clear();
            myPrim.clear();
        }
    }


}








































































































