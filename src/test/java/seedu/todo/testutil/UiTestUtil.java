package seedu.todo.testutil;

import javafx.scene.Node;

/**
 * This class stores all the commonly used helper functions that aids in GUI testing.
 */
public class UiTestUtil {

    /**
     * Checks if the node is displayed on the user interface by checking its properties.
     *
     * @param node The node to be tested for whether it is displayed on the UI.
     * @return Returns true if the node is displayed, false otherwise.
     */
    public static boolean isDisplayed(Node node) {
        //Checks if the node is hidden programatically
        boolean isVisible = node.isVisible();

        //Otherwise, check if the node is hidden with CSS styling
        boolean isCollapsed = parentContainsStyleClass(node, "collapsed");
        boolean isCollapsible = containsStyleClass(node, "collapsible");

        return isVisible || (isCollapsed && isCollapsible);
    }


    public static boolean containsStyleClass(Node node, String styleClass) {
        return node.getStyleClass().contains(styleClass);
    }

    public static boolean parentContainsStyleClass(Node node, String styleClass) {
        return node.getStyleableParent().getStyleClass().contains(styleClass);
    }



}
