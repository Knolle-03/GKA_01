package tests.generators;

import de.hawh.ld.GKA01.util.ColoringType;
import de.hawh.ld.GKA01.util.Painter;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import tests.generators.superclasses.GeneratorTest;
import tests.util.TestGraphGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RandomEulerianGeneratorTest extends GeneratorTest {

    private static final int nodeCount = 4;
    private static final int numberOfTestGraphs = 100000;
    private static final boolean doubleNodeCount = false;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(nodeCount, numberOfTestGraphs, doubleNodeCount, new RandomEulerianGenerator(nodeCount));
    private final List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();
    private final List<Graph> smallEulerianTestGraphs = new ArrayList<>();
    private final List<Graph> smallNonEulerianTestGraphs = new ArrayList<>();



    @Test
    void isEulerianTest() {
        for (Graph graph : testGraphs) {
            super.isConnected(graph);
        try {
            super.eachNodeHasEvenEdgeCount(graph);
        } catch (AssertionFailedError error) {
            try {
                for (Node node : graph) node.addAttribute("ui.label", node.getId());
                for (Edge edge : graph.getEdgeSet()) edge.addAttribute("ui.label", edge.getIndex());
                Painter painter = new Painter();
                painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
                painter.colorGraph(new LinkedList<>());
                graph.display();
                System.out.println(graph.getNodeCount());
                Thread.sleep(100000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        }
    }


    @Test
    void smallListTest() {

    }
}
