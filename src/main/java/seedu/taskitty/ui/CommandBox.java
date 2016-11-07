package seedu.taskitty.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.taskitty.commons.core.EventsCenter;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.taskitty.commons.events.ui.ShowToolTipEvent;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.commons.util.FxViewUtil;
import seedu.taskitty.logic.Logic;
import seedu.taskitty.logic.commands.*;

import java.util.logging.Logger;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    private String previousCommandText;
    private ImageView catImage;

    private Logic logic;
    private boolean isInputValid;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultDisplay resultDisplay, MainWindow mainWindow, Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, mainWindow, logic);
        commandBox.addToPlaceholder();
        commandBox.setTooltipListener();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, MainWindow mainWindow, Logic logic) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        this.catImage = mainWindow.getCatImage();
        isInputValid = true;
        
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 10, 10);
    }
    
    //@@author A0139930B
    /**
     * Creates a text listener to listen on user input into textbox
     * and give appropriate feedback using the ToolTip class.
     */
    private void setTooltipListener() {
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            EventsCenter.getInstance().post(new ShowToolTipEvent(newValue));
        });
    }
    
    //@@author
    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }


    @FXML
    private void handleCommandInputChanged() {
        //Take a copy of the command text
        previousCommandText = commandTextField.getText();

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        handleCommands(previousCommandText);
    }

    public void handleCommands(String command) {
        setStyleToIndicateCorrectCommand();
        emptyCommandText(command);
        if (isInputValid) {
            setCatImage(ResultDisplay.IMAGE_CAT_HAPPY);
        }
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Invalid command: " + previousCommandText));
        restoreCommandText();
        setStyleToIndicateIncorrectCommand();
    }
    
    //@@author A0139930B
    @Subscribe
    private void handleShowToolTip(ShowToolTipEvent event) {
        resultDisplay.postMessage(event.getToolTipMessage(), event.getToolTipDescription());
        
        isInputValid = event.isUserInputValid();
        if (isInputValid) {
            setStyleToIndicateCorrectCommand();
        } else {
            setStyleToIndicateIncorrectCommand();
        }
    }
    
    /**
     * Save the most recent command and empty the command box text
     */
    private void emptyCommandText(String command) {
        commandTextField.setText("");
        mostRecentResult = logic.execute(command);
    }
    
    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        setCatImage(ResultDisplay.IMAGE_CAT_NORMAL);
        isInputValid = true;
    }

    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(previousCommandText);
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        if (!commandTextField.getStyleClass().contains("error")) {
            commandTextField.getStyleClass().add("error"); 
            setCatImage(ResultDisplay.IMAGE_CAT_SAD);
            isInputValid = false;
        }
    }
    
    private void setCatImage(String imagePath) {
        catImage.setImage(AppUtil.getImage(imagePath));
    }

}
