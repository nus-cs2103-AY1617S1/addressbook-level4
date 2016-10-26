package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.ui.InputHandler;
import seedu.todo.ui.UiPartLoader;

public class Console extends Component {

    private static final String FXML_PATH = "components/Console.fxml";
    private static final String INVALID_COMMAND_RESPONSE = "Invalid command!";
    private static final String INVALID_COMMAND_STYLECLASS = "invalid";

    // Props
    public String consoleOutput;
    public String consoleInputValue;
    private String lastCommandEntered;
    
    // Input handler
    private InputHandler inputHandler = InputHandler.getInstance();
    
    // FXML
    @FXML
    private TextField consoleInputTextField;
    @FXML
    private TextArea consoleTextArea;


    public static Console load(Stage primaryStage, Pane placeholderPane) {
        return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new Console());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        // Makes ConsoleInput full width wrt parent container.
        FxViewUtil.makeFullWidth(this.mainNode);
        
        // Set text in ConsoleInput box if provided
        if (consoleInputValue.length() > 0) {
            consoleInputTextField.setText(consoleInputValue);
            
            // Add invalid field css
            consoleInputTextField.getStyleClass().add(INVALID_COMMAND_STYLECLASS);
        } else {
            // Remove invalid field css
            consoleInputTextField.getStyleClass().remove(INVALID_COMMAND_STYLECLASS);
        }
        
        // Load ConsoleDisplay text.
        consoleTextArea.setText(consoleOutput);
    }

    /** ================ ACTION HANDLERS ================== **/
    @FXML
    public void handleConsoleInputKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            String command = inputHandler.getPreviousCommandFromHistory();
            consoleInputTextField.setText(command);
        } else if (event.getCode() == KeyCode.DOWN) {
            String command = inputHandler.getNextCommandFromHistory();
            consoleInputTextField.setText(command);
        }
    }
    
    @FXML
    public void handleConsoleInputChanged() {
        lastCommandEntered = consoleInputTextField.getText();
        
        // Don't change anything if our command is empty.
        if (lastCommandEntered.length() <= 0) {
            return;
        }
        
        assert lastCommandEntered.length() > 0;
        
        boolean isValidCommand = inputHandler.processInput(lastCommandEntered);
        
        if (!isValidCommand) {
            // Show invalid response in Console
            consoleTextArea.setText(INVALID_COMMAND_RESPONSE);
            
            // Set CSS
            consoleInputTextField.getStyleClass().add(INVALID_COMMAND_STYLECLASS);
        } else {
            // Remove console output
            consoleTextArea.setText("");
            
            // Remove CSS
            consoleInputTextField.getStyleClass().remove(INVALID_COMMAND_STYLECLASS);
            
            // Clear input text
            consoleInputTextField.clear();
        }
    }
}
