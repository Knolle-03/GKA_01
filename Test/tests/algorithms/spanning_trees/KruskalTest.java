package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

class KruskalTest extends SpanningTreeTest {

    @BeforeEach
    void setUp() { setWeightOfTestGraphEdges(); }

    @Test
    void testCorrectness() { super.testEachGraph(new org.graphstream.algorithm.Kruskal(), new Kruskal());}
}