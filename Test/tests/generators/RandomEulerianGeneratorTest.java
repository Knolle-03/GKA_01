package tests.generators;

import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;
import tests.generators.superclasses.GeneratorTest;
import tests.util.TestGraphGenerator;

import java.util.ArrayList;
import java.util.List;


public class RandomEulerianGeneratorTest extends GeneratorTest {

    private static final int nodeCount = 7;
    private static final int numberOfTestGraphs = 1_000;
    private static final boolean doubleNodeCount = false;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(nodeCount, numberOfTestGraphs, doubleNodeCount, new RandomEulerianGenerator(nodeCount));
    private final List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();
    private final List<Graph> smallEulerianTestGraphs = new ArrayList<>();
    private final List<Graph> smallNonEulerianTestGraphs = new ArrayList<>();



    @Test
    void isEulerianTest() {
        for (Graph graph : testGraphs) {
            super.isConnected(graph);
            super.eachNodeHasEvenEdgeCount(graph);
        }
    }


    @Test
    void smallListTest() {

    }
}
