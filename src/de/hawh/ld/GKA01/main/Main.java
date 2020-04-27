package de.hawh.ld.GKA01.main;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        AlgorithmTester algorithmTester = new AlgorithmTester();
        int repetitions = 14;
        int nodeCount = 1000;
        Graph testGraph;
        List<String[]> algorithmData;
        algorithmData = algorithmTester.setTable(nodeCount, repetitions);
        for (int i = 0; i < repetitions; i++) {
            testGraph = algorithmTester.generateWeightedGraph(new MultiGraph("testGraph", false, true), new DorogovtsevMendesGenerator(), nodeCount, 1, 20);
            algorithmTester.analyseRuntime(testGraph, algorithmData, i + 1);
            nodeCount *= 2;
        }
        algorithmTester.exportDataToExcelSheet("test123", algorithmData);
    }

}
