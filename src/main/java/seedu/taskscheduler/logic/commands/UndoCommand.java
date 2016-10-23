package seedu.taskscheduler.logic.commands;

import java.util.EmptyStackException;
/**
 * Undo a previous task from the task scheduler.
 */

public class UndoCommand extends Command{
	
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_SUCCESS = "Undid %s command: %s";

	public static final String MESSAGE_FAILURE = "There is no previous command to undo!";
   
    @Override
	public CommandResult execute() {
    	
    	assert model != null;
        
    	try {
            return CommandHistory.getExecutedCommand().revert();
    	} catch (EmptyStackException ese) {
    	    return new CommandResult(MESSAGE_FAILURE);
    	}
	}

    @Override
    public CommandResult revert() {
        // TODO Auto-generated method stub
        return null;
    }
}
