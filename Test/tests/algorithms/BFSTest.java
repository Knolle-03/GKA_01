package tests.algorithms;

import de.hawh.ld.GKA01.algorithms.BFS;
import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static graphPaths.GraphPath.*;
import static org.junit.jupiter.api.Assertions.*;

public class BFSTest {

    private Graph graph01;
    private Graph graph03;
    private Graph graph06;

    @BeforeEach
    public void setUp() {

        graph01 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_01_PATH.getPath()), READ_GRAPH_01_PATH.getPath());
        graph03 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_03_PATH.getPath()), READ_GRAPH_03_PATH.getPath());
        graph06 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_06_PATH.getPath()), READ_GRAPH_06_PATH.getPath());
    }


    @Test
    void breadthFirstSearch() {
        //positive
        BFS.breadthFirstSearch(graph01.getNode("a"), graph01.getNode("h"));
        assertEquals(Integer.valueOf(2), graph01.getNode("h").getAttribute("step"));

        assertTrue(BFS.breadthFirstSearch(graph03.getNode("Oldenburg"), graph03.getNode("Detmold")));
        assertEquals(Integer.valueOf(7), graph03.getNode("Detmold").getAttribute("step"));

        assertTrue(BFS.breadthFirstSearch(graph06.getNode("1"), graph06.getNode("6")));
        assertEquals(Integer.valueOf(2), graph06.getNode("6").getAttribute("step"));

        //negative
        assertFalse(BFS.breadthFirstSearch(graph03.getNode("Oldenburg"), graph03.getNode("Kiel")));

        assertFalse(BFS.breadthFirstSearch(graph03.getNode("Oldenburg"), graph01.getNode("a")));

        assertFalse(BFS.breadthFirstSearch(graph06.getNode("1"), graph06.getNode("12")));

    }

    @Test
    void testBreadthFirstSearchWithNullValues() {

        String expectedMsg = "One or both nodes are null";


        Throwable exception01 = assertThrows( NullPointerException.class,() -> BFS.breadthFirstSearch(graph03.getNode("Oldenburg"), graph03.getNode(null)));
        assertEquals(expectedMsg, exception01.getMessage());

        Throwable exception02 = assertThrows( NullPointerException.class,() -> BFS.breadthFirstSearch(graph03.getNode(null), graph03.getNode(null)));
        assertEquals(expectedMsg, exception02.getMessage());

        Throwable exception03 = assertThrows( NullPointerException.class,() -> BFS.breadthFirstSearch(null, null));
        assertEquals(expectedMsg, exception03.getMessage());

    }


}
