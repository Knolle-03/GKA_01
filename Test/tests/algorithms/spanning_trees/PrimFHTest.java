package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFH;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

class PrimFHTest extends SpanningTreeTest {

    private final org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim();
    private final PrimFH myPrim = new PrimFH();

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
        super.testEachGraph(gsPrim, myPrim);
    }



}