package tests.generators;

import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomEulerianGeneratorTest {

    private static final int loops = 100000;
    private static int initialNodeCount = 10;
    private static final Graph graph = new SingleGraph("testGraph", false, true);

    @Test
    void isEulerianTest() {
        for (int i = 0; i < loops; i++) {
            Generator generator = new RandomEulerianGenerator(initialNodeCount);
            generator.addSink(graph);
            generator.begin();
            generator.end();

            boolean allEven = true;
            for (Node node : graph) {
                if (node.getDegree() % 2 != 0) {
                    allEven = false;
                    break;
                }
            }
            //System.out.println("currently " + initialNodeCount + " nodes");
            System.out.print("connected: ");
            assertTrue(isConnected());
            System.out.println();

            System.out.print("all even: ");
            assertTrue(allEven);
            System.out.println();
            //System.out.println(i + ". run with " + initialNodeCount + " nodes completed");
            //initialNodeCount += 1;
            graph.clear();
        }
    }

    boolean isConnected() {
        Iterator<Node> graphIterator = RandomEulerianGeneratorTest.graph.getNode(0).getBreadthFirstIterator(false);
        int visitedNodes = 0;
        while (graphIterator.hasNext()) {
            graphIterator.next();
            visitedNodes++;
        }
        return visitedNodes == RandomEulerianGeneratorTest.graph.getNodeCount();
    }


}
