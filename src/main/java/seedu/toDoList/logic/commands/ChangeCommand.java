package seedu.toDoList.logic.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

//@@author A0146123R
/**
 * Changes the default storage location of the task manager.
 */
public class ChangeCommand extends Command {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the default storage location to the specified location (must end with the file type extension, .xml)"
            + " and clear data saved in the previous location if specified.\n" + "Parameters: FILE_PATH [clear]\n"
            + "Example: " + COMMAND_WORD + " /Desktop/folder/toDoList.xml clear";

    public static final String MESSAGE_CHANGE_SUCCESS = "Storage location changed: %1$s";
    public static final String MESSAGE_INVALID_FILE_PATH = "The file path provided is invalid."
            + " It must end with the file type extension, .xml";
    public static final String MESSAGE_INVALID_CLEAR_DATA = "The clear data argument provided is invalid."
            + " It must be absent or \"clear\".";

    private static final String CLEAR = "clear";
    private static final String EMPTY = "";
    private static final String XML = ".xml";

    private final String filePath;
    private final String clear;
    private final boolean isToClearOld;

    /**
     * Convenience constructor using raw values.
     */
    public ChangeCommand(String filePath, String clear) {
        this.filePath = filePath;
        this.clear = clear;
        this.isToClearOld = true;
    }

    public ChangeCommand(String filePath) {
        this.filePath = filePath;
        this.clear = EMPTY;
        this.isToClearOld = false;
    }

    @Override
    public CommandResult execute() {
        assert filePath != null;
        assert clear != null;

        if (!isValidPath(filePath)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_INVALID_FILE_PATH);
        }
        if (!isValidClear(clear)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_INVALID_CLEAR_DATA);
        }
        model.updateTaskManager(filePath, isToClearOld);
        return new CommandResult(String.format(MESSAGE_CHANGE_SUCCESS, filePath));
    }

    /**
     * Returns true if the given path is a valid path
     */
    private boolean isValidPath(String path) {
        try {
            Paths.get(path);
            return isXml(path);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    /**
     * Returns true if the given path ends with the file type extension, .xml.
     */
    private static boolean isXml(String path) {
        return path.endsWith(XML);
    }

    /**
     * Returns true if the given clear data argument is valid.
     */
    private static boolean isValidClear(String clear) {
        return clear.isEmpty() || clear.equals(CLEAR);
    }

}
