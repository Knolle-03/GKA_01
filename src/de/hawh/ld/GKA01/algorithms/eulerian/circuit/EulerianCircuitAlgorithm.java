package de.hawh.ld.GKA01.algorithms.eulerian.circuit;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;

import java.util.List;

public interface EulerianCircuitAlgorithm extends Algorithm {
    List<Edge> getEulerianTour();
    void clear();
}
