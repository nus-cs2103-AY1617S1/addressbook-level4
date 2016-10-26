package seedu.address.logic.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * Change the default storage location of the task manager.
 */
public class ChangeCommand extends Command{

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the default storage location to the specified location (must end with the file type extension, .xml)"
            + " and clear data saved in the previous location if specified.\n" 
            + "Parameters: FILE_PATH [clear]\n"
            + "Example: " + COMMAND_WORD
            + " /Desktop/folder/taskManager.xml clear";

    public static final String MESSAGE_CHANGE_SUCCESS = "Storage location has been changed!";
    public static final String MESSAGE_INVALID_FILE_PATH = "The file path provided is invalid."
            + " It must end with the file type extension, .xml";
    public static final String MESSAGE_INVALID_CLEAR_DATA = "The clear data argument provided is invalid.";
    
    private static final String CLEAR = "clear";
    
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
        this.clear = "";
        this.isToClearOld = false;
    }

    @Override
    public CommandResult execute() {
        assert filePath != null;
        assert clear != null;
        
        if (!isValidPath(filePath) || !isXml(filePath)) {
            return new CommandResult(MESSAGE_INVALID_FILE_PATH);
        }
        if (isToClearOld && !isValidClear()) {
            return new CommandResult(MESSAGE_INVALID_CLEAR_DATA);
        }
        model.updateTaskManager(filePath, isToClearOld);
        UndoChangeCommand.undoable = true;
        RedoChangeCommand.isToClearOld = isToClearOld;
        return new CommandResult(MESSAGE_CHANGE_SUCCESS);
    }

    private boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException e) {
            return false;
        }
        return true;
    }
    
    private boolean isXml(String path) {
        return path.endsWith(".xml");
    }
    
    private boolean isValidClear() {
        return clear.equals(CLEAR);
    }
    
}
