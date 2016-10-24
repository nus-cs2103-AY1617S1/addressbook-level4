package seedu.todo.ui.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Optional;

/**
 * Contains generic utility methods for JavaFX views
 */
public class FxViewUtil {

    public static void applyAnchorBoundaryParameters(Node node, double left, double right, double top, double bottom) {
        AnchorPane.setBottomAnchor(node, bottom);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setTopAnchor(node, top);
    }

    //@@author A0135805H
    /**
     * Hides a specified UI element, and ensures that it does not occupy any space.
     */
    public static void setCollapsed(Node node, boolean isCollapsed) {
        node.setVisible(!isCollapsed);
        node.setManaged(!isCollapsed);
    }
    
    /**
     * Set the text to UI element when available, collapse the UI element when not.
     */
    public static void displayTextWhenAvailable(Label labelToDisplay, Node nodeToHide, Optional<String> optionalString) {
        if (optionalString.isPresent()) {
            labelToDisplay.setText(optionalString.get());
        } else {
            labelToDisplay.setText("");
            setCollapsed(nodeToHide, true);
        }
    }

    /**
     * Sets a recurring task on the UI specified in handler to repeat every specified seconds.
     * Does not start until the user executes .play()
     * @param seconds duration between each repeats
     * @param handler method to run is specified here
     * @return {@link Timeline} object to run.
     */
    public static Timeline setRecurringUiTask(int seconds, EventHandler<ActionEvent> handler) {
        Timeline recurringTask = new Timeline(new KeyFrame(Duration.seconds(seconds), handler));
        recurringTask.setCycleCount(Timeline.INDEFINITE);
        return recurringTask;
    }

    /**
     * Converts an index from a list to the index that is displayed to the user via the Ui
     */
    public static int convertToListIndex(int uiIndex) {
        return uiIndex - 1;
    }

    /**
     * Converts an index displayed on the Ui to the user, to the index used on the list
     */
    public static int convertToUiIndex(int listIndex) {
        return listIndex + 1;
    }
}
