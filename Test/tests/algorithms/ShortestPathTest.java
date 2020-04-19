package tests.algorithms;

import de.hawh.ld.GKA01.algorithms.shortestPaths.ShortestPath;
import de.hawh.ld.GKA01.conversion.GraphFromList;
import de.hawh.ld.GKA01.io.FileReader;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static graphPaths.GraphPath.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShortestPathTest {
    private Graph randomGraph;
    private Graph graph01;
    private Graph graph03;
    private Graph graph06;
    
    private List<Node> shortestPathList01;
    private List<Node> shortestPathList03;
    private List<Node> shortestPathList06;

    int firstID;
    int secondID;

    @BeforeEach
    public void setUp()  {
        Random random = new Random();
        int nodeCount = 500;
        firstID = random.nextInt(nodeCount);
        secondID = random.nextInt(nodeCount);

        randomGraph = new SingleGraph("Random");
        Generator gen = new RandomGenerator(5);
        gen.addSink(randomGraph);
        gen.begin();
        for(int i=0; i< nodeCount; i++)
            gen.nextEvents();
        gen.end();



        shortestPathList01 = new ArrayList<>();
        shortestPathList03 = new ArrayList<>();
        shortestPathList06 = new ArrayList<>();

        graph01 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_01_PATH.getPath()), READ_GRAPH_01_PATH.getPath());
        graph03 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_03_PATH.getPath()), READ_GRAPH_03_PATH.getPath());
        graph06 = GraphFromList.populateGraph(FileReader.readLines(READ_GRAPH_06_PATH.getPath()), READ_GRAPH_06_PATH.getPath());

        shortestPathList01.add(graph01.getNode("a"));
        shortestPathList01.add(graph01.getNode("b"));
        shortestPathList01.add(graph01.getNode("h"));

        shortestPathList03.add(graph03.getNode("Oldenburg"));
        shortestPathList03.add(graph03.getNode("Cuxhaven"));
        shortestPathList03.add(graph03.getNode("Bremen"));
        shortestPathList03.add(graph03.getNode("Bremerhaven"));
        shortestPathList03.add(graph03.getNode("Rotenburg"));
        shortestPathList03.add(graph03.getNode("Uelzen"));
        shortestPathList03.add(graph03.getNode("Hameln"));
        shortestPathList03.add(graph03.getNode("Detmold"));

        shortestPathList06.add(graph06.getNode("1"));
        shortestPathList06.add(graph06.getNode("7"));
        shortestPathList06.add(graph06.getNode("6"));
    }



    @Test
    void shortestPath() {

        boolean pathIncluded = false;
        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, null);
        dijkstra.init(graph01);
        dijkstra.setSource(graph01.getNode("a"));
        dijkstra.compute();
        Iterator<Path> pathIterator = dijkstra.getAllPathsIterator(graph01.getNode("h"));
        while (pathIterator.hasNext()) {
            if (pathIterator.next().getNodeSet().equals(shortestPathList01)){
                pathIncluded = true;
                break;
            }
        }
        dijkstra.clear();
        assertTrue(pathIncluded);
        assertEquals(shortestPathList01, ShortestPath.shortestPath(graph01.getNode("a"), graph01.getNode("h")));
        pathIncluded = false;



        Dijkstra dijkstra3 = new Dijkstra(Dijkstra.Element.EDGE, null, null);
        dijkstra3.init(graph03);
        dijkstra3.setSource(graph03.getNode("Oldenburg"));
        dijkstra3.compute();
        pathIterator = dijkstra3.getAllPathsIterator(graph03.getNode("Detmold"));
        while (pathIterator.hasNext()) {
            Path path = pathIterator.next();
            System.out.println(path);
            if (path.getNodeSet().equals(shortestPathList03)){
                pathIncluded = true;
                break;
            }
        }
        dijkstra3.clear();
        assertTrue(pathIncluded);
        assertEquals(shortestPathList03, ShortestPath.shortestPath(graph03.getNode("Oldenburg"), graph03.getNode("Detmold")));
        pathIncluded = false;



        Dijkstra dijkstra6 = new Dijkstra(Dijkstra.Element.EDGE, null, null);
        dijkstra6.init(graph06);
        dijkstra6.setSource(graph06.getNode("1"));
        dijkstra6.compute();
        pathIterator = dijkstra6.getAllPathsIterator(graph06.getNode("6"));
        while (pathIterator.hasNext()) {
            if (pathIterator.next().getNodeSet().equals(shortestPathList06)){
                pathIncluded = true;
                break;
            }
        }
        dijkstra6.clear();
        assertTrue(pathIncluded);
        assertEquals(shortestPathList06, ShortestPath.shortestPath(graph06.getNode("1"), graph06.getNode("6")));
        pathIncluded = false;


            Dijkstra dijkstraForRandomGraph = new Dijkstra(Dijkstra.Element.NODE, null, null);
            List<Node> myPath = ShortestPath.shortestPath(randomGraph.getNode(firstID), randomGraph.getNode(secondID));
            System.out.println("myPath: " + myPath);
            dijkstraForRandomGraph.init(randomGraph);
            dijkstraForRandomGraph.setSource(randomGraph.getNode(firstID));
            dijkstraForRandomGraph.compute();
            pathIterator = dijkstraForRandomGraph.getAllPathsIterator(randomGraph.getNode(secondID));

            if (myPath.isEmpty() && pathIterator.next() == null) {
                assertFalse(false);
            }
            while (pathIterator.hasNext()) {
                Path path = pathIterator.next();
                System.out.println("Path: " + path);
                if (path.getNodeSet().equals(myPath)) {

                    pathIncluded = true;
                    break;

                }
            }
            assertTrue(pathIncluded);



            //randomGraph.display();
            //Thread.sleep(10000);
    }


}
