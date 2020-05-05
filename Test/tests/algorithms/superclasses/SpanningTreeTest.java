package tests.algorithms.superclasses;

import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import tests.TestGraphGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpanningTreeTest {

    private static final int lowerBound = 1;
    private static final int upperBound = 20;
    private static final int nodeCount = 1_000_000;
    private static final int edgeCount = 2_000_000;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(nodeCount, new DorogovtsevMendesGenerator(), new OwnRandomGenerator(nodeCount, edgeCount));
    private final List<Graph> testGraphs = testGraphGenerator.generateWeightedTestGraphs(lowerBound, upperBound);



    public void testEachGraph(AbstractSpanningTree referenceAST , AbstractSpanningTree ownAST) {



        for (Graph graph : testGraphs) {
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
