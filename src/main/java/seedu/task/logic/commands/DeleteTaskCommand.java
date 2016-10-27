package seedu.task.logic.commands;

import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.UniqueTaskList.*;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.AddTaskCommand;

/**
 * Deletes a Task identified using it's last displayed index from the address book.
 * @@author A0121608N
 */
public class DeleteTaskCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private ReadOnlyTask taskToDelete;
    
    public DeleteTaskCommand(int targetIndex) {
        this.lastShownListIndex = targetIndex;
    }


    public DeleteTaskCommand(ReadOnlyTask taskToDelete) {
		this.taskToDelete = taskToDelete;
	}


	@Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < lastShownListIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        if(lastShownListIndex != 0) {
        	taskToDelete = lastShownList.get(lastShownListIndex - 1);
        }
        
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

	@Override
	public CommandResult undo() {
		AddTaskCommand reverseCommand = new AddTaskCommand(taskToDelete);
		reverseCommand.setData(model);
		
		return reverseCommand.execute();
	}
	
	@Override
	public String toString() {
		return COMMAND_WORD +" "+ this.taskToDelete.getAsText();
	}

}
