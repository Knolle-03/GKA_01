package tests.generators;

import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;
import tests.generators.superclasses.GeneratorTest;

import java.util.Random;

public class OwnRandomGeneratorTest extends GeneratorTest {


    private static final int upperBound = 10;
    private static final int lowerBound = 1;
    private static final int graphCount = 100;

    private static final Generator generator = new OwnRandomGenerator(10_000, 15_000, new Random(), upperBound, lowerBound);


    @Test
    void generatorWorks() {
        for (int i = 0; i < graphCount; i++) {
            Graph graph = new SingleGraph("ownRandomGeneratorGraph", false, true);
            generator.addSink(graph);
            generator.begin();
            generator.end();

            super.isConnected(graph);
            super.isWeighted(graph);
            super.correctWeights(graph, upperBound, lowerBound);
        }
    }

}
