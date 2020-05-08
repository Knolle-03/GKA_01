package tests.algorithms.eulerian;

import de.hawh.ld.GKA01.algorithms.eulerian.circuit.Fleury;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;
import tests.TestGraphGenerator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



public class EulerianCircuitTest {

    private static final int initialNodeCount = 16;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(initialNodeCount, 100, true, new RandomEulerianGenerator());
    private List<Graph> testGraphs;


    @Test
    void testEulerianCircuit() {
        for (int i = 0; i < 10; i++) {
            testGraphs = testGraphGenerator.generateEulerianGraphs();
            Fleury fleury = new Fleury();
            for (Graph graph : testGraphs) {
                fleury.init(graph);
                fleury.compute();
                // test if eulerian tour and the edge count of the graph are of same size
                assertEquals(graph.getEdgeCount(), fleury.getEulerianTour().size());
                // test if all edges are used exactly once
                assertTrue(edgesUsedExactlyOnce(fleury.getEulerianTour()));
                // test if the path is "walkable"
                assertTrue(isEulerTour(fleury.getEulerianTour(), graph));
                System.out.println("Graph with " + graph.getNodeCount() + " nodes done.");
                fleury.clear();
            }
        }

    }


    private boolean isEulerTour(List<Edge> circuit, Graph graph){
        Iterator<Edge> edgeIterator = circuit.iterator();
        Node startNode = graph.getNode(0);
        Node currNode = startNode;
        while (edgeIterator.hasNext()) {
            Edge nextEdge = edgeIterator.next();
            nextEdge.addAttribute("used");
            Node formerCurrNode = currNode;
            currNode = nextEdge.getOpposite(currNode);
            assertTrue(formerCurrNode.hasEdgeToward(currNode));
        }
        assertSame(startNode, currNode);


        List<Edge> nonUsedEdges = new ArrayList<>();

        for (Edge edge : graph.getEdgeSet()) {
            if (!edge.hasAttribute("used")) nonUsedEdges.add(edge);
        }

        return 0 == nonUsedEdges.size();
    }

    private boolean edgesUsedExactlyOnce(List<Edge> circuit) {
        Set<Edge> hashSet = new HashSet<>(circuit);
        return hashSet.size() == circuit.size();
    }

}
