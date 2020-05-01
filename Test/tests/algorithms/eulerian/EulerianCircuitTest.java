package tests.algorithms.eulerian;

import de.hawh.ld.GKA01.algorithms.eulerian.circuit.Fleury;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
public class EulerianCircuitTest {

    private static Graph graph;
    private static Generator generator;
    private static int initialNodeCount = 3;
    private static int loops = 20;
    @Test
    void testEulerianCircuit() {

        for (int i = 0; i < loops; i++) {
            graph = generateEulerianGraph(initialNodeCount);
            Fleury fleury = new Fleury();
            fleury.init(graph);
            fleury.compute();
            System.out.println(i);
            assertEquals(fleury.getEulerianTour().size(), graph.getEdgeCount());
            System.out.println("edgeCount ticks");
            System.out.println(fleury.getEulerianTour());
            assertTrue(edgesUsedExactlyOnce(fleury.getEulerianTour()));
            initialNodeCount *= 2;
        }
    }


    private Graph generateEulerianGraph(int nodecount) {
        generator = new RandomEulerianGenerator(initialNodeCount);
        graph = new SingleGraph("eulerianTestGraph", false, true);
        generator.addSink(graph);
        generator.begin();
        generator.end();

        return graph;
    }



    private boolean edgesUsedExactlyOnce(List<Edge> circuit) {
        Set<Edge> hashSet = new HashSet<>(circuit);
        return hashSet.size() == circuit.size();

    }

}
