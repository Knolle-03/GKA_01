package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.spanning_trees.Kruskal;
import de.hawh.ld.GKA01.algorithms.spanning_trees.PrimFibonacciHeap;
import de.hawh.ld.GKA01.util.Stopwatch;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.Random;


public class Main {

    public static void main(String[] args) {

//------------------------graph init-------------------------------------------------
        Stopwatch stopwatch = new Stopwatch();
        int repetitions = 10;
        int nodeCount = 64000;

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

            stopwatch.stop();
            System.out.println("\n");
            System.out.printf("################################%02d################################\n", (j + 1));
            System.out.printf("#  graph with %8d nodes build in %s time.    #\n", nodeCount, stopwatch.elapsedTime());
            System.out.println("##################################################################");
            stopwatch.reset();
            nodeCount *= 2;

//-------------------------gsPrim init---------------------------------------------------
            stopwatch.start();

            org.graphstream.algorithm.Prim gsPrim = new org.graphstream.algorithm.Prim();
            gsPrim.init(graph);
            gsPrim.compute();
            stopwatch.stop();
            //System.out.println("gsTreeWeight: " + gsPrim.getTreeWeight());
            gsPrim.clear();
            System.out.printf("#  gsPrim took %s.                                #\n", stopwatch.elapsedTime());
            stopwatch.reset();
//------------------------primFibonacciHeap init---------------------------------------------------
            stopwatch.start();
            PrimFibonacciHeap primFibonacciHeap = new PrimFibonacciHeap();
            primFibonacciHeap.makeTree(graph);
            stopwatch.stop();
            System.out.printf("#  PrimFibonacciHeap took %s.                     #\n", stopwatch.elapsedTime());
            //System.out.println("fibonacciPrimTreeWeight: " + primFibonacciHeap.getTreeWeight());
            stopwatch.reset();

//------------------------kruskal init-------------------------------------------------

            stopwatch.start();
            Kruskal kruskal = new Kruskal();
            kruskal.init(graph);
            kruskal.compute();
            stopwatch.stop();
            System.out.printf("#  Kruskal took %s.                               #\n", stopwatch.elapsedTime());
            //System.out.println("KruskalTreeWeight: " + kruskal.getTreeWeight());
            stopwatch.reset();

//-----------------------gsKruskal init-------------------------------------------------

            stopwatch.start();
            org.graphstream.algorithm.Kruskal gsKruskal = new org.graphstream.algorithm.Kruskal();
            gsKruskal.init(graph);
            gsKruskal.compute();
            stopwatch.stop();
            gsKruskal.clear();
            System.out.printf("#  gsKruskal took %s.                             #\n", stopwatch.elapsedTime());
            //System.out.println("gsKruskalTreeWeight: " + gsKruskal.getTreeWeight());
            stopwatch.reset();

//--------------------------primPQ init------------------------------------------------
//            int rndInt = random.nextInt(nodeCount);
//            stopwatch.start();
//            PrimPQ prim = new PrimPQ();
//            prim.getMinSpanTree(graph, graph.getNode(rndInt));
//            stopwatch.stop();
//            System.out.println("PrimPQ took " + stopwatch.elapsedTime() + " for " + nodeCount + " nodes.");
//            //System.out.println("myPrim: " + prim.getTreeWeight());
//            stopwatch.reset();
            //System.out.println("#########################################");
            System.out.println("##################################################################");
        }










    }

}
