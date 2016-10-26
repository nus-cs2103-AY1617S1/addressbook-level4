package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.model.task.Status;
import seedu.task.model.task.Task;

/**
 * Mark a task as completed which is identified using it's last displayed index from the task manager.
 */
public class DoneCommand extends Command {
    
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark a task as completed which is identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
	public static final String MESSAGE_SUCCESS_UNDO = "Undo of done command";
	public static final String MESSAGE_ALREADY_COMPLETED = "The task is already done.";
	public final String MESSAGE_DUPLICATE = "The task is a duplicate of an existing task.";
	public final String MESSAGE_NOT_FOUND = "The task was not found.";
    
    public int targetIndex;
    public String targetStatus;
    
    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);

        targetStatus= taskToComplete.getStatus().status;
        if(targetStatus.equals("COMPLETED"))
        	return new CommandResult(MESSAGE_ALREADY_COMPLETED);
        
        try {
            ReadOnlyTask completedTask = taskToComplete;
            completedTask.setStatus(new Status("COMPLETED"));
            model.completeTask(taskToComplete, completedTask);
            
        } catch (TaskNotFoundException pnfe) {
            assert false : MESSAGE_NOT_FOUND;
        }
        

        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, taskToComplete));
    }

	/**
	 * Assume that done task is at the end of list
	 */
	@Override
	public CommandResult executeUndo() {
		if(targetStatus.equals("COMPLETED"))
			return new CommandResult(String.format(MESSAGE_SUCCESS_UNDO));
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		int numberOfTasks = lastShownList.size();
		ReadOnlyTask task = lastShownList.get(numberOfTasks - 1);
		Task taskToAdd = new Task(task.getTitle(), task.getDescription(), task.getStartDate(), task.getDueDate(),
				task.getInterval(), task.getTimeInterval(), task.getStatus(), task.getTags());
		taskToAdd.setStatus(new Status(targetStatus));
		try {
			model.deleteTask(task);
			model.addTaskWithSpecifiedIndex(taskToAdd, targetIndex - 1);
		} catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE);
		} catch (TaskNotFoundException e) {
			return new CommandResult(MESSAGE_NOT_FOUND);
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS_UNDO));
	}

	/**
	 * If the task is already COMPLETED, method is not reversible
	 */
	@Override
	public boolean isReversible() {
		return true;
	}

}
