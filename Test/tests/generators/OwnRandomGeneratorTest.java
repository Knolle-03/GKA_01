package tests.generators;

import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tests.generators.superclass.GeneratorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class OwnRandomGeneratorTest extends GeneratorTest {



    @ParameterizedTest
    @CsvSource({"5000,10,10,10"})
    void generatorWorks(int totalGraphCount, int numberOfEachSize, int initialNodeCount, int increment ) {

        int currentNodesCount = initialNodeCount;
        for (int i = 0; i < totalGraphCount; i += numberOfEachSize) {
            for (int j = 0; j < numberOfEachSize; j++) {
                Generator generator = new OwnRandomGenerator(currentNodesCount, currentNodesCount * 2);
                Graph graph = new SingleGraph("ownRandomGeneratorGraph", false, true);
                generator.addSink(graph);
                generator.begin();
                generator.end();
                super.isConnected(graph);
                assertEquals(currentNodesCount, graph.getNodeCount());
                assertEquals(currentNodesCount * 2, graph.getEdgeCount());
            }
            currentNodesCount += increment;
        }
    }

}
