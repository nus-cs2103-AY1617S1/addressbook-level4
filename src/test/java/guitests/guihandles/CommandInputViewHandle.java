package guitests.guihandles;

import guitests.GuiRobot;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.ui.view.CommandInputView;

//@@author A0135805H
/**
 * A handle to the {@link CommandInputView}'s
 * command text box in the GUI.
 */
public class CommandInputViewHandle extends GuiHandle {
    /* Constants */
    private static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    /**
     * Constructs a handle to the {@link CommandInputView}
     *
     * @param guiRobot {@link GuiRobot} for the current GUI test.
     * @param primaryStage The stage where the views for this handle is located.
     */
    public CommandInputViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /* Interfacing Methods */
    /**
     * Types in the supplied command into the text box in {@link CommandInputView}.
     *      Note: any existing text in the text box will be replaced.
     *      Note: this method will not execute the command. See {@link #runCommand(String)}.
     * @param command Command text to be supplied into the text box.
     */
    public void enterCommand(String command) {
        setTextAreaText(COMMAND_INPUT_FIELD_ID, command);
    }

    /**
     * Gets the command text that is inside the text box in {@link CommandInputView}.
     * @return A command text.
     */
    public String getCommandInput() {
        return getTextAreaText(COMMAND_INPUT_FIELD_ID);
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @param command Command text to be executed.
     */
    public void runCommand(String command) {
        enterCommand(command);
        pressEnter();
        guiRobot.sleep(GUI_SLEEP_DURATION); //Give time for the command to take effect
    }

    /* Text View Helper Methods */
    /**
     * Gets the text stored in a text area given the id to the text area
     *
     * @param textFieldId ID of the text area.
     * @return Returns the text that is contained in the text area.
     */
    private String getTextAreaText(String textFieldId) {
        return ((TextArea) getNode(textFieldId)).getText();
    }

    /**
     * Keys in the given {@code newText} to the specified text area given its ID.
     *
     * @param textFieldId ID for the text area.
     * @param newText Text to be keyed in to the text area.
     */
    private void setTextAreaText(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        TextArea textArea = (TextArea)guiRobot.lookup(textFieldId).tryQuery().get();
        Platform.runLater(() -> textArea.setText(newText));
        guiRobot.sleep(GUI_SLEEP_DURATION); // so that the texts stays visible on the GUI for a short period
    }
}
