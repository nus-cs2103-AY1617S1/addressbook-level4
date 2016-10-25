package seedu.todo.ui.controller;

import com.google.common.base.Strings;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
/**
 * Automatically resize a text area object such that the height fits the content of the text area
 */
public class TextAreaResizer {
    private final TextArea textArea;
    private final Text pseudoText = new Text();
    
    private double lineHeight;
    private double charWidth;
    private double textAreaWidth;
    private int textAreaChars;
    
    public TextAreaResizer(TextArea textArea) {
        this.textArea = textArea;
        textAreaChars = textArea.getText().length();
        textAreaWidth = textArea.getWidth();
        
        pseudoText.fontProperty().addListener((observable, oldValue, newValue) -> updateCharacterWidth());
        pseudoText.fontProperty().bind(textArea.fontProperty());
        
        textArea.widthProperty().addListener((observable, oldValue, newValue) -> {
            textAreaWidth = (double) newValue;
            updateTextArea();
        });
        
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            textAreaChars = newValue.length();
            updateTextArea();
        });
    }

    private void updateCharacterWidth() {
        final int TEST_CHAR_LEN = 60;

        pseudoText.setText("");
        double initialWidth = pseudoText.getLayoutBounds().getWidth();
        pseudoText.setText(Strings.repeat("w", TEST_CHAR_LEN));
        double newWidth = pseudoText.getLayoutBounds().getWidth();
        charWidth = (newWidth - initialWidth) / TEST_CHAR_LEN;
    }
    
    private void updateTextArea() {
        // Silly hack to make sure lineHeight is available before continuing 
        if (lineHeight == 0) {
            lineHeight = textArea.getLayoutBounds().getHeight();
        }
        
        if (lineHeight == 0) {
            return;
        }
        
        int lines = (int) (((textAreaChars + 5) * charWidth) / textAreaWidth) + 1;
        double height = lines * lineHeight;
        
        Platform.runLater(() -> {
            textArea.setMaxHeight(height);
            textArea.setPrefHeight(height);
        });
    }
}
