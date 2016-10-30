package seedu.todo.ui.util;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//@@author A0135805H
/**
 * A utility class that generates commonly used UI elements, such as Labels.
 */
public class ViewGeneratorUtil {

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
        ViewStyleUtil.addClassStyles(label, classStyle);
        label.setWrapText(true);
        return label;
    }

    /**
     * Place all the specified labels into a {@link TextFlow} object.
     */
    public static TextFlow placeIntoTextFlow(Label... texts) {
        return new TextFlow(texts);
    }
}
