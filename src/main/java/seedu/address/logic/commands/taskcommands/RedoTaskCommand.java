package seedu.address.logic.commands.taskcommands;

import seedu.address.logic.commands.CommandResult;

/**
 * Lists all tasks in the TaskManager to the user.
 */
//@@author A0139817U
public class RedoTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_REDO_TASK_SUCCESS = "Redo successful";
    public static final String MESSAGE_REDO_INVALID_STATE = "Redo is not successful because the previous successful command is not undo";

    public static final String HELP_MESSAGE_USAGE = COMMAND_WORD + ": \t redoes previous command.";
    
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
    
    @Override
    public String toString(){
        return MESSAGE_REDO_TASK_SUCCESS;
    }
}
