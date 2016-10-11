package seedu.todo.commons.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

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
    
    public static void makeFullWidth(Node node) {
    	applyAnchorBoundaryParameters(node, 0.0, 0.0, 0.0, 0.0);
    }
}
