package seedu.forgetmenot.logic.commands;

import java.util.NoSuchElementException;

/**
 * Redoes an undo action.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the most recent command in ForgetMeNot. "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Command redone! Your recent undo was undone.";
    public static final String MESSAGE_REDO_INVALID = "There is nothing to redo.";
    
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.loadFromUndoHistory();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (NoSuchElementException e) {
            return new CommandResult(MESSAGE_REDO_INVALID);
        }
    }   

}