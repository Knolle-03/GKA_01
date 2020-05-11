package de.hawh.ld.GKA01.util.generators;

import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 * A Generator that is compatible with graph stream
 */

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

    // The constructors allow to give upper and lower bounds for the weight of edge and require the number of nodes and and edges
    /**
     * A Generator that is compatible with graph stream
     *
     * @throws  IllegalArgumentException if not all nodes can be connected.
     */



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

        // if no Random generator was given create one
        this.rng = this.rng == null ? new Random(System.currentTimeMillis()) : this.rng;
        //start with a single node in the Graph
        addNode(Integer.toString(nodeNames++));
        //add it to the list of nodes that are connected in the graph
        connected.add(internalGraph.getNode(nodeNames - 1));
        nodesToAdd--;


        // while not all nodes are added
        while (nodesToAdd > 0) {

            //add another node
            addNode(Integer.toString(nodeNames++));

            Node nodeToAdd = internalGraph.getNode(nodeNames - 1);
            Node nodeInGraph = connected.get(rng.nextInt(connected.size()));
            // connect the new node with a random node that is already in the graph
            addEdge(nodeInGraph + "-" + nodeToAdd, nodeInGraph.getId(), nodeToAdd.getId());

            // add new node to the list of connected nodes
            connected.add(internalGraph.getNode(nodeNames - 1));

            nodesToAdd--;
            edgesToAdd--;
        }

        // if there are more edges to add

        //while not all edges are added
        while (edgesToAdd > 0) {

            // find two random nodes in the graph
            Node rngNode1 = connected.get(rng.nextInt(connected.size()));
            Node rngNode2 = connected.get(rng.nextInt(connected.size()));

            // check if they are already connected
            while (rngNode1.hasEdgeBetween(rngNode2)) {
                // if so and node one is not already connected to all nodes
                if (rngNode1.getDegree() < nodesToAdd - 1) {
                    // pick another second node
                    rngNode2 = connected.get(rng.nextInt(connected.size()));
                } else {
                    // else pick two new nodes
                    rngNode1 = connected.get(rng.nextInt(connected.size()));
                    rngNode2 = connected.get(rng.nextInt(connected.size()));
                }
            }
            // connect both nodes
            addEdge(rngNode1 + "-" + rngNode2, rngNode1.getId(), rngNode2.getId());


            edgesToAdd--;
        }



        //all nodes are added

    }

    @Override
    public boolean nextEvents() {
        return false;
    }

    @Override
    public void end() {
        super.end();
    }
}
