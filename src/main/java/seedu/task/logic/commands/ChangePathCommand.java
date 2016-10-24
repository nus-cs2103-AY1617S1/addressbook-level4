//@@author A0144939R
package seedu.task.logic.commands;

import java.io.File;

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
    public static final String MESSAGE_PATH_CHANGE_FAIL = "Error, cannot change path to: %1$s";
    
    private final String newFilePath;
    
    public ChangePathCommand(String newFilePath) {
        this.newFilePath = newFilePath.trim();
    }
    
    

    @Override
    public CommandResult execute() {
        if(isValidFilePath(newFilePath)) {
            EventsCenter.getInstance().post(new FilePathChangedEvent(newFilePath, model.getTaskManager()));
            return new CommandResult(true, String.format(MESSAGE_PATH_CHANGE_SUCCESS, newFilePath));
        } else {
            return new CommandResult(false, String.format(MESSAGE_PATH_CHANGE_FAIL, newFilePath));
        }
    }
    
    private boolean isValidFilePath(String newFilePath) {
        File file = new File(newFilePath);
        return (file.getParent() != null && file.canWrite());         
    }



    @Override
    public CommandResult rollback() { 
        EventsCenter.getInstance().post(new FilePathChangedEvent(newFilePath, model.getTaskManager()));
        return null;
    }
    

}
