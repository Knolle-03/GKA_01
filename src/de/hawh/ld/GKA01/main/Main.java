package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnKruskal;
import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnPrimFH;
import de.hawh.ld.GKA01.util.AlgorithmTester;
import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import de.hawh.ld.GKA01.util.generators.RandomEulerianGenerator;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Random;


public class Main {

    public static void main(String[] args) {
        RandomEulerianGenerator randomEulerianGenerator = new RandomEulerianGenerator(42);
        Graph graph = new SingleGraph("testEulerianGraph", false, true);
        randomEulerianGenerator.addSink(graph);
        randomEulerianGenerator.begin();
        randomEulerianGenerator.end();
        graph.display();
        ConnectedComponents connectedComponents = new ConnectedComponents();
        connectedComponents.init(graph);
        connectedComponents.compute();
        System.out.println(connectedComponents.getConnectedComponentsCount());


    }
}
