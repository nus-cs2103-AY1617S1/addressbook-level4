package seedu.address.logic.commands;

/**
 * Undo change the storage location of the task manager.
 */
public class UndoChangeCommand extends Command {
    
    public static final String COMMAND_WORD = "undochange";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the default storage location back to the previous location"
            + " and clear data saved in the new location if specified.\n" 
            + "Parameters: [clear]\n"
            + "Example: " + COMMAND_WORD
            + " clear";

    public static final String MESSAGE_CHANGE_SUCCESS = "Storage location has been changed back!";
    public static final String MESSAGE_INVALID_CLEAR_DATA = "The clear data argument provided is invalid.";
    
    private static final String CLEAR = "clear";
    private static final String EMPTY = "";
    
    private final String clear;
    private final boolean isToClearNew;
    
    /**
     * Convenience constructor using raw values.
     */
    public UndoChangeCommand(String clear) {
        this.clear = clear.trim();
        this.isToClearNew = !this.clear.equals(EMPTY);
    }
    
    @Override
    public CommandResult execute() {
        assert clear != null;
        
        if (isToClearNew && !isValidClear()) {
            return new CommandResult(MESSAGE_INVALID_CLEAR_DATA);
        }
        model.changeBackTaskManager(isToClearNew);
        return new CommandResult(MESSAGE_CHANGE_SUCCESS);
    }
    
    private boolean isValidClear() {
        return clear.equals(CLEAR);
    }

}
