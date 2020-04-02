package de.hawh.ld.GKA01;

import de.hawh.ld.GKA01.Algorithms.ShortestPath;
import de.hawh.ld.GKA01.Conversion.FileFromGraph;
import de.hawh.ld.GKA01.Conversion.GraphFromFile;
import de.hawh.ld.GKA01.IO.FileReader;
import de.hawh.ld.GKA01.IO.FileWriter;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.util.List;

import static de.hawh.ld.GKA01.Algorithms.ShortestPath.*;


public class Main {

    public static void main(String[] args) {

        for (int i = 6; i <= 6; i++) {
            String number = String.format("%02d", i);
            String fileNameRead = "resources/givenGraphs/graph" + number + ".graph";
            String fileNameWritten = "resources/writtenGraphs/graph" + number + ".graph";

            List<String> readLines = FileReader.readLines(fileNameRead);

            Graph graph = GraphFromFile.populateGraph(readLines);
            graph.addAttribute("ui.antialias");

            graph.display();



            List<String> writeLines = FileFromGraph.linesFromGraph(graph);

            FileWriter.writeToFile(writeLines, fileNameWritten);







        }
    }

}
