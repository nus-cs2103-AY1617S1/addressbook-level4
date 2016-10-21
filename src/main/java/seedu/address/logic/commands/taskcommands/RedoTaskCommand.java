package seedu.address.logic.commands.taskcommands;

import seedu.address.logic.commands.CommandResult;

/**
 * Lists all tasks in the TaskManager to the user.
 */
public class RedoTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "redo";
    
    public static final String MESSAGE_REDO_TASK_SUCCESS = "Redo successful";
    public static final String MESSAGE_REDO_INVALID_STATE = "Redo is not successful because the previous successful command is not undo";
    
    public RedoTaskCommand() {}

    @Override
    public CommandResult execute() {
    	try {
    		model.redo();
    	} catch (IllegalStateException ise) {
    		return new CommandResult(MESSAGE_REDO_INVALID_STATE);
    	}
    	return new CommandResult(MESSAGE_REDO_TASK_SUCCESS);
    }
}
