package de.hawh.ld.GKA01.util;

import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AlgorithmTester {






    public void testVisuals(Graph graph, ColoringType coloringType, AbstractSpanningTree abstractSpanningTree) {

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


    public void analyseRuntime(int initialNodeCount, int repsOfEachRun, int timesToDoubleNodeCount, boolean printToConsole, String fileName, AbstractSpanningTree ... ASTs) {
        Stopwatch stopwatch = new Stopwatch();
        String[] excelHeader = getHeader(ASTs);
        List<long[]> dataForEachGraph = new ArrayList<>();
        int nodeCount = initialNodeCount;


        for (int doubling = 0; doubling < timesToDoubleNodeCount; doubling++){
            long[] millisAddedUp = new long[ASTs.length + 1];
            millisAddedUp[0] = nodeCount;
            for (int i = 0; i < repsOfEachRun; i++) {

                Generator generator = new DorogovtsevMendesGenerator();
                Graph graph = new MultiGraph(generator + " graph", false,true);
                generator.addSink(graph);
                generator.begin();
                for(int j=0; j< nodeCount; j++) {
                    generator.nextEvents();
                }
                generator.end();
                Random random = new Random();

                for (Edge edge : graph.getEdgeSet()) {
                    edge.addAttribute("weight", random.nextInt(20) );
                    edge.addAttribute("ui.label", (Integer) edge.getAttribute("weight"));
                }
                for (Node node : graph)  {
                    node.addAttribute("ui.label", node.getId());
                }

                // graph = generateWeightedGraph(generator, nodeCount, 1, 20);
                int ADTIndex = 1;
                for (AbstractSpanningTree AST : ASTs) {
                    stopwatch.start();
                    AST.init(graph);
                    AST.compute();
                    stopwatch.stop();
                    AST.clear();
                    millisAddedUp[ADTIndex++] +=  stopwatch.millisElapsed();
                    stopwatch.reset();
                }
                System.out.println(i + ". " +  nodeCount + " nodes graph done");
                graph = null;
                System.gc();

            }


            for (int j = 1; j < millisAddedUp.length; j++) {
                millisAddedUp[j] /= repsOfEachRun;
            }
            dataForEachGraph.add(millisAddedUp);
            if (printToConsole) printRound((doubling + 1), nodeCount,excelHeader, millisAddedUp);
            nodeCount *= 2;
        }
        exportDataToExcelSheet(fileName, excelHeader, dataForEachGraph);
    }


    public void exportDataToExcelSheet(String fileName,String[] header,  List<long[]> algorithmData) {
        ExcelExporter excelExporter = new ExcelExporter();
        try {
            excelExporter.exportData(fileName, header, algorithmData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Graph generateWeightedGraph(Generator generator, int nodeCount, int lowerBoundWeight, int upperBoundWeight) {

        Graph graph = generateNonWeightedGraph(generator, nodeCount);
        Random rng = new Random();
        for (Edge edge : graph.getEachEdge()) {
            int rndInt = rng.nextInt(upperBoundWeight - lowerBoundWeight) + lowerBoundWeight;
            edge.addAttribute("weight", rndInt);
            edge.addAttribute("ui.label", rndInt);
        }

        return graph;
    }



    public Graph generateNonWeightedGraph(Generator generator, int nodeCount) {
        Graph graph = new SingleGraph(generator + " graph", false,true);
        generator.addSink(graph);
        generator.begin();
        for(int i=0; i< nodeCount; i++)
            generator.nextEvents();
        generator.end();

//        for (Node node : graph) {
//            node.addAttribute("ui.label", "node: " + node.getId());
//        }

        return graph;
    }


    public List<long[]> getTable(int initialNodeCount, int repetitions, AbstractSpanningTree ... ASTs) {
        List<long[]> algorithmData = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            long[] row = new long[ASTs.length + 1];
            row[0] = initialNodeCount;

            algorithmData.add(row);
            initialNodeCount *= 2;
        }
        return algorithmData;
    }

    public String[] getHeader(AbstractSpanningTree ... ASTs) {
        String[] excelHeader = new String[ASTs.length + 1];
        String[] ADTNames = getADTNames(ASTs);
        excelHeader[0] = "Node count";
        System.arraycopy(ADTNames, 0, excelHeader, 1, ASTs.length);
        return excelHeader;
    }

    //TODO rewrite
    public void printRound(int currentLoop, int nodeCount,String[] names, long[] runTimes) {
        Stopwatch stopwatch = new Stopwatch();
        System.out.println("\n");
        System.out.printf("###################%02d##################\n", (currentLoop));
        System.out.printf("#      graph with %8d nodes      #\n", nodeCount);
        System.out.println("#######################################");
        for (int i = 1; i < names.length; i++) {
            System.out.printf("#  %10s took %s  #\n",names[i], stopwatch.elapsedTime(runTimes[i]));
        }
        System.out.println("#######################################");
    }

    private String[] getADTNames(AbstractSpanningTree ... ADTs) {
        String[] names = new String[ADTs.length];
        int i = 0;
        for (AbstractSpanningTree ADT : ADTs) {
            int posOfLastDot = ADT.toString().lastIndexOf('.');
            int posOfLastAt = ADT.toString().lastIndexOf('@');
            names[i++] = String.copyValueOf(ADT.toString().toCharArray(), posOfLastDot + 1, posOfLastAt - posOfLastDot - 1);
        }
        return names;
    }


}
