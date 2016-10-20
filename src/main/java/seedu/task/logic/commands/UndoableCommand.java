package seedu.task.logic.commands;

public abstract class UndoableCommand extends Command {
    
	protected UndoableCommand reverseCommand;
	/**
     * Undo the command and returns the result message.
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult undo();
    
//    /**
//     * Prepare the command that will undo this command
//     * @return command that returns to the previous state
//     */
//    public abstract UndoableCommand prepareUndoCommand();
    
}
