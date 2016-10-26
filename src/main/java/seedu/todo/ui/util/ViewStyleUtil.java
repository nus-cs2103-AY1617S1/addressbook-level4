package seedu.todo.ui.util;

import javafx.scene.Node;

//@@author A0135805H
/**
 * Deals with the CSS styling of View elements
 */
public class ViewStyleUtil {

    /* Style Classes Constants */
    public static final String STYLE_COLLAPSED = "collapsed";
    public static final String STYLE_COMPLETED = "completed";
    public static final String STYLE_OVERDUE = "overdue";
    public static final String STYLE_SELECTED = "selected";
    public static final String STYLE_TEXT_4 = "text4";
    public static final String STYLE_ERROR = "error";
    public static final String STYLE_CODE = "code";
    public static final String STYLE_BOLDER = "bolder";
    public static final String STYLE_UNDERLINE = "underline";
    public static final String STYLE_ONGOING = "ongoing";

    /*Static Helper Methods*/
    /**
     * Adds only one instance of all the class styles to the node object
     * @param node view object to add the class styles to
     * @param classStyles all the class styles that is to be added to the node
     */
    public static void addClassStyles(Node node, String... classStyles) {
        for (String classStyle : classStyles) {
            addClassStyle(node, classStyle);
        }
    }

    /**
     * Remove all instance of all the class styles to the node object
     * @param node view object to add the class styles to
     * @param classStyles all the class styles that is to be removed from the node
     */
    public static void removeClassStyles(Node node, String... classStyles) {
        for (String classStyle : classStyles) {
            removeClassStyle(node, classStyle);
        }
    }

    /**
     * Adds or removes class style based on a boolean parameter
     * @param isAdding true to add, false to remove
     * @param node view object to add the class styles to
     * @param classStyles all the class styles that is to be added to/removed from the node
     */
    public static void addRemoveClassStyles(boolean isAdding, Node node, String... classStyles) {
        for (String classStyle : classStyles) {
            if (isAdding) {
                addClassStyles(node, classStyle);
            } else {
                removeClassStyles(node, classStyle);
            }
        }
    }

    /**
     * Toggles one style class to the node:
     * If supplied style class is available, remove it. Else, add one instance of it.
     *
     * @return true if toggled from OFF -> ON
     */
    public static boolean toggleClassStyle(Node node, String classStyle) {
        boolean wasPreviouslyOff = !node.getStyleClass().contains(classStyle);
        if (wasPreviouslyOff) {
            addClassStyles(node, classStyle);
        } else {
            removeClassStyles(node, classStyle);
        }
        return wasPreviouslyOff;
    }

    /* Private Helper Methods */
    /**
     * Adds only one instance of a single class style to the node object
     */
    private static void addClassStyle(Node node, String classStyle) {
        if (!node.getStyleClass().contains(classStyle)) {
            node.getStyleClass().add(classStyle);
        }
    }

    /**
     * Removes all instances of a single class style from the node object
     */
    private static void removeClassStyle(Node node, String classStyle) {
        while (node.getStyleClass().contains(classStyle)) {
            node.getStyleClass().remove(classStyle);
        }
    }
}
