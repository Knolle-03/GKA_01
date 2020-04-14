package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.algorithms.MyDijkstra;
import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.conversion.ListFromGraph;
import de.hawh.ld.GKA01.io.FileReader;
import de.hawh.ld.GKA01.io.FileWriter;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {


        int nodeCount = 1000;


        Graph rndGraph = new SingleGraph("rndGraph");
        Generator generator = new RandomGenerator(2);
        generator.addSink(rndGraph);
        generator.begin();
        for (int j = 0; j < nodeCount ; j++) {
            generator.nextEvents();
        }
        generator.end();

        Random random = new Random();
        int firstID = random.nextInt(nodeCount);
        int secondID = random.nextInt(nodeCount);


        for (Edge edge : rndGraph.getEachEdge()) {
            edge.addAttribute("weight", random.nextDouble() );
        }



        rndGraph.addAttribute("ui.antialias");



        MyDijkstra myDijkstra = new MyDijkstra(rndGraph);
        myDijkstra.compute(rndGraph.getNode(firstID));





        List<Node> path = myDijkstra.getPath(rndGraph.getNode(secondID));

        System.out.println("mine: " + path);



        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, null);


        dijkstra.init(rndGraph);
        dijkstra.setSource(rndGraph.getNode(firstID));
        dijkstra.compute();


        Iterator<Path> pathIterator = dijkstra.getAllPathsIterator(rndGraph.getNode(secondID));

        while (pathIterator.hasNext()) {
            System.out.println("Ref: " + pathIterator.next());
        }















































//        for (int i = 8; i <= 8; i++) {
//            String number = String.format("%02d", i);
//            String fileNameRead = "resources/givenGraphs/graph" + number + ".graph";
//
//            String fileNameWritten = "resources/writtenGraphs/graph" + number + ".graph";
//            List<String> readLines = FileReader.readLines(fileNameRead);
//            Graph graph = GraphFromList.populateGraph(readLines, fileNameRead);
//            List<String> writtenLines = ListFromGraph.getFileLines(graph);
//            //System.out.println(writtenLines.get(0));
//            FileWriter.writeLines(writtenLines, fileNameWritten);

            //System.out.println(graph.getEdge("ee").isLoop());
//            System.out.println("\n" + graph.getId());
//            switch (i) {
//                case 1 : shortestPath(graph.getNode("a"), graph.getNode("h"));
//                    break;
//                case 2 : shortestPath(graph.getNode("a"), graph.getNode("i"));
//                    break;
//                case 3 : shortestPath(graph.getNode("Oldenburg"), graph.getNode("Detmold"));
//                    break;
//                case 4 : shortestPath(graph.getNode("v3"), graph.getNode("v6"));
//                    break;
//                case 5 : shortestPath(graph.getNode("v1"), graph.getNode("v7"));
//                    break;
//                case 6 : shortestPath(graph.getNode("1"), graph.getNode("12"));
//                    break;
//                case 7 : shortestPath(graph.getNode("v4"), graph.getNode("v7"));
//                    break;
//                case 8 : shortestPath(graph.getNode("v1"), graph.getNode("v16"));
//                    break;
//                case 9 : shortestPath(graph.getNode("a"), graph.getNode("c"));
//                    break;
//                case 10 : shortestPath(graph.getNode("v3"), graph.getNode("v10"));
//                    break;
//                default:
//                    System.out.println("No such graph available");
//            }
//        }
    }

}
