package seedu.toDoList.logic.commands;

import seedu.toDoList.commons.exceptions.StateLimitException;

//@@author A0146123R
/**
 * Undoes change the storage location of the toDoList.
 */
public class UndoChangeCommand extends Command {

    public static final String COMMAND_WORD = "undochange";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the default storage location back to the previous location"
            + " and clear data saved in the current location if specified.\n" 
            + "Parameters: [clear]\n"
            + "Example: " + COMMAND_WORD + " clear";

    public static final String MESSAGE_CHANGE_SUCCESS = "Storage location changed back.";
    public static final String MESSAGE_UNDO_FAILED = "No change command to undo.";
    public static final String MESSAGE_INVALID_CLEAR_DATA = "The clear data argument provided is invalid."
            + " It must be abscent or \"clear\".";

    private static final String CLEAR = "clear";

    private final String clear;
    private final boolean isToClearNew;

    /**
     * Convenience constructor using raw values.
     */
    public UndoChangeCommand(String clear) {
        this.clear = clear.trim();
        this.isToClearNew = !clear.isEmpty();
    }

    @Override
    public CommandResult execute() {
        assert clear != null;

        if (!isValidClear(clear)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_INVALID_CLEAR_DATA);
        }
        try {
            model.changeBackTaskManagerPath(isToClearNew);
            return new CommandResult(MESSAGE_CHANGE_SUCCESS);
        } catch (StateLimitException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_UNDO_FAILED);
        }
    }

    /**
     * Returns true if the given clear data argument is valid.
     */
    private static boolean isValidClear(String clear) {
        return clear.isEmpty() || clear.equals(CLEAR);
    }

}