import de.hawh.ld.GKA01.algorithms.BFS;
import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;

import org.graphstream.graph.Graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class BFSTest {

    private Graph graph01;
    private Graph graph03;
    private Graph graph06;

    @BeforeEach
    public void setUp() {
        String fileName1= "testResources/givenGraphs/graph01.graph";
        String fileName3 = "testResources/givenGraphs/graph03.graph";
        String fileName6 = "testResources/givenGraphs/graph06.graph";



        graph01 = GraphFromList.populateGraph(FileReader.readLines(fileName1), fileName1);
        graph03 = GraphFromList.populateGraph(FileReader.readLines(fileName3), fileName3);
        graph06 = GraphFromList.populateGraph(FileReader.readLines(fileName6), fileName6);
    }


    @Test
    void breadthFirstSearch() {
        BFS.breadthFirstSearch(graph01.getNode("a"), graph01.getNode("h"));
        assertEquals(Integer.valueOf(2), graph01.getNode("h").getAttribute("step"));

        assertTrue(BFS.breadthFirstSearch(graph03.getNode("Oldenburg"), graph03.getNode("Detmold")));
        assertEquals(Integer.valueOf(7), graph03.getNode("Detmold").getAttribute("step"));

        assertTrue(BFS.breadthFirstSearch(graph06.getNode("1"), graph06.getNode("6")));
        assertEquals(Integer.valueOf(2), graph06.getNode("6").getAttribute("step"));

    }


}
