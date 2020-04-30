package de.hawh.ld.GKA01.util.generators;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BaseGenerator;
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
    protected int[] degree;
    private boolean notDone = true;



    public RandomEulerianGenerator() {
        this.nodeCount = 3;
        setUseInternalGraph(true);
        degree = new int[3];
    }


    public RandomEulerianGenerator(int nodeCount) {
        setUseInternalGraph(true);
        this.nodeCount = nodeCount;
        degree = new int[nodeCount];
    }

    public RandomEulerianGenerator(int nodeCount, Random random) {
        this(nodeCount);
        this.random = random;
    }


    @Override
    public void begin() {
        this.random = this.random == null ? new Random(
                System.currentTimeMillis()) : this.random;

        for (int i = 0; i < nodeCount; i++) {
            addNode(Integer.toString(i));
            unconnected.add(internalGraph.getNode(i));
            degree[i] = 0;
        }

        nodeNames = nodeCount;

        // get two random nodes
        Node node0 = Toolkit.randomNode(internalGraph, random);
        Node node1 = Toolkit.randomNode(internalGraph, random);

        //connect those nodes
        addEdge(node0 + "-" + node1, node0.getId(), node1.getId());

        // adjust degree and connected state of nodes
        degree[node0.getIndex()] = 1;
        connected.add(node0);
        degree[node1.getIndex()] = 1;
        connected.add(node1);



    }



    @Override
    public boolean nextEvents() {

        if (notDone) {
//            while (unconnected.size() > 0) {
//                Node nodeInGraph = connected.get(random.nextInt(connected.size()));
//                Node nodeToAdd = unconnected.remove(random.nextInt(unconnected.size()));
//                degree[] += 1;
//                degree[Integer.parseInt(nodeToAdd.getId())] = 1;
//                connected.add(nodeToAdd);
//                addEdge(nodeInGraph + "-" + nodeToAdd, nodeInGraph.getId(), nodeToAdd.getId());
//
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }








            List<Node> oddDegreeNodes = new ArrayList<>();
            List<Node> evenDegreeNodes = new ArrayList<>();
            for (int i = 0; i < degree.length; i++) {
                if (degree[i] % 2 == 1) oddDegreeNodes.add(internalGraph.getNode(i));
                else evenDegreeNodes.add(internalGraph.getNode(i));
            }

            System.out.println(Arrays.toString(oddDegreeNodes.toArray()));
            System.out.println(Arrays.toString(evenDegreeNodes.toArray()));

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (oddDegreeNodes.size() > 0) {
                Node oddNode = oddDegreeNodes.remove(random.nextInt(oddDegreeNodes.size()));
                boolean connectedByNow = false;
                for (Node anotherOddNode : oddDegreeNodes) {
                    if (!oddNode.hasEdgeBetween(anotherOddNode)) {
                        addEdge(oddNode + "-" + anotherOddNode, oddNode.getId(), anotherOddNode.getId());
                        oddDegreeNodes.remove(anotherOddNode);
                        evenDegreeNodes.add(oddNode);
                        evenDegreeNodes.add(anotherOddNode);
                        connectedByNow = true;
                        break;
                    }
                }

                if (!connectedByNow) {
                    for (Node evenDegreeNode : evenDegreeNodes) {
                        if (!oddNode.hasEdgeBetween(evenDegreeNode)) {
                            addEdge(oddNode + "-" + evenDegreeNode, oddNode.getId(), evenDegreeNode.getId());
                            evenDegreeNodes.remove(evenDegreeNode);
                            oddDegreeNodes.add(evenDegreeNode);
                            evenDegreeNodes.add(oddNode);
                            break;
                        }
                    }
                }
            }
            notDone = false;
        }

        return false;
    }
}
