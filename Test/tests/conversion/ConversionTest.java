package tests.conversion;

import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.conversion.ListFromGraph;
import de.hawh.ld.GKA01.io.FileReader;
import graphPaths.GraphPaths;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversionTest implements GraphPaths {


    List<String> lines0;
    List<String> lines1;
    List<String> lines3;
    List<String> lines6;

    private Graph graph0;
    private Graph graph1;
    private Graph graph3;
    private Graph graph6;

    List<String> linesFromGraph0;
    List<String> linesFromGraph1;
    List<String> linesFromGraph3;
    List<String> linesFromGraph6;

    @BeforeEach
    void setUp() {

        lines0 = FileReader.readLines(GraphPaths.READ_GRAPH_00_PATH);
        lines1 = FileReader.readLines(GraphPaths.READ_GRAPH_01_PATH);
        lines3 = FileReader.readLines(GraphPaths.READ_GRAPH_03_PATH);
        lines6 = FileReader.readLines(GraphPaths.READ_GRAPH_06_PATH);

        graph0 = GraphFromList.populateGraph(lines0, GraphPaths.READ_GRAPH_00_PATH);
        graph1 = GraphFromList.populateGraph(lines1, GraphPaths.READ_GRAPH_01_PATH);
        graph3 = GraphFromList.populateGraph(lines3, GraphPaths.READ_GRAPH_03_PATH);
        graph6 = GraphFromList.populateGraph(lines6, GraphPaths.READ_GRAPH_06_PATH);


        linesFromGraph0 = ListFromGraph.getFileLines(graph0);
        linesFromGraph1 = ListFromGraph.getFileLines(graph1);
        linesFromGraph3 = ListFromGraph.getFileLines(graph3);
        linesFromGraph6 = ListFromGraph.getFileLines(graph6);

    }


    @Test
    void populateGraph() {

        assertEquals(5 , graph0.getNodeCount());
        assertEquals(0, graph0.getEdgeCount());

        assertEquals(12 , graph1.getNodeCount());
        assertEquals(39, graph1.getEdgeCount());

        assertEquals(22, graph3.getNodeCount());
        assertEquals(39, graph3.getEdgeCount());

        assertEquals( 11, graph6.getNodeCount());
        assertEquals(13, graph6.getEdgeCount());

    }

    @Test
    void getFileLines() {

        assertEquals(lines0, linesFromGraph0);
        assertEquals(lines1, linesFromGraph1);
        assertEquals(lines3, linesFromGraph3);
        assertEquals(lines6, linesFromGraph6);

    }
}
