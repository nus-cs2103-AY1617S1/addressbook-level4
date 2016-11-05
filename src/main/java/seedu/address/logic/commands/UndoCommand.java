package seedu.address.logic.commands;

import seedu.address.commons.exceptions.StateLimitException;

//@@author A0146123R
/**
 * Undoes the most recent action (up to 10 times).
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Undid the most recent action.\n%1$s";
    public static final String MESSAGE_UNDO_FAILED = "No command to undo.";

    @Override
    public CommandResult execute() {
        try {
            String message = model.getPreviousState();
            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, message));
        } catch (StateLimitException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_UNDO_FAILED);
        }
    }

}