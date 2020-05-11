package tests.util;


import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;
import org.apache.commons.io.FilenameUtils;
import org.graphstream.graph.Graph;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//  This class converts all *.graph files in a given directory into a List of Graphs
public  class GraphReader {


    public static List<Graph> getGraphsInDirectory(final File folder) {
        List<Graph> testGraphs = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isFile() && FilenameUtils.getExtension(String.valueOf(fileEntry)).equals("graph")) {
                testGraphs.add(GraphFromList.populateGraph(FileReader.readLines(fileEntry.toString()), fileEntry.toString()));
            }
        }

        return testGraphs;
    }
}
