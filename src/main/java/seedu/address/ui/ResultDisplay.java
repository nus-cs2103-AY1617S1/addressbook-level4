package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Pane> {

    private static final String FXML = "/view/ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    private final StringProperty displayed = new SimpleStringProperty("");

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
    }

    public void postMessage(String message) {
        displayed.setValue(message);
    }

}
