package tests.algorithms.eulerian;

import de.hawh.ld.GKA01.algorithms.eulerian.circuit.Fleury;
import de.hawh.ld.GKA01.util.ColoringType;
import de.hawh.ld.GKA01.util.Painter;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import tests.algorithms.eulerian.superclasses.EulerianCircuitTest;
import tests.util.GraphReader;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmallEulerianGraphTest extends EulerianCircuitTest {


    @Test
    public void testSmallEulerianTestGraphs() {
        String eulerianGraphsPath = "Test\\testResources\\smallEulerianTestGraphs";
        List<Graph> eulerianTestGraphs = populateTestGraphList(new File(eulerianGraphsPath));
        Fleury fleury = new Fleury();
        for (Graph graph : eulerianTestGraphs) {
            fleury.init(graph);
            fleury.compute();
            assertTrue(super.edgesUsedExactlyOnce(fleury.getEulerianTour()));
            super.isEulerianCircuit(fleury.getEulerianTour(), graph);
            fleury.clear();
        }
    }


    @Test
    public void testSmallNonEulerianTestGraphs() {
        String nonEulerianGraphsPath = "Test\\testResources\\smallNonEulerianTestGraphs";
        List<Graph> nonEulerianTestGraphs = populateTestGraphList(new File(nonEulerianGraphsPath));
        Fleury fleury = new Fleury();
        for (Graph graph : nonEulerianTestGraphs) {
            try {
                fleury.init(graph);
                assertThrows(NullPointerException.class, fleury::compute);
            } catch (AssertionFailedError error) {
                graph.display();
                Painter painter = new Painter();
                painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
                painter.colorGraph(fleury.getEulerianTour());
            }
        }
    }



    public List<Graph> populateTestGraphList(final File folder) {
        return GraphReader.getGraphsInDirectory(folder);
    }

}
