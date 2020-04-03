import de.hawh.ld.GKA01.Algorithms.BFS;
import de.hawh.ld.GKA01.Algorithms.ShortestPath;
import de.hawh.ld.GKA01.Conversion.GraphFromFile;
import de.hawh.ld.GKA01.IO.FileReader;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;

class GKAGraphTest {
    private String fileName = "resources/givenGraphs/graph06.graph";
    private Graph graph = GraphFromFile.populateGraph(FileReader.readLines(fileName));


    @org.junit.jupiter.api.Test
    void populateGraphFromFile() {
    }

    @org.junit.jupiter.api.Test
    void writeGraphToFile() {
    }

    @org.junit.jupiter.api.Test
    void breadthFirstSearch() throws InterruptedException {
        Deque<Node> deque = new ArrayDeque<>();

        deque.add(graph.getNode("1"));
        deque.add(graph.getNode("7"));
        deque.add(graph.getNode("6"));

        assertEquals(deque, ShortestPath.shortestPath(graph.getNode("1"), graph.getNode("6")));
    }

    @org.junit.jupiter.api.Test
    void shortestPath() {
    }
}