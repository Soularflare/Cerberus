package com.project.cerberus.jumble.util;

public class MessageFormatter {
    public static final String HIGHLIGHT_COLOR = "33b5e5";
    private static final String HTML_FONT_COLOR_FORMAT = "<font color=\"#%s\">%s</font>";

    public static String highlightString(String string) {
        return String.format(HTML_FONT_COLOR_FORMAT, new Object[]{HIGHLIGHT_COLOR, string});
    }
}
