package seedu.todo.guitests.guihandles;

import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

/**
 * @@author A0139812A
 */
public class ConsoleHandle extends GuiHandle {

    private static final String CONSOLE_INPUT_ID = "#consoleInputTextField";
    private static final String CONSOLE_TEXT_ID = "#consoleTextArea";
    private static final int COMMAND_WAIT_TIME = 500;

    public ConsoleHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }

    public String getConsoleInputText() {
        return getTextFieldText(CONSOLE_INPUT_ID);
    }

    public String getConsoleTextArea() {
        return getTextAreaText(CONSOLE_TEXT_ID);
    }
    
    public void enterCommand(String command) {
        setTextField(CONSOLE_INPUT_ID, command);
    }

    /**
     * Enters the given command in the ConsoleInputTextField and presses enter.
     */
    public void runCommand(String command) {
        enterCommand(command);
        pressEnter();
        guiRobot.sleep(COMMAND_WAIT_TIME);
    }
}
