package seedu.todo.commons.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    public static Label constructRoundedText(String string) {
        Label label = constructLabel(string, "roundLabel");
        label.setPadding(new Insets(0, 8, 0, 8));
        return label;
    }

    /**
     * Generates a {@link Label} object with the class style applied onto the object.
     * @param string to be wrapped in the {@link Label} object
     * @param classStyle css style to be applied to the label
     * @return a {@link Label} object
     */
    public static Label constructLabel(String string, String classStyle) {
        Label label = new Label(string);
        addClassStyle(label, classStyle);
        return label;
    }

    /**
     * Generates a {@link Text} object with the class style applied onto the object.
     * @param string to be wrapped in the {@link Text} object
     * @param classStyle css style to be applied to the label
     * @return a {@link Text} object
     */
    public static Text constructText(String string, String classStyle) {
        Text text = new Text(string);
        addClassStyle(text, classStyle);
        return text;
    }

    /**
     * Place all the specified texts into a {@link TextFlow} object.
     */
    public static TextFlow placeIntoTextFlow(Text... texts) {
        return new TextFlow(texts);
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
