package seedu.todoList.logic.commands;

import seedu.todoList.commons.exceptions.IllegalValueException;

//@@author A0144061U
/**
 * Change the storage directory of the application
 */
public class StorageCommand extends Command {

    public static final String COMMAND_WORD = "storage";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the storage directory of the app.\n"
            + "Parameters: DIRECTORY"
            + "Example: " + COMMAND_WORD
            + " /Documents/ShardFolder/TdooData";

    public static final String MESSAGE_SUCCESS = "Storage location changed: %1$s";
    public static final String INVALID_VALUE = "Invalid value";
    
    final String newDirectory;

    /**
     * Change storage directory
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public StorageCommand(String directory) {
           // throws IllegalValueException {
        this.newDirectory = directory;
    }

    @Override
    public CommandResult execute() {
        try {
        	storage.changeStorage(newDirectory);
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.newDirectory));
        } catch (IllegalValueException ive) {
        	return new CommandResult(INVALID_VALUE);
        }

    }
}