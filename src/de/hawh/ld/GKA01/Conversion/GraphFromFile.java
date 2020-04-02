package de.hawh.ld.GKA01.Conversion;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphFromFile {

    private static final String DIRECTED = "# ?directed;";
    private static final String IDENTIFIER = "[A-za-z0-9ÄäÖöÜü]+";
    private static final String ATTRIBUTE = "(:[0-9]+)";
    private static final String EDGE = "( \\([A-Za-z0-9ÄäÖöÜü]+\\))";
    private static final String WEIGHT = "( :: +[0-9]+)";
    private static final String DIGITS = "[0-9]+";
    private static final String FIRST_NODE = IDENTIFIER + ATTRIBUTE + "?";
    private static final String SECOND_NODE_AND_INFO = "(," + IDENTIFIER + ATTRIBUTE + "?" + EDGE + "?" + WEIGHT + "?" + ")? ?";


    private static final Pattern ORIENTATION_PATTERN = Pattern.compile(DIRECTED);
    private static final Pattern VALID_ROW_PATTERN = Pattern.compile(FIRST_NODE + SECOND_NODE_AND_INFO + ";");

    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile(IDENTIFIER);
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(?!" + IDENTIFIER + ")" + ATTRIBUTE);
    private static final Pattern EDGE_PATTERN = Pattern.compile("(?!" + IDENTIFIER + ")" + "(?!" + ATTRIBUTE + ")" + EDGE);
    private static final Pattern WEIGHT_PATTERN = Pattern.compile("(?!" + IDENTIFIER + ")" + "(?!" + ATTRIBUTE + ")" + "(?!" + EDGE + ")" + WEIGHT);
    private static final Pattern DIGITS_PATTERN = Pattern.compile(DIGITS);



    public static Graph populateGraph(List<String> lines) {

        MultiGraph multiGraph = new MultiGraph("multiGraph", false, true);
        List<String> nodes = new ArrayList<>();
        List<String> edges = new ArrayList<>();

        boolean isDirected = isGraphDirected(lines.get(0));


        for (String line : lines) {

            if (VALID_ROW_PATTERN.matcher(line).matches()) {

                String part1;
                String part2 = "";

                if (line.contains(",")) {
                    part1 = line.substring(0, line.indexOf(","));
                    part2 = line.substring(line.indexOf(","));
                } else {
                    part1 = line;
                }

                String node1 = patternFromString(IDENTIFIER_PATTERN, part1);
                String attribute1 = patternFromString(ATTRIBUTE_PATTERN, part1);
                String node2 = patternFromString(IDENTIFIER_PATTERN, part2);
                String attribute2 = patternFromString(ATTRIBUTE_PATTERN, part2);
                String edgeInfo = patternFromString(EDGE_PATTERN, part2);
                String weightInfo = patternFromString(WEIGHT_PATTERN, part2);



                String edge = null;
                if (edgeInfo != null) {
                    edge = patternFromString(IDENTIFIER_PATTERN, edgeInfo);
                }

                String weight = null;
                if (weightInfo != null) {
                    weight = patternFromString(DIGITS_PATTERN, weightInfo);
                }


                multiGraph.addNode(node1);
                if (attribute1 != null) multiGraph.getNode(node1).addAttribute("attributeOne", attribute1);


                if (node2 != null) {
                    multiGraph.addNode(node2);
                    if (attribute2 != null) multiGraph.getNode(node2).addAttribute("attributeTwo", attribute2);

                    multiGraph.addEdge(node1+node2, node1, node2, isDirected);

                    if (edge != null) {
                        // Edge als Attribut hinzufügen

                        multiGraph.getEdge(node1 + node2).addAttribute("ui.label", edge);
                        multiGraph.getEdge(node1 + node2).addAttribute("edge.name", edge);

                        if (weight != null) {
                            multiGraph.getEdge(node1 + node2).setAttribute("weight", Integer.valueOf(weight));
                            multiGraph.getEdge(node1 + node2).addAttribute("ui.label",
                                    multiGraph.getEdge(node1 + node2).getAttribute("ui.label") + " :: " + Integer.valueOf(weight));
                        }
                    } else if (weight != null) {
                        multiGraph.getEdge(node1 + node2).setAttribute("weight", Integer.valueOf(weight));
                        multiGraph.getEdge(node1 + node2).addAttribute("ui.label", Integer.valueOf(weight));
                    }
                }




            }
        }

        for (Node node : multiGraph.getEachNode()) {
            node.addAttribute("ui.label", node.getId());
        }

        return multiGraph;
    }


    private static boolean isGraphDirected (String firstLine) {
        System.out.println(ORIENTATION_PATTERN.matcher(firstLine).matches());
       return ORIENTATION_PATTERN.matcher(firstLine).matches();
    }

    private static String patternFromString(Pattern pattern, String string) {

        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

}
