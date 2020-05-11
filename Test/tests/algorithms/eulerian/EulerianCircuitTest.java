package tests.algorithms.eulerian;

import de.hawh.ld.GKA01.algorithms.eulerian_circuits.EulerianCircuitAlgorithm;
import de.hawh.ld.GKA01.algorithms.eulerian_circuits.Hierholzer;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.util.TestGraphGenerator;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EulerianCircuitTest {



    private TestGraphGenerator testGraphGenerator;

    private static Stream<Arguments> algorithms() {
        return Stream.of(
                //Arguments.of(new Fleury()),
                Arguments.of(new Hierholzer()));
    }

    @BeforeAll
    void setUp() {
        testGraphGenerator = new TestGraphGenerator(50_000, 5, true, new RandomEulerianGenerator());
    }


    @ParameterizedTest
    @MethodSource("algorithms")
    public void testSmallEulerianTestGraphs(EulerianCircuitAlgorithm algorithm) {
        List<Graph> eulerianTestGraphs = testGraphGenerator.readTestGraphsFromFolder(new File("Test\\testResources\\smallEulerianTestGraphs"));
        for (Graph graph : eulerianTestGraphs) {
            algorithm.init(graph);
            algorithm.compute();
            assertEquals(graph.getEdgeCount(), algorithm.getEulerianCircuit().size());
            assertTrue(edgesUsedExactlyOnce(algorithm.getEulerianCircuit()));
            isEulerianCircuit(algorithm.getEulerianCircuit(), graph);
            algorithm.clear();
        }
    }


    @ParameterizedTest
    @MethodSource("algorithms")
    public void testSmallNonEulerianTestGraphs(EulerianCircuitAlgorithm algorithm) {
        List<Graph> nonEulerianTestGraphs = testGraphGenerator.readTestGraphsFromFolder(new File("Test\\testResources\\smallNonEulerianTestGraphs"));
        for (Graph graph : nonEulerianTestGraphs) {
            assertThrows(IllegalArgumentException.class,() -> algorithm.init(graph), "A non eulerian graph must not be accepted.");
        }
    }



    @ParameterizedTest
    @MethodSource("algorithms")
    protected void testEulerianCircuit(EulerianCircuitAlgorithm algorithm) {
        for (int i = 0; i < 1; i++) {
            List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();
            for (Graph graph : testGraphs) {
                algorithm.init(graph);
                algorithm.compute();
                // test if eulerian tour and the edge count of the graph are of same size
                assertEquals(graph.getEdgeCount(), algorithm.getEulerianCircuit().size(), "The number of edges in the graph should be the same as in the eulerian circuit.");
                // test if all edges are used exactly once
                assertTrue(edgesUsedExactlyOnce(algorithm.getEulerianCircuit()), "Each edge should be in the eulerian circuit exactly once.");
                // test if the path is "walkable"
                isEulerianCircuit(algorithm.getEulerianCircuit(), graph);
                System.out.println("Graph with " + graph.getNodeCount() + " nodes done.");
                algorithm.clear();
            }
        }

    }


    private void isEulerianCircuit(List<Edge> circuit, Graph graph){
        Iterator<Edge> edgeIterator = circuit.iterator();
        Node startNode = graph.getNode(0);
        Node currNode = startNode;

        while (edgeIterator.hasNext()) {
            Edge nextEdge = edgeIterator.next();
            assertFalse(nextEdge.hasAttribute("testingUsed"), "The next Edge was not used yet.");
            nextEdge.addAttribute("testingUsed");
            currNode = nextEdge.getOpposite(currNode);
        }
        assertSame(startNode, currNode, "The circuit ends back at the start.");
    }

    private boolean edgesUsedExactlyOnce(List<Edge> circuit) {
        Set<Edge> hashSet = new HashSet<>(circuit);
        return hashSet.size() == circuit.size();
    }
}
