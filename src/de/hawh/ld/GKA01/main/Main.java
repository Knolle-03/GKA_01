package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal;
import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFH;
import de.hawh.ld.GKA01.util.*;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.Prim;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        int repetitions = 14;
        int nodeCount = 1000;
        Graph testGraph;
        List<String[]> algorithmData;
        algorithmData = setTable(nodeCount, repetitions);
        for (int i = 0; i < repetitions; i++) {
            testGraph = generateWeightedGraph(new MultiGraph("testGraph", false, true), new DorogovtsevMendesGenerator(), nodeCount, 1, 20);
            analyseRuntime(testGraph, algorithmData, i + 1);
            nodeCount *= 2;
        }
        exportDataToExcelSheet("test123", algorithmData);
    }


    private static void testVisuals(Graph graph, ColoringType coloringType, AbstractSpanningTree abstractSpanningTree) {

        Painter painter = new Painter();
        abstractSpanningTree.init(graph);
        painter.attach(graph, coloringType);
        graph.display();
        abstractSpanningTree.compute();

        List<Edge> spanningTree = new ArrayList<>();
        Iterator<Edge> edgeIterator = abstractSpanningTree.getTreeEdgesIterator();
        edgeIterator.forEachRemaining(spanningTree::add);

        painter.colorGraph(spanningTree);
    }


    private static void analyseRuntime(Graph graph, List<String[]> algorithmData, int round) {
        AbstractSpanningTree[] abstractSpanningTrees = new AbstractSpanningTree[] {new Prim("used", null, null), new PrimFH(), new org.graphstream.algorithm.Kruskal(), new Kruskal()};
        Stopwatch stopwatch = new Stopwatch();

            int cell = 1;
            for (AbstractSpanningTree tree : abstractSpanningTrees) {
                stopwatch.start();
                tree.init(graph);
                tree.compute();
                stopwatch.stop();
                tree.clear();
                algorithmData.get(round)[cell++] = "" + stopwatch.millisElapsed();
                stopwatch.reset();
        }
    }


    private static void exportDataToExcelSheet(String fileName, List<String[]> algorithmData) {
        ExcelExporter excelExporter = new ExcelExporter();
        try {
            excelExporter.exportData(fileName, algorithmData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Graph generateWeightedGraph(Graph newGraph, Generator generator, int nodeCount, int lowerBoundWeight, int upperBoundWeight) {

        Graph graph = generateNonWeightedGraph(newGraph, generator, nodeCount);
        Random rng = new Random();
        for (Edge edge : graph.getEachEdge()) {
            int rndInt = rng.nextInt(upperBoundWeight - lowerBoundWeight) + lowerBoundWeight;
            edge.addAttribute("weight", rndInt);
            edge.addAttribute("ui.label", rndInt);
        }

        return graph;
    }



    private static Graph generateNonWeightedGraph(Graph graph, Generator generator, int nodeCount) {
        generator.addSink(graph);
        generator.begin();
        for(int i=0; i< nodeCount; i++)
            generator.nextEvents();
        generator.end();

        for (Node node : graph) {
            node.addAttribute("ui.label", "node: " + node.getId());
        }

        return graph;
    }


    private static List<String[]> setTable(int initialNodeCount, int repetitions) {
        String[] algorithmNames = new String[] {"gsPrim", "myPrimFH", "gsKruskal", "myKruskal"};
        List<String[]> algorithmData = new ArrayList<>();
        String[] excelHeader = new String[algorithmNames.length + 1];

        excelHeader[0] = "Node count";
        System.arraycopy(algorithmNames, 0, excelHeader, 1, algorithmNames.length);

        algorithmData.add(excelHeader);

        for (int i = 0; i < repetitions; i++) {
            String[] row = new String[algorithmNames.length + 1];
            row[0] = "" + initialNodeCount;
            algorithmData.add(row);
            initialNodeCount *= 2;
        }



        return algorithmData;
    }


    private static void printRound() {
        //                 System.out.printf("#  %15s took %s.                       #\n",algorithmNames[counter++], stopwatch.elapsedTime());
    }

    
    private static void printHeader(int currentLoop, int nodeCount) {
        System.out.println("\n");
        System.out.printf("################################%02d################################\n", (currentLoop + 1));
        System.out.printf("#                   graph with %8d nodes                    #\n", nodeCount);
        System.out.println("##################################################################");

    }
    
    private static void printFooter() {
        System.out.println("##################################################################");
    }
}
