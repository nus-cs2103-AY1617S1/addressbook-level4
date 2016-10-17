package seedu.todo.commons.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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
            setCollapsed(nodeToHide, true);
        }
    }
    
    /**
     * Constructs a label view with a dark grey rounded background.
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

}
