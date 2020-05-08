package tests.generators;

import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;
import tests.TestGraphGenerator;
import tests.generators.superclasses.GeneratorTest;

import java.util.List;


public class RandomEulerianGeneratorTest extends GeneratorTest {

    private static final int nodeCount = 1_000;
    private static final int numberOfTestGraphs = 13;
    private static final boolean doubleNodeCount = true;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(nodeCount, numberOfTestGraphs, doubleNodeCount, new RandomEulerianGenerator(nodeCount));
    private final List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();


    @Test
    void isEulerianTest() {
        for (Graph graph : testGraphs) {
            super.isConnected(graph);
            super.eachNodeHasEvenEdgeCount(graph);
        }
    }
}
