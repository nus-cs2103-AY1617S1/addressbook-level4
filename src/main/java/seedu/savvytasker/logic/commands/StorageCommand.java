package seedu.savvytasker.logic.commands;

import seedu.savvytasker.commons.core.EventsCenter;
import seedu.savvytasker.commons.events.storage.DataSavingLocationChangedEvent;
import seedu.savvytasker.model.ReadOnlySavvyTasker;

/**
 * Changes the storage location of Savvy Tasker
 */
public class StorageCommand extends StorageAndModelRequiringCommand {

    public final String path;

    public static final String COMMAND_WORD = "storage";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the storage path to the path specified.\n"
            + "Parameters: PATH\n"
            + "Example: " + COMMAND_WORD + " data/savvytasker.xml";

    public static final String MESSAGE_CHANGE_LOCATION_SUCCESS = "Changed storage location to: %1$s";
    public static final String MESSAGE_CHANGE_LOCATION_FAILED = "Failed to change storage location to: %1$s";

    public StorageCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute() {
        if (storage.setSavvyTaskerFilePath(path)) {
            ReadOnlySavvyTasker savvyTasker = model.getSavvyTasker();
            EventsCenter.getInstance().post(new DataSavingLocationChangedEvent(savvyTasker, path));
            return new CommandResult(String.format(MESSAGE_CHANGE_LOCATION_SUCCESS, path));
        } else {
            return new CommandResult(String.format(MESSAGE_CHANGE_LOCATION_FAILED, path));
        }
    }
    
    //@@author A0097627N
    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * Redo the select command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return false;
    }

    /**
     * Undo the select command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return false;
    }
    
    /**
     * Check if command is an undo command
     * @return true if the command is an undo operation, false otherwise
     */
    @Override
    public boolean isUndo() {
        return false;
    }
    
    /**
     * Check if command is a redo command
     * @return true if the command is a redo operation, false otherwise
     */
    @Override
    public boolean isRedo(){
        return false;
    }
    //@@author
}
