package seedu.address.logic.commands;

import seedu.address.commons.exceptions.StateLimitException;

//@@author A0146123R
/*
 * Redoes the most recent undo action.
 */
public class RedoCommand extends Command{
    
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_SUCCESS = "Redid the most receent undo action:\n";
    public static final String MESSAGE_REDO_FAILED = "No command to redo.";

    @Override
    public CommandResult execute() {
        try {
            String message = model.getNextState();
            return new CommandResult(MESSAGE_REDO_SUCCESS + "\n" + message);
        } catch (StateLimitException e){
            return new CommandResult(MESSAGE_REDO_FAILED);
        }
    }

}
