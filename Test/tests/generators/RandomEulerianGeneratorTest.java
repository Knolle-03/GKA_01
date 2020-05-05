package tests.generators;

import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;
import tests.TestGraphGenerator;
import tests.generators.superclasses.GeneratorTest;

import java.util.List;


public class RandomEulerianGeneratorTest extends GeneratorTest {

    private static final int loops = 100_000;
    private static final int nodeCount = 12;
    private static final int numberOfTestGraphs = 50;
    private static final boolean doubleNodeCount = true;
    private static final int edgeCount = 2_000_000;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(nodeCount, numberOfTestGraphs, doubleNodeCount, new RandomEulerianGenerator(nodeCount));
    private final List<Graph> testGraphs = testGraphGenerator.generateNonWeightedTestGraphs();


    private static final Graph graph = new SingleGraph("testGraph", false, true);

    @Test
    void isEulerianTest() {
        for (Graph graph : testGraphs) {
            super.isConnected(graph);
            super.eachNodeHasEvenEdgeCount(graph);
        }
    }
}
