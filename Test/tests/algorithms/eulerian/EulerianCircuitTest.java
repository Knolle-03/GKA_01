package tests.algorithms.eulerian;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class EulerianCircuitTest {


    @Test
    void testEulerianCircuit() {




    }






    private boolean edgesUsedExactlyOnce(Graph graph, List<Edge> circuit) {

        Set<Edge> hashSet = new HashSet<>(circuit);
        return hashSet.size() == circuit.size();

    }

}
