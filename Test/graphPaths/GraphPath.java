package graphPaths;



// paths to graph files to use in the test classes
public enum GraphPath {

    READ_GRAPH_00_PATH("Test/testResources/givenGraphs/graph00.graph"),
    READ_GRAPH_01_PATH("Test/testResources/givenGraphs/graph01.graph"),
    READ_GRAPH_03_PATH("Test/testResources/givenGraphs/graph03.graph"),
    READ_GRAPH_06_PATH("Test/testResources/givenGraphs/graph06.graph"),

    WRITE_GRAPH_00_PATH("Test/testResources/writtenGraphs/graph00.graph"),
    WRITE_GRAPH_01_PATH("Test/testResources/writtenGraphs/graph01.graph"),
    WRITE_GRAPH_03_PATH("Test/testResources/writtenGraphs/graph03.graph"),
    WRITE_GRAPH_06_PATH("Test/testResources/writtenGraphs/graph06.graph");

    String path;

    GraphPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

}
