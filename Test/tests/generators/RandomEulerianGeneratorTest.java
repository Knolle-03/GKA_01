package tests.generators;

import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;
import tests.generators.superclass.GeneratorTest;
import tests.util.TestGraphGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class RandomEulerianGeneratorTest extends GeneratorTest {



    @Test
    void isEulerianTest() {

        TestGraphGenerator testGraphGenerator = new TestGraphGenerator(4, 2_500, 1, new RandomEulerianGenerator());
        List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();

        for (Graph graph : testGraphs) {
            super.isConnected(graph);
            super.eachNodeHasEvenEdgeCount(graph);
        }
    }

    @Test
    void tooFewNodesTest() {
        assertThrows(IllegalArgumentException.class, () -> new RandomEulerianGenerator(2));
    }


}
