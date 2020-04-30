package de.hawh.ld.GKA01.util.generators;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleGenerator {

    private int nodeNames = 0;

    private final Graph internalGraph = new SingleGraph("ownGraph", false, true);
    private Random rng;
    private int nodeCount;
    private int edgeCount;
    private int upperBound = 10;
    private int lowerBound = 0;
    private final List<Node> connected = new ArrayList<>();


    public SimpleGenerator(int nodeCount, int edgeCount) {
        if (edgeCount < nodeCount - 1) throw new IllegalArgumentException("need at least nodeCount - 1 edges to generate a connected graph.");

        this.nodeCount = nodeCount;
        this.edgeCount = edgeCount;
    }

    public SimpleGenerator(int nodeCount, int edgeCount, Random rng){
        this(nodeCount, edgeCount);
        this.rng = rng;
    }

    public SimpleGenerator(int nodeCount, int edgeCount, Random rng, int upperBound) {
        this(nodeCount, edgeCount, rng);
        this.upperBound = upperBound;
    }

    public SimpleGenerator(int nodeCount, int edgeCount, Random rng, int upperBound, int lowerBound){
        this(nodeCount, edgeCount, rng, upperBound);
        this.lowerBound = lowerBound;
    }




    public Graph generate() {

        this.rng = this.rng == null ? new Random(System.currentTimeMillis()) : this.rng;
        addNode(Integer.toString(nodeNames++));
        connected.add(internalGraph.getNode(nodeNames - 1));
        nodeCount--;
        while (nodeCount > 0) {
            addNode(Integer.toString(nodeNames++));
            Node nodeToAdd = internalGraph.getNode(nodeNames - 1);
            Node nodeInGraph = connected.get(rng.nextInt(connected.size()));
            addEdge(nodeInGraph + "-" + nodeToAdd, nodeInGraph.getId(), nodeToAdd.getId());

            connected.add(internalGraph.getNode(nodeNames - 1));
            nodeCount--;
        }
        edgeCount -= internalGraph.getEdgeCount();

        while (edgeCount > 0) {

            Node rngNode1 = connected.get(rng.nextInt(connected.size()));
            Node rngNode2 = connected.get(rng.nextInt(connected.size()));

            while (rngNode1.hasEdgeBetween(rngNode2)) {
                if (rngNode1.getDegree() < nodeCount - 1) {
                    rngNode2 = connected.get(rng.nextInt(connected.size()));
                } else {
                    rngNode1 = connected.get(rng.nextInt(connected.size()));
                }
            }

            addEdge(rngNode1 + "-" + rngNode2, rngNode1.getId(), rngNode2.getId());

            edgeCount--;
        }

        for (Edge edge : internalGraph.getEdgeSet()) {
            edge.addAttribute("weight", rng.nextInt(upperBound - lowerBound) + lowerBound);
        }

        return internalGraph;
    }


    private void addEdge(String name, String from, String to) {
        internalGraph.addEdge(name, from, to);
    }

    private void addNode(String name) {
        internalGraph.addNode(name);
    }


    public static void main(String[] args) {
        SimpleGenerator simpleGenerator = new SimpleGenerator(1000, 2000);
        Graph graph = simpleGenerator.generate();
        graph.display();
    }


}
