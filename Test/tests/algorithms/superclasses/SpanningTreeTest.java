package tests.algorithms.superclasses;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpanningTreeTest extends GraphTest {
    protected static final int WEIGHT_MAX = 20;


    public void generateWeightedTestGraphs() {
        super.generateNonWeightedTestGraphs();

        for (Graph graph : testGraphs) {
            if (!graph.getNode(0).hasAttribute("weight")){
                int counter = 0;
                for (Edge edge: graph.getEdgeSet()) {
                    if (counter++ % 50_000 == 0) System.out.println(counter + " edges weighted.");
                    edge.addAttribute("weight", random.nextInt(WEIGHT_MAX));
                }
            }
        }
    }


    public void testEachGraph(AbstractSpanningTree referenceAST , AbstractSpanningTree ownAST) {
        System.out.println(ownAST.toString());
        for (Graph graph : testGraphs) {
            referenceAST.init(graph);
            referenceAST.compute();

            ownAST.init(graph);
            ownAST.compute();

            int referenceWeight = 0;
            for (Edge edge : referenceAST.getTreeEdges()) {
                int weight = edge.getAttribute("weight");
                referenceWeight += weight;
            }

            int ownWeight = 0;
            for (Edge edge : ownAST.getTreeEdges()) {
                int weight = edge.getAttribute("weight");
                ownWeight += weight;
            }




            assertEquals(referenceWeight, ownWeight);

            referenceAST.clear();
            ownAST.clear();
        }

    }

}
