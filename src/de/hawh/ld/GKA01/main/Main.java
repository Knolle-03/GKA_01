package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnKruskal;
import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnPrimFH;
import de.hawh.ld.GKA01.util.AlgorithmTester;
import org.graphstream.algorithm.AbstractSpanningTree;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.Prim;


public class Main {

    public static void main(String[] args) {

//        int nodeCount = 1000;
//        for (int i = 0; i < 1000 ; i++) {

//        }





        AlgorithmTester algorithmTester = new AlgorithmTester();
        AbstractSpanningTree[] ASTs = new AbstractSpanningTree[] {new Prim(), new OwnPrimFH(), new Kruskal(), new OwnKruskal()};

        algorithmTester.analyseRuntime(1_000, 100, 14, true, "1k_100reps_14doubling", ASTs);


    }
}
