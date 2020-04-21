package tests.algorithms.shortest_paths;

import de.hawh.ld.GKA01.algorithms.shortestPaths.ShortestPath;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortestPathTest {

    private final int RESP_IN_RANDOM_GRAPH = 100;

    ShortestPath shortestPath = new ShortestPath();
    Dijkstra dijkstraForRandomGraph = new Dijkstra(Dijkstra.Element.NODE, null, null);
    Graph randomGraph;

    int firstID;
    int secondID;
    int nodeCount;
    boolean pathIncluded;
    Random random;

    @BeforeEach
    public void setUp() {
        random = new Random();
        nodeCount = 500_000;
        firstID = random.nextInt(nodeCount);

        randomGraph = new MultiGraph("Random");
        Generator gen = new DorogovtsevMendesGenerator();
        gen.addSink(randomGraph);
        gen.begin();
        for(int i=0; i< nodeCount; i++)
            gen.nextEvents();
        gen.end();

    }


    @Test
    void shortestPath() {
        dijkstraForRandomGraph.init(randomGraph);
        dijkstraForRandomGraph.setSource(randomGraph.getNode(firstID));
        dijkstraForRandomGraph.compute();

        for (int i = 0; i < RESP_IN_RANDOM_GRAPH; i++) {

            pathIncluded = false;

            shortestPath.init(randomGraph, randomGraph.getNode(firstID), randomGraph.getNode(secondID));
            shortestPath.compute();

            dijkstraForRandomGraph.getPath(randomGraph.getNode(secondID));

            Iterator<Path> pathIterator = dijkstraForRandomGraph.getAllPathsIterator(randomGraph.getNode(secondID));

            while (pathIterator.hasNext()) {
                Path path = pathIterator.next();
                if (path.getNodeSet().equals(shortestPath.getShortestPath())) {
                    pathIncluded = true;
                    break;
                }
            }

            assertTrue(pathIncluded);

            secondID = random.nextInt(nodeCount);
            shortestPath.clear();
        }
    }


}
