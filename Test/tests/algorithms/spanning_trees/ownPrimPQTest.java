package tests.algorithms.spanning_trees;


//          _   _       _                      _            __
//         | \ | |     | |                    | |          / _|
//         |  \| | ___ | |_   _ __   __ _ _ __| |_    ___ | |_
//         | . ` |/ _ \| __| | '_ \ / _` | '__| __|  / _ \|  _|
//         | |\  | (_) | |_  | |_) | (_| | |  | |_  | (_) | |
//         |_| \_|\___/ \__| | .__/ \__,_|_|   \__|  \___/|_|
//                           | |
//                           |_|
//
//          _   _                                   _
//         | | | |                                 (_)
//         | |_| |__   ___    _____  _____ _ __ ___ _ ___  ___
//         | __| '_ \ / _ \  / _ \ \/ / _ \ '__/ __| / __|/ _ \
//         | |_| | | |  __/ |  __/>  <  __/ | | (__| \__ \  __/
//         \__|_| |_|\___|  \___/_/\_\___|_|  \___|_|___/\___|


// not comparable with graphStream implementation of the Prim algorithm since this one only calculates the MST of one tree in a forest if the given graph is not a single component
// whereas graphStream calculates the sum of the MSTs in the forest

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnPrimPQ;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.SpanningTreeTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ownPrimPQTest extends SpanningTreeTest {

    private final org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim();
    private final OwnPrimPQ myPrim = new OwnPrimPQ();

    @BeforeEach
    void setUp() {
        super.setWeightOfTestGraphEdges();
    }

    @Test
    void testCorrectness() {

        for (Graph graph : testGraphs) {
            if (graph.toString().contains("RandomGenerator")) continue;
            gsPrim.init(graph);
            gsPrim.compute();

            myPrim.init(graph);
            myPrim.compute();

            assertEquals(gsPrim.getTreeWeight(), myPrim.getTreeWeight());
            gsPrim.clear();
            myPrim.clear();
        }
    }


}








































































































