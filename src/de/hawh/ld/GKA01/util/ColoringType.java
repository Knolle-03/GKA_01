package de.hawh.ld.GKA01.util;

public enum ColoringType {

    MINIMAL_SPANNING_TREE("resources\\MST_StyleSheet"),
    POSSIBLE_OTHER_SHEET_PATH("resources\\possibleOtherSheetPath");

    private final String path;

    ColoringType(String styleSheetPath) {
        this.path = styleSheetPath;
    }

    public String getPath() {
        return path;
    }

}
