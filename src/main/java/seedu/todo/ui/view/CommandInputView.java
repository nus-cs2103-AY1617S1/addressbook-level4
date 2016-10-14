package seedu.todo.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.commons.util.TextAreaResizerUtil;
import seedu.todo.logic.Logic;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;

import java.util.logging.Logger;

public class CommandInputView extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandInputView.class);
    private static final String FXML = "CommandInputView.fxml";
    private static final String ERROR_STYLE = "error";

    private AnchorPane placeHolderPane;
    private AnchorPane commandInputPane;

    @FXML
    private TextArea commandTextField;

    public static CommandInputView load(Stage primaryStage, AnchorPane commandBoxPlaceholder) {
        
        CommandInputView commandInputView = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandInputView());
        commandInputView.addToPlaceholder();
        commandInputView.configure();
        return commandInputView;
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandInputPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    private void configure() {
        setCommandInputHeightAutoResizeable();
        listenToCommandInput();
    }

    /**
     * Allow {@link #commandTextField} to adjust automatically with the height of the content of the text area itself.
     */
    private void setCommandInputHeightAutoResizeable() {
        new TextAreaResizerUtil().setResizable(commandTextField);
    }

    /**
     * Sets {@link #commandTextField} to listen out for a command.
     * Once a command is received, calls {@link #submitCommandInput(String)} to submit that command.
     */
    private void listenToCommandInput() {
        this.commandTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitCommandInput(commandTextField.getText());
                event.consume(); //To prevent commandTextField from printing a new line.
            }
        });
    }





    /* UI Methods */

    /**
     * Indicate an error visually on the {@link #commandTextField}
     */
    public void flagError() {
        commandTextField.getStyleClass().add(ERROR_STYLE);
    }

    /**
     * Remove the error flag visually on the {@link #commandTextField}
     */
    public void unflagError() {
        commandTextField.getStyleClass().remove(ERROR_STYLE);
    }

    /* Override Methods */
    @Override
    public void setNode(Node node) {
        commandInputPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    /*Interface Declarations*/
    /**
     * Defines an interface for controller class to receive a command from this view class, and process it.
     */
    public interface CommandCallback {
        void onCommandReceived(String command);
    }
}
