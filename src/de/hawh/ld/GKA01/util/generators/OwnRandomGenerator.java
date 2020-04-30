package de.hawh.ld.GKA01.util.generators;

import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OwnRandomGenerator extends BaseGenerator {

    private int nodeNames = 0;


    private Random rng;
    private int nodesToAdd;
    private int edgesToAdd;
    private int upperBound = 10;
    private int lowerBound = 0;
    private final List<Node> connected = new ArrayList<>();



    public OwnRandomGenerator() {
        setUseInternalGraph(true);
    }

    public OwnRandomGenerator(int nodesToAdd, int edgesToAdd) {
        this();
        if (edgesToAdd < nodesToAdd - 1) throw new IllegalArgumentException("need at least nodeCount - 1 edges to generate a connected graph.");
        this.nodesToAdd = nodesToAdd;
        this.edgesToAdd = edgesToAdd;
    }

    public OwnRandomGenerator(int nodesToAdd, int edgesToAdd, Random rng){
        this(nodesToAdd, edgesToAdd);
        this.rng = rng;
    }

    public OwnRandomGenerator(int nodesToAdd, int edgesToAdd, Random rng, int upperBound) {
        this(nodesToAdd, edgesToAdd, rng);
        this.upperBound = upperBound;
    }

    public OwnRandomGenerator(int nodesToAdd, int edgesToAdd, Random rng, int upperBound, int lowerBound){
        this(nodesToAdd, edgesToAdd, rng, upperBound);
        this.lowerBound = lowerBound;
    }


    @Override
    public void begin() {
        this.rng = this.rng == null ? new Random(System.currentTimeMillis()) : this.rng;
        addNode(Integer.toString(nodeNames++));
        connected.add(internalGraph.getNode(nodeNames - 1));
        nodesToAdd--;

        while (nodesToAdd > 0) {
            addNode(Integer.toString(nodeNames++));
            Node nodeToAdd = internalGraph.getNode(nodeNames - 1);
            Node nodeInGraph = connected.get(rng.nextInt(connected.size()));
            addEdge(nodeInGraph + "-" + nodeToAdd, nodeInGraph.getId(), nodeToAdd.getId());

            connected.add(internalGraph.getNode(nodeNames - 1));
            nodesToAdd--;
            edgesToAdd--;
        }

        while (edgesToAdd > 0) {
            Node rngNode1 = connected.get(rng.nextInt(connected.size()));
            Node rngNode2 = connected.get(rng.nextInt(connected.size()));

            while (rngNode1.hasEdgeBetween(rngNode2)) {
                if (rngNode1.getDegree() < nodesToAdd - 1) {
                    rngNode2 = connected.get(rng.nextInt(connected.size()));
                } else {
                    rngNode1 = connected.get(rng.nextInt(connected.size()));
                }
            }

            addEdge(rngNode1 + "-" + rngNode2, rngNode1.getId(), rngNode2.getId());

            edgesToAdd--;
        }

        for (Edge edge : internalGraph.getEdgeSet()) {
            edge.addAttribute("weight", rng.nextInt(upperBound - lowerBound) + lowerBound);
        }

    }

    @Override
    public boolean nextEvents() {
        return true;
    }

    @Override
    public void end() {
        super.end();
    }
}
