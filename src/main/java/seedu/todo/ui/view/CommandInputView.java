package seedu.todo.ui.view;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.events.ui.CommandInputEnterEvent;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.TextAreaResizerUtil;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import javax.swing.*;
import java.util.logging.Logger;

//@@author A0315805H
/**
 * A view class that handles the Input text box directly.
 */
public class CommandInputView extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandInputView.class);
    private static final String FXML = "CommandInputView.fxml";

    private AnchorPane placeHolder;
    private AnchorPane commandInputPane;

    @FXML
    private TextArea commandTextField;

    /**
     * Loads and initialise the input view element to the placeHolder
     * @param primaryStage of the application
     * @param placeHolder where the view element {@link #commandInputPane} should be placed
     * @return an instance of this class
     */
    public static CommandInputView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandInputView commandInputView = UiPartLoaderUtil.loadUiPart(primaryStage, placeHolder, new CommandInputView());
        commandInputView.configureLayout();
        commandInputView.configureProperties();
        return commandInputView;
    }

    /**
     * Configure the UI layout of {@link CommandInputView}
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(commandInputPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Configure the UI properties of {@link CommandInputView}
     */
    private void configureProperties() {
        setCommandInputHeightAutoResizeable();
        unflagErrorWhileTyping();
        listenAndRaiseEnterEvent();
    }

    /**
     * Sets {@link #commandTextField} to listen out for keystrokes.
     * Once a keystroke is received, calls {@link KeyStrokeCallback} interface to process this command.
     */
    public void listenToInput(KeyStrokeCallback listener) {
        this.commandTextField.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            KeyCode keyCode = event.getCode();
            String textInput = commandTextField.getText();
            
            boolean isNonEssential = keyCode.isNavigationKey() || 
                    keyCode.isFunctionKey() || 
                    keyCode.isMediaKey() || 
                    keyCode.isModifierKey();
            
            if (!isNonEssential) {
                listener.onKeyStroke(keyCode, textInput);
            }
        });
    }
    
    /**
     * Listens for Enter keystrokes, and raises an event when it happens.
     */
    public void listenAndRaiseEnterEvent() {
        this.commandTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                EventsCenter.getInstance().post(new CommandInputEnterEvent());
                event.consume(); //To prevent commandTextField from printing a new line.
            }
        });
    }

    /* UI Methods */
    /**
     * Resets the text box by {@link #unflagError()} after a key is pressed
     */
    private void unflagErrorWhileTyping() {
        this.commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> unflagError());
    }

    /**
     * Allow {@link #commandTextField} to adjust automatically with the height of the content of the text area itself.
     */
    private void setCommandInputHeightAutoResizeable() {
        new TextAreaResizerUtil().setResizable(commandTextField);
    }

    /**
     * Resets the state of the text box, by clearing the text box and clear the errors.
     */
    public void resetViewState() {
        unflagError();
        clear();
    }

    /**
     * Indicate an error visually on the {@link #commandTextField}
     */
    public void flagError() {
        ViewStyleUtil.addClassStyles(commandTextField, ViewStyleUtil.STYLE_ERROR);
    }

    /**
     * Remove the error flag visually on the {@link #commandTextField}
     */
    private void unflagError() {
        ViewStyleUtil.removeClassStyles(commandTextField, ViewStyleUtil.STYLE_ERROR);
    }

    /**
     * Clears the texts inside the text box
     */
    private void clear() {
        commandTextField.clear();
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
        this.placeHolder = pane;
    }

    /*Interface Declarations*/
    /**
     * Defines an interface for controller class to receive a command from this view class, and process it.
     */
    public interface KeyStrokeCallback {
        void onKeyStroke(KeyCode keyCode, String text);
    }
}
