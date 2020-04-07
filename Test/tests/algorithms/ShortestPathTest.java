package tests.algorithms;

import de.hawh.ld.GKA01.algorithms.ShortestPath;
import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;
import graphPaths.GraphPaths;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortestPathTest implements GraphPaths {

    private Graph graph01;
    private Graph graph03;
    private Graph graph06;
    
    private List<Node> shortestPathList01;
    private List<Node> shortestPathList03;
    private List<Node> shortestPathList06;

    @BeforeEach
    public void setUp() {

        shortestPathList01 = new ArrayList<>();
        shortestPathList03 = new ArrayList<>();
        shortestPathList06 = new ArrayList<>();

        graph01 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_01_PATH), READ_GRAPH_01_PATH);
        graph03 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_03_PATH), READ_GRAPH_03_PATH);
        graph06 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_06_PATH), READ_GRAPH_06_PATH);

        shortestPathList01.add(graph01.getNode("a"));
        shortestPathList01.add(graph01.getNode("b"));
        shortestPathList01.add(graph01.getNode("h"));

        shortestPathList03.add(graph03.getNode("Oldenburg"));
        shortestPathList03.add(graph03.getNode("Cuxhaven"));
        shortestPathList03.add(graph03.getNode("Bremen"));
        shortestPathList03.add(graph03.getNode("Bremerhaven"));
        shortestPathList03.add(graph03.getNode("Rotenburg"));
        shortestPathList03.add(graph03.getNode("Uelzen"));
        shortestPathList03.add(graph03.getNode("Hameln"));
        shortestPathList03.add(graph03.getNode("Detmold"));

        shortestPathList06.add(graph06.getNode("1"));
        shortestPathList06.add(graph06.getNode("7"));
        shortestPathList06.add(graph06.getNode("6"));
    }



    @Test
    void shortestPath() {
        assertEquals(shortestPathList01, ShortestPath.shortestPath(graph01.getNode("a"), graph01.getNode("h")));

        assertEquals(shortestPathList03, ShortestPath.shortestPath(graph03.getNode("Oldenburg"), graph03.getNode("Detmold")));

        assertEquals(shortestPathList06, ShortestPath.shortestPath(graph06.getNode("1"), graph06.getNode("6")));
    }


}
