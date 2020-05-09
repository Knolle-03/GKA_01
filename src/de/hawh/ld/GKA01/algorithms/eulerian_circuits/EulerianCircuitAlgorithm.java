package de.hawh.ld.GKA01.algorithms.eulerian_circuits;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;

import java.util.List;

public interface EulerianCircuitAlgorithm extends Algorithm {
    List<Edge> getEulerianCircuit();
    void clear();
}
