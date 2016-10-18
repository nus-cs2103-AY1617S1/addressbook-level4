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
     * Adds or removes class style based on a boolean parameter
     * @param node to add/remove style onto
     * @param classStyle to add to/remove from the node
     * @param isAdding true to add, false to remove
     */
    public static void addRemoveClassStyle(Node node, String classStyle, boolean isAdding) {
        if (isAdding) {
            addClassStyle(node, classStyle);
        } else {
            removeClassStyle(node, classStyle);
        }
    }

    /**
     * Toggles the style class:
     *      If supplied style class is available, remove it.
     *      Else, add one instance of it.
     *  @return true if toggled from OFF -> ON
     */
    public static boolean toggleClassStyle(Node node, String classStyle) {
        boolean wasPreviouslyOff = !node.getStyleClass().contains(classStyle);
        if (wasPreviouslyOff) {
            addClassStyle(node, classStyle);
        } else {
            removeClassStyle(node, classStyle);
        }
        return wasPreviouslyOff;
    }
}
