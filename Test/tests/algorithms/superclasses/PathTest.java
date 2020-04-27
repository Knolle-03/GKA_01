package tests.algorithms.superclasses;

import de.hawh.ld.GKA01.algorithms.shortestPaths.ShortestPath;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class PathTest extends GraphTest {

    Dijkstra dijkstraForRandomGraph = new Dijkstra(Dijkstra.Element.EDGE, null, null);
    public static final int REPS_IN_RANDOM_GRAPH = 100;
    Node source;
    Node target;
    boolean pathIncluded;



    public void shortestPath(ShortestPath shortestPath) {
        generateNonWeightedTestGraphs();
        for (Graph graph : testGraphs) {

            source = graph.getNode(random.nextInt(graph.getNodeCount()));
            target = graph.getNode(random.nextInt(graph.getNodeCount()));

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

                if ((i + 1) % 100 == 0) System.out.println(i + " reps done");

                target = graph.getNode(random.nextInt(graph.getNodeCount()));
                shortestPath.clear();
            }

        }
    }

    @Test
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
