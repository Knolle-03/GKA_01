package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnKruskal;
import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnPrimFH;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

public class ownKruskalOwnPrimTest extends SpanningTreeTest {


    @BeforeEach
    void setUp() { setWeightOfTestGraphEdges(); }

    @Test
    void testCorrectness() { super.testEachGraph(new OwnPrimFH(), new OwnKruskal());}

}
