package seedu.todo.ui.util;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

//@@author A0135805H
/**
 * Automatically resize a text area object such that the height fits the content of the text area
 */
public class TextAreaResizerUtil {

    private Text pseudoText; //A text object that will imitate the text in the TextArea object
    private double singleLineHeight;

    /**
     * Set a particular TextArea object to have it's height automatically resize-d.
     */
    public void setResizable(TextArea textArea) {
        bindToMockText(textArea);
        setupTextArea(textArea);
    }

    private void bindToMockText(TextArea textArea) {
        pseudoText = new Text();
        pseudoText.textProperty().bind(textArea.textProperty());
        pseudoText.fontProperty().bind(textArea.fontProperty());
        pseudoText.wrappingWidthProperty().bind(textArea.widthProperty());
    }

    private void setupTextArea(TextArea textArea) {
        ChangeListener<Object> changeListener = (observable, oldValue, newValue) -> {
            double height = getCorrectedHeight();
            Platform.runLater(() -> {
                textArea.setMaxHeight(height);
                textArea.setPrefHeight(height);
            });
        };
        textArea.widthProperty().addListener(changeListener);
        textArea.textProperty().addListener(changeListener);
    }

    /**
     * Gets a stable value of height by eliminating slight jitters in text area height.
     */
    private double getCorrectedHeight() {
        double rawHeight = pseudoText.getLayoutBounds().getHeight();
        if (singleLineHeight == 0) {
            singleLineHeight = rawHeight;
        }
        int numRows = (int) Math.ceil(rawHeight/singleLineHeight);
        return singleLineHeight * (numRows + 1); //10 is added to account for the border of textarea
    }
}