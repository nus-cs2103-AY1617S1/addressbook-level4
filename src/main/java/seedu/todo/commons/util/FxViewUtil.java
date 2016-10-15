package seedu.todo.commons.util;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

/**
 * Contains utility methods for JavaFX views
 */
public class FxViewUtil {

    public static void applyAnchorBoundaryParameters(Node node, double left, double right, double top, double bottom) {
        AnchorPane.setBottomAnchor(node, bottom);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setTopAnchor(node, top);
    }
    
    /**
     * Hides a specified UI element, and ensures that it does not occupy any space.
     * @author Wang Xien Dong
     */
    public static void setCollapsed(Node node, boolean isCollapsed) {
        node.setVisible(!isCollapsed);
        node.setManaged(!isCollapsed);
    }
    
    /**
     * Set the text to UI element when available, collapse the UI element when not.
     * @author Wang Xien Dong
     */
    public static void displayTextWhenAvailable(Label labelToDisplay, Node nodeToHide, Optional<String> optionalString) {
        if (optionalString.isPresent()) {
            labelToDisplay.setText(optionalString.get());
        } else {
            setCollapsed(nodeToHide, true);
        }
    }
    
    /**
     * Constructs a label view with a dark grey rounded background.
     * @author Wang Xien Dong
     */
    public static Label constructRoundedText(String text) {
        Label label = new Label();
        label.setText(text);
        label.getStyleClass().add("roundLabel");
        label.setPadding(new Insets(0, 8, 0, 8));
        return label;
    }

    /**
     * Adds only one instance of the class style to the node object
     * @author Wang Xien Dong
     */
    public static void addClassStyle(Node node, String classStyle) {
        if (!node.getStyleClass().contains(classStyle)) {
            node.getStyleClass().add(classStyle);
        }
    }

    /**
     * Removes all instances of the class style
     * @author Wang Xien Dong
     */
    public static void removeClassStyle(Node node, String classStyle) {
        while (node.getStyleClass().contains(classStyle)) {
            node.getStyleClass().remove(classStyle);
        }
    }

}
