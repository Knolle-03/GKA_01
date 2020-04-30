package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnKruskal;
import de.hawh.ld.GKA01.algorithms.spanning_trees.OwnPrimFH;
import de.hawh.ld.GKA01.util.AlgorithmTester;
import org.graphstream.algorithm.AbstractSpanningTree;



public class Main {

    public static void main(String[] args) {
        AbstractSpanningTree[] ASTs = new AbstractSpanningTree[] {new OwnPrimFH(), new OwnKruskal()};
        AlgorithmTester algorithmTester = new AlgorithmTester();

        algorithmTester.analyseRuntime(1000, 1, 10, true, "5TimesAsManyEdgesAsNodes", ASTs);

    }
}
