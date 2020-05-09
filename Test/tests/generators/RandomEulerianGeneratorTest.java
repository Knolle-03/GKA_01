package tests.generators;

import de.hawh.ld.GKA01.util.ColoringType;
import de.hawh.ld.GKA01.util.Painter;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import tests.generators.superclasses.GeneratorTest;
import tests.util.TestGraphGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RandomEulerianGeneratorTest extends GeneratorTest {

    private static final int nodeCount = 40;
    private static final int numberOfTestGraphs = 100_000;
    private static final boolean doubleNodeCount = false;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(nodeCount, numberOfTestGraphs, doubleNodeCount, new RandomEulerianGenerator(nodeCount));
    private final List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();
    private final List<Graph> smallEulerianTestGraphs = new ArrayList<>();
    private final List<Graph> smallNonEulerianTestGraphs = new ArrayList<>();



    @Test
    void isEulerianTest() {
        for (Graph graph : testGraphs) {
            super.isConnected(graph);
//        try {
            super.eachNodeHasEvenEdgeCount(graph);
//        } catch (AssertionFailedError error) {
//            try {
//                Painter painter = new Painter();
//                painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
//                painter.colorGraph(new LinkedList<>());
//                graph.display();
//
//                Thread.sleep(100000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


        }
    }


    @Test
    void smallListTest() {

    }
}
