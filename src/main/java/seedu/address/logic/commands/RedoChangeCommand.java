package seedu.address.logic.commands;

/**
 * Redo change the storage location of the task manager.
 */
public class RedoChangeCommand extends Command {
    
    public static final String COMMAND_WORD = "redochange";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo change the default storage location back to the new location"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_CHANGE_SUCCESS = "Storage location has been changed!";
    public static final String MESSAGE_REDO_FAILED = "No undo change command to redo.";
    public static boolean redoable = false;
    public static boolean isToClearOld = false;
    
    @Override
    public CommandResult execute() {
        if (!redoable) {
            return new CommandResult(MESSAGE_REDO_FAILED);
        }
        model.redoUpdateTaskManager(isToClearOld);
        redoable = false;
        UndoChangeCommand.undoable = true;
        return new CommandResult(MESSAGE_CHANGE_SUCCESS);
    }
    
}