package tests.algorithms.shortest_paths;

import de.hawh.ld.GKA01.algorithms.shortest_paths.ShortestPath;
import de.hawh.ld.GKA01.util.generators.OwnRandomGenerator;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.util.TestGraphGenerator;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PathTest {


    public static final int REPS_IN_RANDOM_GRAPH = 1_000;
    private List<Graph> testGraphs;
    private final Random rng = new Random();


    private static Stream<Arguments> algorithms() {
        return Stream.of(
                Arguments.of(new ShortestPath(), new Dijkstra(Dijkstra.Element.EDGE, null, null)));
    }


    @BeforeAll
    void setUp() {
        TestGraphGenerator testGraphGenerator = new TestGraphGenerator(1_000_000, 2, true, new DorogovtsevMendesGenerator(), new OwnRandomGenerator(), new RandomGenerator());
        testGraphs = testGraphGenerator.generateNonWeightedTestGraphs();
    }


    @ParameterizedTest
    @MethodSource("algorithms")
    public void shortestPath(ShortestPath shortestPath, Dijkstra dijkstra) {

        for (Graph graph : testGraphs) {

            Node source = graph.getNode(rng.nextInt(graph.getNodeCount()));
            Node target = graph.getNode(rng.nextInt(graph.getNodeCount()));

            dijkstra.init(graph);
            dijkstra.setSource(source);
            dijkstra.compute();


            for (int i = 0; i < REPS_IN_RANDOM_GRAPH; i++) {


                boolean pathIncluded = false;

                shortestPath.init(graph);
                shortestPath.setSource(source);
                shortestPath.setTarget(target);
                shortestPath.compute();

                dijkstra.getPath(target);

                Iterator<Path> pathIterator = dijkstra.getAllPathsIterator(target);

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

    @ParameterizedTest
    @MethodSource("algorithms")
    public void shortestPathInGraphWithOneNode(ShortestPath shortestPath, Dijkstra dijkstra) {
        Graph graph = new SingleGraph("lonelyNode");
        Node lonelyNode = graph.addNode("lonelyNode");

        dijkstra.init(graph);
        dijkstra.setSource(lonelyNode);
        dijkstra.compute();

        shortestPath.init(graph);
        shortestPath.setSource(lonelyNode);
        shortestPath.setTarget(lonelyNode);
        shortestPath.compute();
        assertEquals(dijkstra.getPath(lonelyNode).getNodeSet(), shortestPath.getShortestPath());
    }





}
