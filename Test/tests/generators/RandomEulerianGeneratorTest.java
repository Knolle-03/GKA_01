package tests.generators;

import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.opentest4j.AssertionFailedError;
import tests.generators.superclass.GeneratorTest;
import tests.util.TestGraphGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RandomEulerianGeneratorTest extends GeneratorTest {



    @Test
    void isEulerianTest() {

        TestGraphGenerator testGraphGenerator = new TestGraphGenerator(3, 20, true, new RandomEulerianGenerator());
        List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();

        for (Graph graph : testGraphs) {
            try {
                super.isConnected(graph);
                super.eachNodeHasEvenEdgeCount(graph);

            } catch (AssertionFailedError error) {
                try {
                    for (Node node : graph) {
                        node.addAttribute("ui.label", node.getId());
                    }
                    System.out.println(graph.getEdgeSet());
                    graph.display();
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ParameterizedTest
    @CsvSource({"-1", "0", "2"})
    void tooFewNodesTest(int nodeCount) {
        assertThrows(IllegalArgumentException.class, () -> new RandomEulerianGenerator(nodeCount));
    }

    @Test
    void oneNodeTest() {
        assertDoesNotThrow(() -> new RandomEulerianGenerator(1));
    }
}
