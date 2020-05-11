package tests.algorithms.spanning_trees;

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnKruskal;
import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnPrimFH;
import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.Prim;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.util.TestGraphGenerator;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SpanningTreeTest {

    private static List<Graph> testGraphs;

    private static Stream<Arguments> algorithms() {
        return Stream.of(
                Arguments.of(new OwnPrimFH(), new Prim()),
                Arguments.of(new OwnKruskal(), new Kruskal()));
    }


    @BeforeAll
    static void setUp() {
        TestGraphGenerator testGraphGenerator = new TestGraphGenerator(10, 100, 10, new DorogovtsevMendesGenerator(), new OwnRandomGenerator());
        testGraphs = testGraphGenerator.generateWeightedTestGraphs(1, 20);
    }

    @ParameterizedTest
    @MethodSource("algorithms")
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
