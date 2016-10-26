package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
	public static final String MESSAGE_SUCCESS_UNDO = "Undo of delete command";
	public final String MESSAGE_DUPLICATE = "The edited task is a duplicate of an existing task.";
	
    //@@author A0153411W
    public final int targetIndex;
    private Task savedTaskForUndo;
    //@@author 
    
    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        //@@author A0153411W
        saveTaskForUndo(taskToDelete);
        //@@author 
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    //@@author A0153411W
    /**
     * Save task for undo command before it is deleted.
     */
	private void saveTaskForUndo(ReadOnlyTask task){
		this.savedTaskForUndo = new Task(task.getTitle(), task.getDescription(), task.getStartDate(), task.getDueDate(), task.getInterval(), task.getTimeInterval(), task.getStatus(), task.getTags()); 
	}
	
	

	/**
	 * Execute undo method for delete command - Deleted task is added
	 * at specific place to restore 
	 * task manager to state before method was executed
	 * @throws DuplicateTaskException
	 * 				if task to add is already in manager
	 */
	@Override
	public CommandResult executeUndo() {
		try {
			model.addTaskWithSpecifiedIndex(savedTaskForUndo, targetIndex-1);
		} catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE);
		}

		return new CommandResult(MESSAGE_SUCCESS_UNDO);
	}


	@Override
	public boolean isReversible() {
		return true;
	}

}
