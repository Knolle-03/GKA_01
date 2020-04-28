package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFH;
import org.graphstream.algorithm.Prim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

class PrimFHTest extends SpanningTreeTest {

    @BeforeEach
    void setUp() {
        super.setWeightOfTestGraphEdges();
    }

    @Test
    void testCorrectness() {
        super.testEachGraph(new Prim(), new PrimFH());
    }
}