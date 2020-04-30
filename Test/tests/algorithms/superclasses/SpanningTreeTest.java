package tests.algorithms.superclasses;

import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpanningTreeTest extends GraphTest {

    protected static final int WEIGHT_MAX = 20;








    public void setWeightOfTestGraphEdges() {
        if (testGraphs.size() == 0) generateNonWeightedTestGraphs();
        System.out.println(testGraphs.size());
        for (Graph graph : testGraphs) {
            //System.out.println(!graph.getNode(0).hasAttribute("weight"));
            if (!graph.getEdge(0).hasAttribute("weight")){
                int counter = 0;
                for (Edge edge: graph.getEdgeSet()) {
                    if (counter++ % 50_000 == 0) System.out.println(counter + " edges weighted.");
                    edge.addAttribute("weight", random.nextInt(WEIGHT_MAX));
                    //edge.addAttribute("ui.label", (Integer) edge.getAttribute("weight"));
                }
            }

        }
    }

    public void testEachGraph(AbstractSpanningTree referenceAST , AbstractSpanningTree ownAST) {
        setWeightOfTestGraphEdges();

        System.out.println(testGraphs.size());

        for (Graph graph : testGraphs) {
            System.out.println(graph);
            referenceAST.init(graph);
            referenceAST.compute();

            ownAST.init(graph);
            ownAST.compute();

            assertEquals(getTreeWeight(referenceAST), getTreeWeight(ownAST));

            referenceAST.clear();
            ownAST.clear();
        }

    }



    private int getTreeWeight(AbstractSpanningTree AST) {
        int totalWeight = 0;
        for (Edge edge : AST.getTreeEdges()) {
            int weight = edge.getAttribute("weight");
            totalWeight += weight;
        }
        return totalWeight;
    }
}
