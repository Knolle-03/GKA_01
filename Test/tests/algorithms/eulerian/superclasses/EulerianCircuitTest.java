package tests.algorithms.eulerian.superclasses;

import de.hawh.ld.GKA01.algorithms.eulerian_circuits.EulerianCircuitAlgorithm;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import tests.util.TestGraphGenerator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



public class EulerianCircuitTest {

    private static final int initialNodeCount = 10;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(initialNodeCount, 1000, 1, new RandomEulerianGenerator());


    protected void testEulerianCircuit(EulerianCircuitAlgorithm algorithm) {
        for (int i = 0; i < 1; i++) {
            List<Graph> testGraphs = testGraphGenerator.generateEulerianGraphs();
            for (Graph graph : testGraphs) {
                algorithm.init(graph);
                algorithm.compute();
                // test if eulerian tour and the edge count of the graph are of same size
                assertEquals(graph.getEdgeCount(), algorithm.getEulerianCircuit().size());
                // test if all edges are used exactly once
                assertTrue(edgesUsedExactlyOnce(algorithm.getEulerianCircuit()));
                // test if the path is "walkable"
                isEulerianCircuit(algorithm.getEulerianCircuit(), graph);
                System.out.println("Graph with " + graph.getNodeCount() + " nodes done.");
                algorithm.clear();
            }
        }

    }


    protected void isEulerianCircuit(List<Edge> circuit, Graph graph){
        Iterator<Edge> edgeIterator = circuit.iterator();
        Node startNode = graph.getNode(0);
        Node currNode = startNode;

        while (edgeIterator.hasNext()) {
            Edge nextEdge = edgeIterator.next();
            assertFalse(nextEdge.hasAttribute("testingUsed"));
            nextEdge.addAttribute("testingUsed");
            currNode = nextEdge.getOpposite(currNode);
        }
        assertSame(startNode, currNode);
    }

    protected boolean edgesUsedExactlyOnce(List<Edge> circuit) {
        Set<Edge> hashSet = new HashSet<>(circuit);
        return hashSet.size() == circuit.size();
    }

}
