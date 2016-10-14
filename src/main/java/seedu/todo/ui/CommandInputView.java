package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.commons.util.TextAreaResizerUtil;
import seedu.todo.logic.Logic;
import seedu.todo.logic.commands.CommandResult;

import java.util.logging.Logger;

public class CommandInputView extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandInputView.class);
    private static final String FXML = "CommandInputView.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandInputPane;
    private CommandFeedbackView commandFeedbackView;
    String previousCommandText;

    private Logic logic;

    @FXML
    private TextArea commandTextField;

    public static CommandInputView load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
                                        CommandFeedbackView commandFeedbackView, Logic logic) {
        
        CommandInputView commandInputView = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandInputView());
        commandInputView.configure(commandFeedbackView, logic);
        commandInputView.addToPlaceholder();
        return commandInputView;
    }

    public void configure(CommandFeedbackView commandFeedbackView, Logic logic) {
        this.commandFeedbackView = commandFeedbackView;
        this.logic = logic;
        setCommandInputHeightAutoResizeable();
        listenToCommandInput();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandInputPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
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
        this.commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitCommandInput(commandTextField.getText());
                event.consume(); //To prevent commandTextField from printing a new line.
            }
        });
    }

    /**
     * Submits a command to logic, and handle the result of CommandResult with
     * {@link #handleCommandResult(CommandResult)}
     * @param commandText command submitted by user via {@link #commandTextField}
     */
    private void submitCommandInput(String commandText) {
        //Do not execute an empty command. TODO: This check should be done in the parser class.
        if (!StringUtil.isEmpty(commandText)) {
            commandTextField.setDisable(true);
            CommandResult result = logic.execute(previousCommandText);
            handleCommandResult(result);
        }
    }

    /**
     * Handles a CommandResult object, and updates the user interface to reflect the result.
     * @param result produced by Logic class
     */
    private void handleCommandResult(CommandResult result) {
        if (result.isSuccessful()) {
            commandFeedbackView.unFlagError();
            commandTextField.clear();
        } else {
            commandFeedbackView.flagError();
        }
        commandFeedbackView.displayMessage(result.getFeedback());
        commandTextField.setDisable(false);
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
}
