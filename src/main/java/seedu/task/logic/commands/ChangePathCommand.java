//@@author A0144939R
package seedu.task.logic.commands;

import java.io.File;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.storage.FilePathChangedEvent;

/**
 * Change the file path
 */
public class ChangePathCommand extends Command{
    
    public static final String COMMAND_WORD = "change-to";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes save/load location for the TaskManager "
            + "Parameters: NEW FILE PATH\n"
            + "Example: " + COMMAND_WORD
            + "taskmanager.xml";
    
    public static final String MESSAGE_PATH_CHANGE_SUCCESS = "Success! New File path: %1$s";
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
    /**
     * Checks if the user defined file path is valid.
     * A file path is defined to be valid if it has a valid parent folder, if it can be written to, and if 
     * it is an xml file.
     * @param newFilePath The user defined file path 
     * @return boolean variable indicating if file path is valid
     */
    private boolean isValidFilePath(String newFilePath) {
        File file = new File(newFilePath);
        System.out.println(file.getParentFile() != null);
        System.out.println(file.canWrite());
        System.out.println(newFilePath.endsWith(".xml"));
        return (file.getParentFile() != null && file.canWrite() && newFilePath.endsWith(".xml"));         
    }
}
