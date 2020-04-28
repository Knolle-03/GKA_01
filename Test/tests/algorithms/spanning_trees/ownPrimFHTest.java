package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnPrimFH;
import org.graphstream.algorithm.Prim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

class ownPrimFHTest extends SpanningTreeTest {

    @BeforeEach
    void setUp() {
        super.setWeightOfTestGraphEdges();
    }

    @Test
    void testCorrectness() {
        super.testEachGraph(new Prim(), new OwnPrimFH());
    }
}