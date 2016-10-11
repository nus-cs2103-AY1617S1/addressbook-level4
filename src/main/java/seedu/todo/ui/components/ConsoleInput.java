package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.ui.InputHandler;
import seedu.todo.ui.UiPartLoader;

public class ConsoleInput extends Component {

    private static final String FXML_PATH = "components/ConsoleInput.fxml";

    // Props
    public String lastCommandEntered;
    
    // Input handler
    private InputHandler inputHandler = new InputHandler();
    
    // FXML
    @FXML
    private TextField consoleInputTextField;

    public static ConsoleInput load(Stage primaryStage, Pane placeholderPane) {
        return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new ConsoleInput());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        // Makes ConsoleInput full width wrt parent container.
        FxViewUtil.makeFullWidth(this.mainNode);
    }

    /** ================ ACTION HANDLERS ================== **/
    @FXML
    public void handleConsoleInputChanged() {
        lastCommandEntered = consoleInputTextField.getText();
        inputHandler.processInput(lastCommandEntered);
        consoleInputTextField.clear();
    }
}
