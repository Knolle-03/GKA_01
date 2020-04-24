package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal1;
import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal2;
import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFibonacciHeap;
import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimPQ;
import de.hawh.ld.GKA01.util.*;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.Prim;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {

    private static final String[] algorithmNames = new String[] {"gsPrim", "myPrimFH", "myPrimPQ", "myKruskal", "myKruskal2", "gsKruskal"};
    private static final AbstractSpanningTree[] abstractSpanningTrees = new AbstractSpanningTree[] {new Prim("used", null, null), new PrimFibonacciHeap(), new PrimPQ(), new Kruskal1(), new Kruskal2(), new Kruskal()};
    private static final ExcelExporter excelExporter = new ExcelExporter();
    private static final Stopwatch stopwatch = new Stopwatch();
    private static final Random random = new Random();
    private static int currentLoop;
    private static final int repetitions = 16;
    private static int nodeCount = 16;
    private static final List<String[]> algorithmData = setTable(nodeCount);

    public static void main(String[] args) {

        Graph graph = generateGraph();
        PrimFibonacciHeap primFibonacciHeap = new PrimFibonacciHeap();
        Painter painter = new Painter();
        primFibonacciHeap.init(graph);
        primFibonacciHeap.compute();
        painter.attach(graph, ColoringType.MINIMAL_SPANNING_TREE);
        painter.colorGraph(primFibonacciHeap.getSpanningTree());
        graph.display();










//        for (currentLoop = 0 ; currentLoop < repetitions ; currentLoop++) {
//
//            Graph graph = generateGraph();
//            printHeader();
//            int row = 1;
//            int counter = 0;
//            for (AbstractSpanningTree tree : abstractSpanningTrees) {
//                stopwatch.start();
//                tree.init(graph);
//                tree.compute();
//                stopwatch.stop();
//                tree.clear();
//                System.out.printf("#  %15s took %s.                       #\n",algorithmNames[counter], stopwatch.elapsedTime());
//                algorithmData.get(row++)[currentLoop + 1] = "" + stopwatch.millisElapsed();
//                stopwatch.reset();
//            }
//            printFooter();
//            nodeCount *= 2;
//
//        }
//
//
//        try {
//            excelExporter.exportData( "test2", algorithmData);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }
















    private static Graph generateGraph() {
        Graph graph = new MultiGraph("rndGraph");
        Generator gen = new DorogovtsevMendesGenerator();
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i< nodeCount; i++)
            gen.nextEvents();
        gen.end();

        for (Edge edge : graph.getEachEdge()) {
            int rndInt = random.nextInt(5);
            edge.addAttribute("weight", rndInt);
            edge.addAttribute("ui.label", rndInt);
        }

        for (Node node : graph) {
            node.addAttribute("ui.label", "node: " + node.getId());
        }

        return graph;
    }


    private static List<String[]> setTable(int start) {
        List<String[]> algorithmData = new ArrayList<>();
        String[] excelHeader = new String[repetitions + 1];

        excelHeader[0] = "Name of algorithm";
        for (int i = 1; i < excelHeader.length ; i++) {
            excelHeader[i] = "" + start;
            start *= 2;
        }

        algorithmData.add(excelHeader);

        for (String algorithm : algorithmNames) {
            String[] row = new String[repetitions + 1];
            row[0] = algorithm;
            algorithmData.add(row);
        }
        return algorithmData;
    }
    
    private static void printHeader() {
        System.out.println("\n");
        System.out.printf("################################%02d################################\n", (currentLoop + 1));
        System.out.printf("#                   graph with %8d nodes                    #\n", nodeCount);
        System.out.println("##################################################################");

    }
    
    private static void printFooter() {
        System.out.println("##################################################################");
    }
}
