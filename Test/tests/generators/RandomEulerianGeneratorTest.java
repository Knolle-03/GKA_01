package tests.generators;

import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomEulerianGeneratorTest extends GeneratorTest {

    private static final int loops = 1_000_000;


    private static final Graph graph = new SingleGraph("testGraph", false, true);

    @Test
    void isEulerianTest() {
        int initialNodeCount = 12;
        for (int j = initialNodeCount; j < 1_000_001 ; j++) {
            for (int i = 0; i < loops; i++) {
                Generator generator = new RandomEulerianGenerator(j);
                generator.addSink(graph);
                generator.begin();
                generator.end();
                System.out.println("Graph #" + (i + 1) + " with " + j + " nodes tested.");
                assertTrue(isConnected(graph));
                assertTrue(eachNodeHasEvenEdgeCount(graph));

                graph.clear();
            }
        }

    }




}
