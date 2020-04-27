package tests.algorithms.shortest_paths;

import de.hawh.ld.GKA01.algorithms.shortestPaths.ShortestPath;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ShortestPathTest {

    ShortestPath shortestPath = new ShortestPath();
    Dijkstra dijkstraForRandomGraph = new Dijkstra(Dijkstra.Element.EDGE, null, null);
    private static final Generator[] generators = new Generator[] {new BarabasiAlbertGenerator(), new ChvatalGenerator(), new DorogovtsevMendesGenerator(), new RandomGenerator(5),};
    private static final int nodeCount = 1_000_000;
    private static final int WEIGHT_MAX = 20;
    private static final List<Graph> testGraphs = new ArrayList<>();
    private static final int REPS_IN_RANDOM_GRAPH = 1_000;

    int firstID;
    int secondID;
    boolean pathIncluded;
    private static final Random random = new Random();


    public void setUp() {

        for (Generator generator : generators) {

            Graph graph = new MultiGraph(generator.toString());
            generator.addSink(graph);
            generator.begin();
            for (int i = 0; i < nodeCount; i++){
                generator.nextEvents();
                if (i % 50_000 == 0) System.out.println(i + " nodes generated");
            }

            generator.end();


            for (Edge edge : graph.getEachEdge()) {
                edge.addAttribute("weight", random.nextInt(WEIGHT_MAX));
            }

            testGraphs.add(graph);


        }

    }


    @Test
    void shortestPath() {

        setUp();

        for (Graph graph : testGraphs) {

            firstID = random.nextInt(graph.getNodeCount());
            secondID = random.nextInt(graph.getNodeCount());

            dijkstraForRandomGraph.init(graph);
            dijkstraForRandomGraph.setSource(graph.getNode(firstID));
            dijkstraForRandomGraph.compute();



            for (int i = 0; i < REPS_IN_RANDOM_GRAPH; i++) {



                pathIncluded = false;

                shortestPath.init(graph, graph.getNode(firstID), graph.getNode(secondID));
                shortestPath.compute();

                dijkstraForRandomGraph.getPath(graph.getNode(secondID));

                Iterator<Path> pathIterator = dijkstraForRandomGraph.getAllPathsIterator(graph.getNode(secondID));

                if (pathIterator.hasNext()){
                    while (pathIterator.hasNext()) {
                        Path path = pathIterator.next();
                        if (path.getNodeSet().equals(shortestPath.getShortestPath())) {
                            pathIncluded = true;
                            break;
                        }
                    }
                    assertTrue(pathIncluded, "" + graph + " done");
                } else {
                    assertFalse(pathIncluded, "" + graph + " done");
                }




                secondID = random.nextInt(graph.getNodeCount());
                shortestPath.clear();
            }
        }


    }


    @Test
    void shortestPathInGraphWithOneNode() {
        Graph graph = new SingleGraph("lonelyNode");
        Node lonelyNode = graph.addNode("lonelyNode");

        Dijkstra dijkstra = new Dijkstra();
        dijkstra.init(graph);
        dijkstra.setSource(lonelyNode);
        dijkstra.compute();

        ShortestPath shortestPath = new ShortestPath();
        shortestPath.init(graph, lonelyNode, lonelyNode);
        shortestPath.compute();
        assertEquals(dijkstra.getPath(lonelyNode).getNodeSet(), shortestPath.getShortestPath());
    }


}
