//@@author A0144939R
package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.storage.FilePathChangedEvent;

/**
 * Change the file path
 */
public class ChangePathCommand extends UndoableCommand{
    
    public static final String COMMAND_WORD = "change-to";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes save/load location for the TaskManager "
            + "Parameters: NEW FILE PATH\n"
            + "Example: " + COMMAND_WORD
            + "taskmanager.xml";
    
    public static final String MESSAGE_PATH_CHANGE_SUCCESS = "Success! New File path: %1$s";
    public static final String MESSAGE_PATH_CHANGE_ROLLBACK_SUCCESS = "Path change reverted.";
    public static final String MESSAGE_DUPLICATE_PATH = "This is the same path as the one being used.";
    
    private final String newFilePath;
    
    public ChangePathCommand(String newFilePath) {
        this.newFilePath = newFilePath;
    }
    
    

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new FilePathChangedEvent(newFilePath));
        return new CommandResult(true, String.format(MESSAGE_PATH_CHANGE_SUCCESS, newFilePath));
    }

    @Override
    public CommandResult rollback() {
        //send event to Config
        return null;
    }
    

}
