package de.hawh.ld.GKA01;

import de.hawh.ld.GKA01.Algorithms.ShortestPath;
import de.hawh.ld.GKA01.Conversion.GraphFromFile;
import de.hawh.ld.GKA01.IO.FileReader;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;

import java.util.List;

import static de.hawh.ld.GKA01.Algorithms.ShortestPath.*;


public class Main {

    public static void main(String[] args) {

        for (int i = 3; i <= 3; i++) {
            String number = String.format("%02d", i);
            String fileName = "resources/givenGraphs/graph" + number + ".graph";

            List<String> lines = FileReader.readLines(fileName);

            Graph graph = GraphFromFile.populateGraph(lines);
            graph.addAttribute("ui.antialias");


            shortestPath(graph.getNode("Oldenburg"), graph.getNode("Detmold"));


            graph.display();




        }
    }

}
