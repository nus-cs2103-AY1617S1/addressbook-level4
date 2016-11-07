package w15c2.tusk.logic.commands;

import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

//@@author A0139817U
/**
 * Undoes the latest change to tasks or aliases of the Model.
 */
public class UndoCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "undo";
    public static final String ALTERNATE_COMMAND_WORD = null;
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String COMMAND_DESCRIPTION = "Undo a Command"; 
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": \t undoes previous command.";
    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo successful";
    public static final String MESSAGE_UNDO_INVALID_STATE = "Undo is not successful because there is no previous command";
    
    public UndoCommand() {}

    /**
     * Undo the latest task or alias change to the Model.
     * 
     * @return CommandResult Result of the execution of the undo command.
     */
    @Override
    public CommandResult execute() {
    	try {
    		model.undo();
    	} catch (IllegalStateException ise) {
    		return new CommandResult(MESSAGE_UNDO_INVALID_STATE);
    	}
    	closeHelpWindow();
    	return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }
    
    @Override
    public String toString(){
        return MESSAGE_UNDO_TASK_SUCCESS;
    }
}
