package tests.algorithms.eulerian;

import de.hawh.ld.GKA01.algorithms.eulerian_circuits.EulerianCircuitAlgorithm;
import de.hawh.ld.GKA01.algorithms.eulerian_circuits.Fleury;
import de.hawh.ld.GKA01.algorithms.eulerian_circuits.Hierholzer;
import de.hawh.ld.GKA01.algorithms.eulerian_circuits.Hierholzer2;
import de.hawh.ld.GKA01.util.Stopwatch;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
import static org.junit.jupiter.api.Assumptions.assumeTrue;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EulerianCircuitTest {



    private TestGraphGenerator testGraphGenerator;

    private static Stream<Arguments> algorithms() {
        return Stream.of(
                Arguments.of(new Fleury()),
                Arguments.of(new Hierholzer()),
                Arguments.of(new Hierholzer2()));
    }

    @BeforeAll
    void setUp() {
        testGraphGenerator = new TestGraphGenerator(1_000_000, 4 , true, new RandomEulerianGenerator());
    }


    @ParameterizedTest
    @MethodSource("algorithms")
    void testSmallEulerianTestGraphs(EulerianCircuitAlgorithm algorithm) {
        List<Graph> eulerianTestGraphs = testGraphGenerator.readTestGraphsFromFolder(new File("Test\\testResources\\smallEulerianTestGraphs"));
        for (Graph graph : eulerianTestGraphs) {
            algorithm.init(graph);
            algorithm.compute();
            assertEquals(graph.getEdgeCount(), algorithm.getEulerianCircuit().size());
            assertTrue(edgesUsedExactlyOnce(algorithm.getEulerianCircuit()));
            isEulerianCircuit(algorithm, graph);
            algorithm.clear();
        }
    }


    @ParameterizedTest
    @MethodSource("algorithms")
    void testSmallNonEulerianTestGraphs(EulerianCircuitAlgorithm algorithm) {
        List<Graph> nonEulerianTestGraphs = testGraphGenerator.readTestGraphsFromFolder(new File("Test\\testResources\\smallNonEulerianTestGraphs"));
        for (Graph graph : nonEulerianTestGraphs) {
            assertThrows(IllegalArgumentException.class,() -> algorithm.init(graph), "A non eulerian graph must not be accepted.");
        }
    }



    @ParameterizedTest
    @MethodSource("algorithms")
    void testEulerianCircuit(EulerianCircuitAlgorithm algorithm) {
        for (int i = 0; i < 1; i++) {

            Stopwatch stopwatch = new Stopwatch();
            List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();
            for (Graph graph : testGraphs) {
                stopwatch.start();
                algorithm.init(graph);
                algorithm.compute();
                stopwatch.stop();
                // test if eulerian tour and the edge count of the graph are of same size
                assertEquals(graph.getEdgeCount(), algorithm.getEulerianCircuit().size(), "The number of edges in the graph should be the same as in the eulerian circuit.");
                // test if all edges are used exactly once
                assertTrue(edgesUsedExactlyOnce(algorithm.getEulerianCircuit()), "Each edge should be in the eulerian circuit exactly once.");
                // test if the path is "walkable"
                isEulerianCircuit(algorithm, graph);
                System.out.println("Graph with " + graph.getNodeCount() + " nodes done in " + stopwatch.elapsedTime() + ".");
                System.out.println("---------------------");
                stopwatch.reset();
                algorithm.clear();
            }
        }
    }

    @Test
    @Disabled
    void testBigEulerianGraphs() {
        Hierholzer2 hierholzer2 = new Hierholzer2();
        List<Graph> bigTestGraphs = testGraphGenerator.readTestGraphsFromFolder(new File("Test\\testResources\\bigEulerianTestGraphs"));
        Stopwatch stopwatch = new Stopwatch();
        for (Graph graph : bigTestGraphs) {
            stopwatch.start();
            hierholzer2.init(graph);
            hierholzer2.compute();
            stopwatch.stop();
            // test if eulerian tour and the edge count of the graph are of same size
            assertEquals(graph.getEdgeCount(), hierholzer2.getEulerianCircuit().size(), "The number of edges in the graph should be the same as in the eulerian circuit.");
            // test if all edges are used exactly once
            assertTrue(edgesUsedExactlyOnce(hierholzer2.getEulerianCircuit()), "Each edge should be in the eulerian circuit exactly once.");
            // test if the path is "walkable"
            isEulerianCircuit(hierholzer2, graph);
            System.out.println("Graph with " + graph.getNodeCount() + " nodes done in " + stopwatch.elapsedTime() + ".");
            System.out.println("---------------------");
            stopwatch.reset();
            hierholzer2.clear();

        }

    }


    @ParameterizedTest
    @MethodSource("algorithms")
    void emptyAndSingleNodeGraph(EulerianCircuitAlgorithm algorithm) {
        Graph emptyGraph = new SingleGraph("emptyGraph");
        assertThrows(IllegalArgumentException.class, () -> algorithm.init(emptyGraph));
        emptyGraph.addNode("onlyNode");
        assertDoesNotThrow(() ->algorithm.init(emptyGraph));
        algorithm.compute();

        assertEquals(0, algorithm.getEulerianCircuit().size());
    }

    private void isEulerianCircuit(EulerianCircuitAlgorithm algorithm, Graph graph){
        Iterator<Edge> edgeIterator = algorithm.getEulerianCircuit().iterator();
        Node startNode = graph.getNode(0);
        Node currNode = startNode;



        while (edgeIterator.hasNext()) {
            Edge nextEdge = edgeIterator.next();
            assertFalse(nextEdge.hasAttribute("testingUsed"), "The next Edge should not have been used.");
            nextEdge.addAttribute("testingUsed");
            currNode = nextEdge.getOpposite(currNode);
        }

        assumeTrue(!algorithm.getClass().equals(Hierholzer2.class));
        assertSame(startNode, currNode, "The circuit should end back at the start.");
    }


    private boolean edgesUsedExactlyOnce(List<Edge> circuit) {
        Set<Edge> hashSet = new HashSet<>(circuit);
        return hashSet.size() == circuit.size();
    }


}
