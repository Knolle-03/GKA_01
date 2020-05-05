package tests.generators;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Iterator;

public class GeneratorTest {



    boolean isConnected(Graph graph) {
        Iterator<Node> graphIterator = graph.getNode(0).getBreadthFirstIterator(false);
        int visitedNodes = 0;
        while (graphIterator.hasNext()) {
            graphIterator.next();
            visitedNodes++;
        }

        return visitedNodes == graph.getNodeCount();
    }

    boolean eachNodeHasEvenEdgeCount(Graph graph) {
        boolean allEven = true;
        for (Node node : graph) {
            if (node.getDegree() % 2 != 0) {
                allEven = false;
                break;
            }
        }

        return allEven;
    }



}
