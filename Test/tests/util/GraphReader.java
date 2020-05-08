package tests.util;


import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;
import org.graphstream.graph.Graph;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//  This class converts all *.graph files in a given directory into a List of Graphs
public  class GraphReader {


    public static List<Graph> getGraphsInDirectory(final File folder) {
        List<Graph> testGraphs = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                testGraphs.add(GraphFromList.populateGraph(FileReader.readLines(fileEntry.toString()), fileEntry.toString()));
            }
        }

        return testGraphs;
    }
}
