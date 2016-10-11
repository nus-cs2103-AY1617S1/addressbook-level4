package seedu.address.logic.commands;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.Undo;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Undo a previous task from the task scheduler.
 */

public class UndoCommand extends Command{
	
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_SUCCESS = "Undid: %1$s";
   
	private final Undo toUndo;

	public UndoCommand() {
		toUndo = CommandHistory.getMutateCmd();
	}
    @Override
	public CommandResult execute() {
    	
    	assert model != null;
        
    	try {
        	if (toUndo.getCommandKey().equals("add")) {
        		model.deleteTask(toUndo.getTask());
        	}
        	else if (toUndo.getCommandKey().equals("delete")) {
        		model.addTask(toUndo.getTask());
        	}
        	else if (toUndo.getCommandKey().equals("mark")) {
        		model.markTask(toUndo.getTask());
        	}
            return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo.getTask()));
        } catch (Exception e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
	}


}
