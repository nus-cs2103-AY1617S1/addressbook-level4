package seedu.flexitrack.logic.commands;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
 
//@@author A0138455Y
/**
 * 
 * Change current storage to other place
 * Limitation : only allow user to change storage within FlexiTrack folder
 */
public class ChangeStoragePathCommand extends Command{

    public static final String COMMAND_WORD = "cs";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the storage directory of FlexiTrack.\n"
            + "Parameters: DIRECTORY"
            + "Example: " + COMMAND_WORD
            + " /Documents/newFolder/tasktracker";

    public static final String MESSAGE_SUCCESS = "Storage location changed: %1$s";
    public static final String INVALID_VALUE = "Invalid path input! Please enter a valid path!";
    
    final String storagePath;

    /**
     * taking new storage path
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public ChangeStoragePathCommand(String newPath) {
        this.storagePath = newPath;
    }

    @Override
    public CommandResult execute() {
        model.changeStorage(storagePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.storagePath));
    }
}
