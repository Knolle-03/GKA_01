package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal1;
import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal2;
import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFibonacciHeap;
import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimPQ;
import de.hawh.ld.GKA01.util.ExcelExporter;
import de.hawh.ld.GKA01.util.Stopwatch;
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
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

//------------------------graph init-------------------------------------------------
        ExcelExporter excelExporter = new ExcelExporter();
        String[] algorithmNames = new String[] {"gsPrim", "myPrimFH", "myPrimPQ", "myKruskal", "myKruskal2", "gsKruskal"};
        AbstractSpanningTree[] abstractSpanningTrees = new AbstractSpanningTree[] {new Prim(), new PrimFibonacciHeap(), new PrimPQ(), new Kruskal1(), new Kruskal2(), new Kruskal1()};
        List<String[]> algorithmData = new ArrayList<>();
        algorithmData.add(new String[] {"Name of Algorithm", "32000", "64000", "128000", "265000", "512000", "1024000", "2048000", "4096000", "8192000", "16384000"});
        for (String algorithm: algorithmNames) {
            String[] strArr = new String[11];
            strArr[0] = algorithm;
            algorithmData.add(strArr);

        }

        Stopwatch stopwatch = new Stopwatch();
        int repetitions = 10;
        int nodeCount = 32000;

        for (int j = 0; j < repetitions ; j++) {
            stopwatch.start();
//            System.out.println("initializing graph...");
            Graph graph = new MultiGraph("rndGraph");
            Generator gen = new DorogovtsevMendesGenerator();
            gen.addSink(graph);
            gen.begin();
            for(int i=0; i< nodeCount; i++)
                gen.nextEvents();
            gen.end();


            Random random = new Random();

            for (Edge edge : graph.getEachEdge()) {
                int rndInt = random.nextInt(5);
                edge.addAttribute("weight", rndInt);
                edge.addAttribute("ui.label", rndInt);
            }

            for (Node node : graph) {
                node.addAttribute("ui.label", "node: " + node.getId());
            }

            int avgDegree = 0;
            for (Node node : graph) {
                avgDegree += node.getDegree();
            }
            avgDegree /= nodeCount;

            stopwatch.stop();
            System.out.println("\n");
            System.out.printf("################################%02d################################\n", (j + 1));
            System.out.printf("#  graph with %8d nodes build in %s time.    #\n", nodeCount, stopwatch.elapsedTime());
            System.out.println("##################################################################");
            stopwatch.reset();

            int counter = 1;
            for (AbstractSpanningTree tree : abstractSpanningTrees) {
                stopwatch.start();
                tree.init(graph);
                tree.compute();
                stopwatch.stop();
                //System.out.println(algorithm+ " weight: " + algorithm.);
                tree.clear();
                System.out.printf("#  %15s took %s.                                #\n",tree.toString(), stopwatch.elapsedTime());
                algorithmData.get(counter++)[j + 1] = String.valueOf(stopwatch.millisElapsed());
                stopwatch.reset();
            }








////-------------------------gsPrim init---------------------------------------------------
//
//            stopwatch.start();
//
//            org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim("weight", null, null);
//            gsPrim.init(graph);
//            gsPrim.compute();
//            stopwatch.stop();
//            //System.out.println("gsTreeWeight: " + gsPrim.getTreeWeight());
//            gsPrim.clear();
//            System.out.printf("#  gsPrim took %s.                                #\n", stopwatch.elapsedTime());
//            algorithmData.get(1)[j + 1] = String.valueOf(stopwatch.millisElapsed());
//            stopwatch.reset();
//
////------------------------primFibonacciHeap init---------------------------------------------------
//
//            stopwatch.start();
//            PrimFibonacciHeap primFibonacciHeap = new PrimFibonacciHeap();
//            primFibonacciHeap.init(graph);
//            primFibonacciHeap.compute();
//            stopwatch.stop();
//            System.out.printf("#  myPrimFH took %s.                              #\n", stopwatch.elapsedTime());
//            //System.out.println("fibonacciPrimTreeWeight: " + primFibonacciHeap.getTreeWeight());
//            algorithmData.get(2)[j + 1] = String.valueOf(stopwatch.millisElapsed());
//            stopwatch.reset();
//
//
////--------------------------primPQ init------------------------------------------------
//
//            int rndInt = random.nextInt(nodeCount);
//            stopwatch.start();
//            PrimPQ primPQ = new PrimPQ();
//            primPQ.init(graph);
//            primPQ.compute();
//            stopwatch.stop();
//            System.out.printf("#  myPrimPQ took %s.                              #\n", stopwatch.elapsedTime());
//            //System.out.println("myPrim: " + prim.getTreeWeight());
//            algorithmData.get(3)[j + 1] = String.valueOf(stopwatch.millisElapsed());
//            stopwatch.reset();
//
//
////------------------------kruskal init-------------------------------------------------
//
//            stopwatch.start();
//            Kruskal1 kruskal1 = new Kruskal1();
//            kruskal1.init(graph);
//            kruskal1.compute();
//            stopwatch.stop();
//            System.out.printf("#  Kruskal took %s.                               #\n", stopwatch.elapsedTime());
//            //System.out.println("KruskalTreeWeight: " + kruskal.getTreeWeight());
//            algorithmData.get(4)[j + 1] = String.valueOf(stopwatch.millisElapsed());
//            stopwatch.reset();
//
//
////------------------------kruskal2 init-------------------------------------------------
//
//            stopwatch.start();
//            Kruskal2 kruskal2 = new Kruskal2();
//            kruskal2.init(graph);
//            kruskal2.compute();
//            stopwatch.stop();
//            System.out.printf("#  Kruskal2 took %s.                              #\n", stopwatch.elapsedTime());
//            //System.out.println("Kruskal2TreeWeight: " + kruskal2.getTreeWeight());
//            algorithmData.get(5)[j + 1] = String.valueOf(stopwatch.millisElapsed());
//            stopwatch.reset();
//
//
////-----------------------gsKruskal init-------------------------------------------------
//
//            stopwatch.start();
//            org.graphstream.algorithm.Kruskal gsKruskal = new org.graphstream.algorithm.Kruskal("weight", null, null);
//            gsKruskal.init(graph);
//            gsKruskal.compute();
//            stopwatch.stop();
//            gsKruskal.clear();
//            System.out.printf("#  gsKruskal took %s.                             #\n", stopwatch.elapsedTime());
//            //System.out.println("gsKruskalTreeWeight: " + gsKruskal.getTreeWeight());
//            algorithmData.get(6)[j + 1] = String.valueOf(stopwatch.millisElapsed());
//            stopwatch.reset();








            // pq testing

//            System.out.println(graph.getEdgeCount());
//            stopwatch.start();
//            PriorityQueue<Edge> edgeQ = new PriorityQueue<>(Comparator.comparingInt(PrimPQ::getWeight));
//            edgeQ.addAll(graph.getEdgeSet());
//            stopwatch.stop();
//            String edgeQTime = stopwatch.elapsedTime();
//            stopwatch.reset();
//
//            stopwatch.start();
//            ArrayList<Edge> edgeArrayList = new ArrayList<>(graph.getEdgeSet());
//            edgeArrayList.sort(Comparator.comparingInt(PrimPQ::getWeight));
//            stopwatch.stop();
//            String edgeArrayListTime = stopwatch.elapsedTime();
//            stopwatch.reset();
//
//            stopwatch.start();
//            LinkedList<Edge> edgeLinkedList = new LinkedList<>(graph.getEdgeSet());
//            edgeLinkedList.sort(Comparator.comparingInt(PrimPQ::getWeight));
//            edgeQ.addAll(graph.getEdgeSet());
//            stopwatch.stop();
//            String edgeLinkedListTime = stopwatch.elapsedTime();
//            stopwatch.reset();
//
//            System.out.println("edgeQ: " + edgeQTime);
//            System.out.println("edgeArrayList: " + edgeArrayListTime);
//            System.out.println("edgeLinkedList: " + edgeLinkedListTime);
//


            System.out.printf("#  avgDegree: %3d with RandomGenerator                           #\n", avgDegree);
            System.out.println("##################################################################");
            nodeCount *= 2;





        }

        try {
            excelExporter.exportData( "test", algorithmData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }











    }

}
