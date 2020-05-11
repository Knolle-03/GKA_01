package tests.generators.superclass;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class GeneratorTest {

    public void isConnected(Graph graph) {

        Iterator<Node> graphIterator = graph.getNode(0).getBreadthFirstIterator(false);
        int visitedNodes = 0;
        while (graphIterator.hasNext()) {
            graphIterator.next();
            visitedNodes++;
        }

        assertEquals(graph.getNodeCount(), visitedNodes);
    }

    public void eachNodeHasEvenEdgeCount(Graph graph) {
        boolean allEven = true;
        for (Node node : graph) {
            if (node.getDegree() % 2 != 0) {
                allEven = false;
                break;
            }
        }

        assertTrue(allEven);
    }

    public void isWeighted(Graph graph) {
        boolean allWeighted = true;
        for (Edge edge : graph.getEdgeSet()) {
            if (!edge.hasAttribute("weight")){
                allWeighted = false;
                break;
            }
        }

        assertTrue(allWeighted);
    }

    public void correctWeights(Graph graph, int upperBound, int lowerBound) {
        boolean allCorrect = true;
        for (Edge edge : graph.getEdgeSet()) {
            int weight = edge.getAttribute("weight");
            if (weight < lowerBound || weight >= upperBound) {
                allCorrect = false;
                break;
            }
        }

        assertTrue(allCorrect);
    }
}
