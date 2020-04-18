package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.util.Stopwatch;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Random;


public class Main {

    public static void main(String[] args) {


        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        System.out.println("generating graph...");
        int nodeCount = 5_500_000;
        Graph rndGraph = new SingleGraph("rndGraph");
        Generator generator = new DorogovtsevMendesGenerator();
        generator.addSink(rndGraph);
        generator.begin();
        for (int j = 0; j < nodeCount; j++) {
            generator.nextEvents();
        }
        generator.end();

        Random random = new Random();
        int firstID = random.nextInt(nodeCount);
        int secondID = random.nextInt(nodeCount);


        for (Edge edge : rndGraph.getEachEdge()) {
            edge.addAttribute("weight", random.nextInt(1000));
            edge.addAttribute("ui.label", edge.getAttribute("weight").toString());
        }

        for (Node node : rndGraph) {
            node.addAttribute("ui.label", node.getId());
        }

        stopwatch.stop();

        System.out.println("Graph generated in: " + stopwatch.elapsedTime());

        stopwatch.reset();


        String css = "edge.used {size:10px;fill-color:yellow;}" +
                     "edge.unused {size:2px;fill-color:black;}";

        stopwatch.start();
        Kruskal gsKruskal = new Kruskal();
        gsKruskal.init(rndGraph);
        gsKruskal.compute();
        stopwatch.stop();
        System.out.println("gsWeight: " + gsKruskal.getTreeWeight());
        System.out.println("gsKruskal time: " + stopwatch.elapsedTime());
        gsKruskal.clear();

        stopwatch.reset();

        stopwatch.start();
        de.hawh.ld.GKA01.algorithms.Kruskal myKruskal = new de.hawh.ld.GKA01.algorithms.Kruskal();
        myKruskal.init(rndGraph);
        myKruskal.compute();
        stopwatch.stop();
        System.out.println("myWeight: " + myKruskal.getTreeWeight());
        System.out.println("myKruskal time: " + stopwatch.elapsedTime());
































//        int fail = 0;
//        int win = 0;
//        int noConnection = 0;
//        long start = System.currentTimeMillis();
//
//
//        for (int i = 0; i < 1_000_000 ; i++) {
//
//
//
//
//
//              TODO RANDOM GRAPH HERE
//
//
//
//            //-------------------------------------------------------------
//
//            if (BFS.breadthFirstSearch(rndGraph.getNode(firstID), rndGraph.getNode(secondID))) {
//                //System.out.println("BFS true");
//
//                rndGraph.addAttribute("ui.antialias");
//
//
//                MyDijkstra myDijkstra = new MyDijkstra(rndGraph);
//                myDijkstra.compute(rndGraph.getNode(firstID));
//
//
//                List<Node> path = myDijkstra.getPath(rndGraph.getNode(secondID));
//
//                //System.out.println("mee: " + path);
//
//
//                Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, "distanceFromSource", "weight");
//                dijkstra.init(rndGraph);
//                //System.out.println("Init Ref algo");
//                dijkstra.setSource(rndGraph.getNode(firstID));
//                //System.out.println("Set source for ref algo");
//                dijkstra.compute();
//
//
//                //System.out.println("FirstID: " + firstID + "\n" + "SecondID: " + secondID);
//                MyDijkstra.Data data = rndGraph.getNode(secondID).getAttribute("data");
//
//
////
////                System.out.println("MyDijkstra: " + data.getDistance());
////                System.out.println(dijkstra.getPathLength(rndGraph.getNode(secondID)));
//
//
//
//
//                if (data.getDistance() == dijkstra.getPathLength(rndGraph.getNode(secondID))){
//                    win++;
//                } else {
//                    System.out.println("FAIL!!!elfeins1");
//                    fail++;
//                    rndGraph.display();
//                }
//
//            } else {
//                //System.out.println("BFS false");
//                noConnection++;
//            }
//
//
//            if (i  % 10 == 0) System.out.println(i);
//
//        }
//        long finish = System.currentTimeMillis();
//
//        long time = finish - start;
//
//        System.out.println("win: " + win + "\n" + "fail: " + fail + "\n");
//        System.out.println("noConn: " + noConnection);
//
//        long hours = time / 3600000;
//        long hoursRemainder = time % 3600000;
//
//        long minutes = hoursRemainder / 60000;
//        long minutesRemainder = hoursRemainder % 60000;
//
//        long seconds = minutesRemainder / 1000;
//        long millis = minutesRemainder % 1000;
//
//
//        System.out.println("hours: " + hours);
//        System.out.println("minutes: " + minutes);
//        System.out.println("seconds: " + seconds);
//        System.out.println("millis: " + millis);
//
//        System.out.println("time: " + time);
//        System.out.printf("time elapsed :: %02d:%02d:%02d :: %d ", hours, minutes, seconds, millis );




        //-------------------------------------------------------------


        //        Iterator<Path> pathIterator = dijkstra.getAllPathsIterator(rndGraph.getNode(secondID));




//        if (pathIterator.hasNext()) {
//            while (pathIterator.hasNext()) {
//                System.out.println("Ref: " + pathIterator.next());
//            }
//        } else {
//            System.out.println("No path available");
//        }
//        System.out.println("Ref alg done");
        //-------------------------------------------------------------



    }

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

