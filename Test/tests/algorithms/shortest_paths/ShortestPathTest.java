package tests.algorithms.shortest_paths;

import de.hawh.ld.GKA01.algorithms.shortest_paths.ShortestPath;
import org.junit.jupiter.api.Test;
import tests.algorithms.superclasses.PathTest;


public class ShortestPathTest extends PathTest {

    @Test
    public void shortestPath() {
        super.shortestPath(new ShortestPath());
    }


    @Test
    public void shortestPathInGraphWithOneNode() {
        super.shortestPathInGraphWithOneNode();
    }


}
