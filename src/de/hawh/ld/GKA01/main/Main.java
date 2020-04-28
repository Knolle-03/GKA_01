package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.util.ColoringType;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;



public class Main {

    public static void main(String[] args) {
        AlgorithmTester algorithmTester = new AlgorithmTester();
        int repetitions = 10;
        int nodeCount = 20;
        Graph testGraph = algorithmTester.generateWeightedGraph(  new MultiGraph("testGraph", false, true),
                new RandomGenerator(1 ),
                nodeCount, 1, 20);
//        List<String[]> algorithmData;
//        algorithmData = algorithmTester.setTable(nodeCount, repetitions);
//        for (int i = 0; i < repetitions; i++) {
//
//            algorithmTester.analyseRuntime(testGraph, algorithmData, i + 1);
//            nodeCount *= 2;
//            testGraph = algorithmTester.generateWeightedGraph(  new MultiGraph("testGraph", false, true),
//                    new DorogovtsevMendesGenerator(),
//                    nodeCount, 1, 20);
//        }
//        algorithmTester.exportDataToExcelSheet("test123", algorithmData);
//
        algorithmTester.testVisuals(testGraph, ColoringType.MINIMAL_SPANNING_TREE, new Kruskal());



    }
}
