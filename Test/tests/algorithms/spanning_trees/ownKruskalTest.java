package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnKruskal;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

class ownKruskalTest extends SpanningTreeTest {


    @Test
    void testCorrectness() { super.testEachGraph(new org.graphstream.algorithm.Kruskal(), new OwnKruskal());}
}