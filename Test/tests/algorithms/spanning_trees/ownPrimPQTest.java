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
import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import org.graphstream.algorithm.Prim;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;
import tests.util.TestGraphGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ownPrimPQTest extends SpanningTreeTest {


    private final int lowerBound = 1;
    private final int upperBound = 20;
    private final Prim gsPrim = new Prim();
    private final OwnPrimPQ myPrim = new OwnPrimPQ();
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(1_000_000, new DorogovtsevMendesGenerator(), new RandomGenerator(), new OwnRandomGenerator());
    private final List<Graph> testGraphs = testGraphGenerator.generateWeightedTestGraphs(lowerBound, upperBound);
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








































































































