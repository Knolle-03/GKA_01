package de.hawh.ld.GKA01.util.generators;

import org.apache.poi.ss.formula.functions.T;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomEulerianGenerator extends BaseGenerator {

    protected int nodeNames = 0;

    protected int nodeCount;
    protected List<Node> unconnected = new ArrayList<>();
    protected List<Node> connected = new ArrayList<>();


    public RandomEulerianGenerator() {
        this.nodeCount = 20;
        setUseInternalGraph(true);
    }


    public RandomEulerianGenerator(int nodeCount) {
        setUseInternalGraph(true);
        this.nodeCount = nodeCount;
    }

    public RandomEulerianGenerator(int nodeCount, Random random) {
        this(nodeCount);
        this.random = random;
    }


    @Override
    public void begin() {
        this.random = this.random == null ? new Random(System.currentTimeMillis()) : this.random;

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

        //connect remaining nodes randomly
        while (unconnected.size() > 0) {
            Node unconnectedNode = unconnected.remove(random.nextInt(unconnected.size()));
            Node nodeInGraph = connected.get(random.nextInt(connected.size()));
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

//            if (oddDegreeNodes.size() == 2) {
//                Node oddNode0 = oddDegreeNodes.get(0);
//                Node oddNode1 = oddDegreeNodes.get(1);
//
//                if (oddNode0.hasEdgeBetween(oddNode1)) {
//                    internalGraph.removeEdge(oddNode0, oddNode1);
//                } else {
//                    internalGraph.addEdge(oddNode0 + "-" + oddNode1, oddNode0.getId(), oddNode1.getId());
//                }
//                break;
//            }

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
                        oddDegreeNodes.add(evenDegreeNode);
                        evenDegreeNodes.add(oddNode);
                        break;
                    }
                }
            }
        }


    }


    private  <T extends Element> void debug(long millis, List<T> ...  lists) {
        try {
            for (List<T> list : lists) System.out.println(Arrays.toString(list.toArray()));
            internalGraph.display();
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    @Override
    public boolean nextEvents() {
        return false;
    }
}
