package tests.algorithms.eulerian;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomEulerianGeneratorTest {


    @Test
    void hasEulerianCircuit(Graph graph) {
        boolean hasCircuit = true;
        if (graph.getEdge(0).isDirected()) {
            for (Node node : graph) {
                if (node.getInDegree() != node.getOutDegree()){
                    hasCircuit = false;
                    break;
                }
            }
        } else {
            for (Node node : graph) {
                if (node.getDegree() % 2 != 0) {
                    hasCircuit = false;
                    break;
                }
            }
        }
        assertTrue(hasCircuit);
    }

}
