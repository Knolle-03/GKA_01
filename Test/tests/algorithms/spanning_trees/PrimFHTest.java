package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFH;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrimFHTest extends SpanningTreeTest {

    private final org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim();
    private final PrimFH myPrim = new PrimFH();

    @BeforeEach
    void setUp() {
        generateTestGraphs();
    }

    @AfterEach
    void resetLists() {
        resetGenerators();
    }

    @Test
    void testCorrectness() {
        testEachGraph(gsPrim, myPrim);
    }



}