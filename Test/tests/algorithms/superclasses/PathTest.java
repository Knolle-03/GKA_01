package tests.algorithms.superclasses;

import de.hawh.ld.GKA01.algorithms.shortestPaths.ShortestPath;
import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import tests.util.TestGraphGenerator;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PathTest {


    private static final int nodeCount = 1_000_000;
    private static final int edgeCount = 2_000_000;
    private final TestGraphGenerator testGraphGenerator = new TestGraphGenerator(nodeCount, new DorogovtsevMendesGenerator(), new OwnRandomGenerator(nodeCount, edgeCount), new RandomGenerator());
    private final List<Graph> testGraphs = testGraphGenerator.generateNonWeightedTestGraphs();
    private final Random rng = new Random();
    Dijkstra dijkstraForRandomGraph = new Dijkstra(Dijkstra.Element.EDGE, null, null);
    public static final int REPS_IN_RANDOM_GRAPH = 1;
    Node source;
    Node target;
    boolean pathIncluded;


    public void shortestPath(ShortestPath shortestPath) {

        for (Graph graph : testGraphs) {

            source = graph.getNode(rng.nextInt(graph.getNodeCount()));
            target = graph.getNode(rng.nextInt(graph.getNodeCount()));

            dijkstraForRandomGraph.init(graph);
            dijkstraForRandomGraph.setSource(source);
            dijkstraForRandomGraph.compute();


            for (int i = 0; i < REPS_IN_RANDOM_GRAPH; i++) {


                pathIncluded = false;

                shortestPath.init(graph);
                shortestPath.setSource(source);
                shortestPath.setTarget(target);
                shortestPath.compute();

                dijkstraForRandomGraph.getPath(target);

                Iterator<Path> pathIterator = dijkstraForRandomGraph.getAllPathsIterator(target);

                if (pathIterator.hasNext()) {
                    while (pathIterator.hasNext()) {
                        Path path = pathIterator.next();
                        if (path.getNodeSet().equals(shortestPath.getShortestPath())) {
                            pathIncluded = true;
                            break;
                        }
                    }
                    assertTrue(pathIncluded);
                } else {
                    assertFalse(pathIncluded);
                }

                //if (i % 99 == 1) System.out.println((i + 1) + " reps done");

                target = graph.getNode(rng.nextInt(graph.getNodeCount()));
                shortestPath.clear();
            }

        }
    }

    public void shortestPathInGraphWithOneNode() {
        Graph graph = new SingleGraph("lonelyNode");
        Node lonelyNode = graph.addNode("lonelyNode");

        Dijkstra dijkstra = new Dijkstra();
        dijkstra.init(graph);
        dijkstra.setSource(lonelyNode);
        dijkstra.compute();

        ShortestPath shortestPath = new ShortestPath();
        shortestPath.init(graph);
        shortestPath.setSource(lonelyNode);
        shortestPath.setTarget(lonelyNode);
        shortestPath.compute();
        assertEquals(dijkstra.getPath(lonelyNode).getNodeSet(), shortestPath.getShortestPath());
    }





}
