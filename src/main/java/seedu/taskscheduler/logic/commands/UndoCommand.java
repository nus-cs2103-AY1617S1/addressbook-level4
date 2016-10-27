package seedu.taskscheduler.logic.commands;

import java.util.EmptyStackException;

import seedu.taskscheduler.commons.core.Messages;

//@@author A0140007B

/**
 * Undo a previous command in the task scheduler.
 */

public class UndoCommand extends Command{
	
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_FAILURE = "There is no previous command to undo!";
	
    @Override
	public CommandResult execute() {
    	assert model != null;
    	try {
            return CommandHistory.getExecutedCommand().revert();
    	} catch (EmptyStackException e) {
    	    return new CommandResult(MESSAGE_FAILURE);
    	}
	}

    @Override
    public CommandResult revert() {
        // This command not available for revert
        assert false : Messages.MESSAGE_PROGRAM_ERROR;
        return null;
    }
}
