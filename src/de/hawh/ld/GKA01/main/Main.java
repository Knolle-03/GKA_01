package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnKruskal;
import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnPrimFH;
import de.hawh.ld.GKA01.util.AlgorithmTester;
import de.hawh.ld.GKA01.util.ColoringType;
import de.hawh.ld.GKA01.util.RandomEulerianGenerator;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.Prim;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;


public class Main {

    public static void main(String[] args) {

        //AlgorithmTester algorithmTester = new AlgorithmTester();
        //AbstractSpanningTree[] ASTs = new AbstractSpanningTree[] {new Prim(), new OwnPrimFH(), new Kruskal(), new OwnKruskal()};
        //Graph graph = algorithmTester.generateWeightedGraph(new DorogovtsevMendesGenerator(), 5, 1, 20);
        //algorithmTester.analyseRuntime(1_000, 1, 14, true, "1k_1reps_14doubling_RandomGraph10", ASTs);

        //algorithmTester.testVisuals(graph, ColoringType.MINIMAL_SPANNING_TREE, new OwnPrimFH());

//        Graph graph = new SingleGraph("random euclidean");
//        Generator gen = new RandomEuclideanGenerator();
//        gen.addSink(graph);
//        gen.begin();
//        for(int i=0; i<1000; i++) {
//            gen.nextEvents();
//        }
//        gen.end();
//        graph.addAttribute("ui.stylesheet", "url(resources\\stylesheet)");
//        graph.addAttribute("ui.antialias");
//        graph.display(false);

        Graph graph = new SingleGraph("testGraph", false, true);
        RandomEulerianGenerator randomEulerianGenerator = new RandomEulerianGenerator(4);
        graph.display();
        randomEulerianGenerator.addSink(graph);
        randomEulerianGenerator.begin();
        randomEulerianGenerator.nextEvents();
        randomEulerianGenerator.end();


        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }








    }
}
