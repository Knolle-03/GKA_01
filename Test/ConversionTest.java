import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class ConversionTest {



    private Graph graph0;
    private Graph graph1;
    private Graph graph3;
    private Graph graph6;


    private List<String> lines0;
    private List<String> lines1;
    private List<String> lines3;
    private List<String> lines6;


    @BeforeEach
    void setUp() {
        String fileName0 = "testResources/givenGraphs/graph00.graph";
        String fileName1 = "testResources/givenGraphs/graph01.graph";
        String fileName3 = "testResources/givenGraphs/graph03.graph";
        String fileName6 = "testResources/givenGraphs/graph06.graph";


        lines0 = FileReader.readLines(fileName0);
        lines1 = FileReader.readLines(fileName1);
        lines3 = FileReader.readLines(fileName3);
        lines6 = FileReader.readLines(fileName6);

        graph0 = GraphFromList.populateGraph(lines0, fileName0);
        graph1 = GraphFromList.populateGraph(lines1, fileName1);
        graph3 = GraphFromList.populateGraph(lines3, fileName3);
        graph6 = GraphFromList.populateGraph(lines6, fileName6);

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

    }
}
