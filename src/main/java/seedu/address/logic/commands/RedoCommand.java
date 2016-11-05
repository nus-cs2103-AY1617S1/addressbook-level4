package seedu.address.logic.commands;

import seedu.address.commons.exceptions.StateLimitException;

//@@author A0146123R
/**
 * Redoes the most recent action that is undone.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_SUCCESS = "Redid the most recent action that is undone.\n%1$s";
    public static final String MESSAGE_REDO_FAILED = "No command to redo.";

    @Override
    public CommandResult execute() {
        try {
            String message = model.getNextState();
            return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, message));
        } catch (StateLimitException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_REDO_FAILED);
        }
    }

}