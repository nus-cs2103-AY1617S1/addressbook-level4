package seedu.task.logic.commands;


//@@author A0144702N
public abstract class UndoableCommand extends Command {
    
	protected UndoableCommand reverseCommand;
	/**
     * Undo the command and returns the result message.
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult undo();
}
