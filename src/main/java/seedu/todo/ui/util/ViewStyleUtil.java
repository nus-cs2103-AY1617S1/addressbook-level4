package seedu.todo.ui.util;

import javafx.scene.Node;

/**
 * Deals with the CSS styling of View elements
 */
public class ViewStyleUtil {
    /**
     * Adds only one instance of the class style to the node object
     */
    public static void addClassStyle(Node node, String classStyle) {
        if (!node.getStyleClass().contains(classStyle)) {
            node.getStyleClass().add(classStyle);
        }
    }

    /**
     * Removes all instances of the class style
     */
    public static void removeClassStyle(Node node, String classStyle) {
        while (node.getStyleClass().contains(classStyle)) {
            node.getStyleClass().remove(classStyle);
        }
    }

    /**
     * Toggles the style class:
     *      If supplied style class is available, remove it.
     *      Else, add one instance of it.
     */
    public static void toggleClassStyle(Node node, String classStyle) {
        if (node.getStyleClass().contains(classStyle)) {
            removeClassStyle(node, classStyle);
        } else {
            addClassStyle(node, classStyle);
        }
    }
}
