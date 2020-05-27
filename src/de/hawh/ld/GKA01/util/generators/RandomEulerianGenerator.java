package de.hawh.ld.GKA01.util.generators;

import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEulerianGenerator extends BaseGenerator {

    protected int nodeNames = 0;
    protected int nodeCount;
    protected List<Node> unconnected = new ArrayList<>();
    protected List<Node> connected = new ArrayList<>();


    public RandomEulerianGenerator() {
        this.nodeCount = 3;
        setUseInternalGraph(true);
    }


    public RandomEulerianGenerator(int nodeCount) {
        if (nodeCount < 1 || nodeCount == 2) throw new IllegalArgumentException("An eulerian graph must have only one node or at least three nodes.");
        setUseInternalGraph(true);
        this.nodeCount = nodeCount;
    }

    @Override
    public void begin() {

        if (nodeCount == 1) {
            addNode(Integer.toString(1));
            return;
        }

        random =  new Random(System.currentTimeMillis());




        for (int i = 0; i < nodeCount; i++) {
            addNode(Integer.toString(i));
            unconnected.add(internalGraph.getNode(i));
        }

        nodeNames = nodeCount;

        // get two random nodes
        Node node0 = unconnected.remove(random.nextInt(unconnected.size()));
        Node node1 = unconnected.remove(random.nextInt(unconnected.size()));

        //connect those nodes
        addEdge(node0 + "-" + node1, node0.getId(), node1.getId());

        // adjust connected state of nodes
        connected.add(node0);
        connected.add(node1);


//        if (nodeCount == 3) {
//            Node lastUnconnectedNode = unconnected.get(0);
//            addEdge(lastUnconnectedNode + "-" + node0, lastUnconnectedNode.getId(), node0.getId());
//            addEdge(lastUnconnectedNode + "-" + node1, lastUnconnectedNode.getId(), node1.getId());
//
//        } else {
            //connect remaining nodes randomly
            while (unconnected.size() > 0) {
                Node unconnectedNode = unconnected.remove(random.nextInt(unconnected.size()));
                Node nodeInGraph;

                do {
                    nodeInGraph = connected.get(random.nextInt(connected.size()));
                // make sure a node is not connected to all other nodes if the graph has an even node count
                // because that node would have an uneven edge count and can't be connected to any more nodes.
                } while (nodeInGraph.getDegree() == nodeCount - 2 && nodeCount % 2 == 0);
                addEdge(unconnectedNode + "-" + nodeInGraph, unconnectedNode.getId(), nodeInGraph.getId());
                connected.add(unconnectedNode);
            }



            // get two lists for nodes with odd and even degree
            List<Node> oddDegreeNodes = new ArrayList<>();
            List<Node> evenDegreeNodes = new ArrayList<>();
            for (Node node : internalGraph) {
                if (node.getDegree() % 2 == 1) oddDegreeNodes.add(node);
                else evenDegreeNodes.add(node);
            }

            // while there are still odd nodes
            while (oddDegreeNodes.size() > 0) {



                // get an odd node
                Node oddNode = oddDegreeNodes.remove(random.nextInt(oddDegreeNodes.size()));


                boolean connectedByNow = false;

                // try to connect the node with another odd node
                for (Node anotherOddNode : oddDegreeNodes) {
                    // only if not already directly connected
                    if (!oddNode.hasEdgeBetween(anotherOddNode)) {
                        //connect nodes
                        addEdge(oddNode + "-" + anotherOddNode, oddNode.getId(), anotherOddNode.getId());
                        oddDegreeNodes.remove(anotherOddNode);
                        evenDegreeNodes.add(oddNode);
                        evenDegreeNodes.add(anotherOddNode);
                        connectedByNow = true;
                        break;
//                    } else if (oddNode.getDegree() > 2 && anotherOddNode.getDegree() > 2) {
//                        delEdge(oddNode.getEdgeBetween(anotherOddNode).getId());
//                        oddDegreeNodes.remove(anotherOddNode);
//                        evenDegreeNodes.add(oddNode);
//                        evenDegreeNodes.add(anotherOddNode);
//                        connectedByNow = true;
//                        break;
                    }
                }

                // if no odd node was found connect with even node
                if (!connectedByNow) {

                    for (Node evenDegreeNode : evenDegreeNodes) {
                        // only if not already connected
                        if (!oddNode.hasEdgeBetween(evenDegreeNode)) {
                            addEdge(oddNode + "-" + evenDegreeNode, oddNode.getId(), evenDegreeNode.getId());
                            // add former even node to odd list since it got another edge
                            evenDegreeNodes.remove(evenDegreeNode);
                            evenDegreeNodes.add(oddNode);
                            // add former odd node to even list since it got another edge
                            oddDegreeNodes.add(evenDegreeNode);
                            //connectedByNow = true;
                            break;
                        }
                    }
                }


                // handle edge case where one node is connected to all other nodes
//            if (!connectedByNow && oddDegreeNodes.size() == 1) {
//
//                Node theOtherOddNode = oddDegreeNodes.remove(0);
//
//                if (theOtherOddNode.hasEdgeBetween(oddNode) && theOtherOddNode.getDegree() > 1 && oddNode.getDegree() > 1) {
//                    Edge edgeToRemove = oddNode.getEdgeBetween(theOtherOddNode);
//                    delEdge(edgeToRemove.getId());
//                    evenDegreeNodes.add(oddNode);
//                    evenDegreeNodes.add(theOtherOddNode);
//                    connectedByNow = true;
//                } else {
//                    oddDegreeNodes.add(theOtherOddNode);
//                }
//            }
//            if (!connectedByNow) oddDegreeNodes.add(oddNode);

//                System.out.println("even: " + evenDegreeNodes.size());
//                System.out.println("uneven: " + oddDegreeNodes.size());
//                System.out.println("--------");
            }


    }

    @Override
    public boolean nextEvents() {
        return false;
    }
}
