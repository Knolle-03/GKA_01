package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

class KruskalTest extends SpanningTreeTest {


    private final org.graphstream.algorithm.Kruskal gsKruskal = new org.graphstream.algorithm.Kruskal();
    private final Kruskal myKruskal = new Kruskal();

    @BeforeEach
    void setUp() { generateWeightedTestGraphs(); }

//    @AfterEach
//    void resetLists() { resetGenerators(); }

    @Test
    void testCorrectness() { super.testEachGraph(gsKruskal, myKruskal);}
}