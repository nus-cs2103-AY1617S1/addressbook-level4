//@@author A0146107M
package seedu.tasklist.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using its last displayed index or name from the address
 * book.
 */
public class DeleteCommand extends Command {

	public static final String COMMAND_WORD = "delete";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Deletes the task identified by the index number used in the last task listing.\n"
			+ "Parameters: either INDEX (must be a positive integer) or TASKNAME\n" + "Example: " + COMMAND_WORD + " 1";

	public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
	public static final String MESSAGE_DELETE_TASK_FAILURE = "No such task was found.";
	public static final String MESSAGE_DELETE_IN_NEXT_STEP = "Multiple tasks were found containing the entered keywords."
			+ " Please check below and delete by index.";

	public final boolean deleteByIndex;

	public int targetIndex;
	public String taskName;


    /**
     * Constructor using targetIndex.
     * 
     * @param	targetIndex index of task to be deleted
     */
	public DeleteCommand(int targetIndex) {
		deleteByIndex = true;
		this.targetIndex = targetIndex-1;
	}
	
    /**
     * Constructor using taskName.
     * 
     * @param	taskName name of task to be deleted
     */
	public DeleteCommand(String taskName) {
		deleteByIndex = false;
		taskName = taskName.trim();
		this.taskName = taskName;
	}

	/**
     * Executes the command
     */
	@Override
	public CommandResult execute(){
		if(deleteByIndex){
			return deleteUsingIndex();
		}
		else{
			return deleteUsingString();
		}	
	}

	/**
     * Processes the deletion of tasks by string
     * 
     * @return CommandResult containing task deletion outcome
     */
	private CommandResult deleteUsingString(){
		UnmodifiableObservableList<ReadOnlyTask> matchingTasks = getFilteredTaskList();
		String returnValue;
		switch(matchingTasks.size()){
		case 0:
			model.updateFilteredListToShowIncomplete();
			returnValue = String.format(MESSAGE_DELETE_TASK_FAILURE);
			break;
		case 1:
			String details = deleteTask(matchingTasks.get(0));
			returnValue = String.format(MESSAGE_DELETE_TASK_SUCCESS, details);
			break;
		default:
			returnValue = String.format(MESSAGE_DELETE_IN_NEXT_STEP);
		}
		return new CommandResult(returnValue);
	}

    /**
     * Gets filtered tasklist for deletion by string
     * 
     * @return UnmodifiableObservableList containing tasks whose name contain the given string
     */
	private UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList(){
		Set<String> taskNameSet = new HashSet<String>();
		taskNameSet.add(taskName);
		model.updateFilteredTaskList(taskNameSet);
		return model.getFilteredTaskList();
	}


    /**
     * Processes the deletion of tasks by index
     * 
     * @return CommandResult containing task deletion outcome
     */
	private CommandResult deleteUsingIndex(){
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		if(targetIndex >= lastShownList.size()){
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}
		else{
			String details = deleteTask(lastShownList.get(targetIndex));
			return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, details));
		}
	}
	
    /**
     * Deletes task from model
     * 
     * @param taskToDelete the task to be deleted
     * @return String containing details of the deleted task
     */
	private String deleteTask(ReadOnlyTask taskToDelete){
		try {
			model.deleteTask(taskToDelete);
		}
		catch (TaskNotFoundException e) {
			assert false: "The target task cannot be missing";
		}
		return taskToDelete.getTaskDetails().toString();
	}
}