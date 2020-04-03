package de.hawh.ld.GKA01.main;

import de.hawh.ld.GKA01.conversion.ListFromGraph;
import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;
import de.hawh.ld.GKA01.io.FileWriter;
import org.graphstream.graph.Graph;

import java.util.List;

import static de.hawh.ld.GKA01.algorithms.ShortestPath.shortestPath;


public class Main {

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            String number = String.format("%02d", i);
            String fileNameRead = "resources/writtenGraphs/graph" + number + ".graph";

            String fileNameWritten = "resources/writtenWrittenGraphs/graph" + number + ".graph";
            List<String> readLines = FileReader.readLines(fileNameRead);
            Graph graph = GraphFromList.populateGraph(readLines, fileNameRead);
            List<String> writtenLines = ListFromGraph.getFileLines(graph);
            //System.out.println(writtenLines.get(0));
            FileWriter.writeLines(writtenLines, fileNameWritten);



//            graph.addAttribute("ui.antialias");
//            graph.display();



            System.out.println("\n" + graph.getId());
            switch (i) {
                case 1 : shortestPath(graph.getNode("a"), graph.getNode("h"));
                    break;
                case 2 : shortestPath(graph.getNode("a"), graph.getNode("i"));
                    break;
                case 3 : shortestPath(graph.getNode("Oldenburg"), graph.getNode("Detmold"));
                    break;
                case 4 : shortestPath(graph.getNode("v3"), graph.getNode("v6"));
                    break;
                case 5 : shortestPath(graph.getNode("v1"), graph.getNode("v7"));
                    break;
                case 6 : shortestPath(graph.getNode("1"), graph.getNode("12"));
                    break;
                case 7 : shortestPath(graph.getNode("v4"), graph.getNode("v7"));
                    break;
                case 8 : shortestPath(graph.getNode("v1"), graph.getNode("v16"));
                    break;
                case 9 : shortestPath(graph.getNode("a"), graph.getNode("c"));
                    break;
                case 10 : shortestPath(graph.getNode("v3"), graph.getNode("v10"));
                    break;
                default:
                    System.out.println("No such graph available");
            }








        }
    }

}