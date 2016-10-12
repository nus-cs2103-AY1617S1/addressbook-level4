package seedu.task.logic.commands;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task in the address book.
 * Specifically, it finds the details that requires editing and creates a new task, deleting the old one.
 * Currently, it can only edit the name of a task.
 */

public class EditCommand extends Command{
	public final String MESSAGE_SUCCESS = "The data has been successfully edited.";
	public final String MESSAGE_NOT_FOUND = "The task was not found.";
	public final String MESSAGE_DUPLICATE = "The edited task is a duplicate of an existing task.";
	public final String MESSAGE_PARAM = "Incorrect parameters.";
	
	private ReadOnlyTask selectedTask;
	private Task copy, editedTask;
	private int paramLength;
	private String[] params;
	private UnmodifiableObservableList<ReadOnlyTask> taskList;
	private String taskName;

	/**
	 * Constructor
	 * @param name the name/identifier of the task
	 * @param strings the parameters
	 */
	public EditCommand(String name, String... strings) {
		taskName = name;
		taskList = model.getFilteredTaskList();
		paramLength = strings.length;
		params = strings;
	}
	
	
	/**
	 * Searches through the task list to find the specified task
	 * @param name the name/identifier of the task
	 * @return the specified task
	 * @throws TaskNotFoundException if the task was not found
	 */
	public ReadOnlyTask searchTask(String name) throws TaskNotFoundException {
		assert !taskList.isEmpty();
		boolean found = false;
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getName().toString().equalsIgnoreCase(name)) {
				found = true;
				return taskList.get(i);
			}
		}
		if (!found) {
			throw new TaskNotFoundException();
		}
		return null;
	}

	@Override
	public CommandResult execute() {
		try {
			selectedTask = searchTask(taskName);
			edit(selectedTask);
			model.deleteTask(selectedTask);
			model.addTask(editedTask);
		} catch (TaskNotFoundException e) {
			return new CommandResult(MESSAGE_NOT_FOUND);
		} catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE);
		} catch (IllegalValueException e) {
			return new CommandResult(MESSAGE_PARAM);
		}
		return new CommandResult(MESSAGE_SUCCESS);
	}
	
	/**
	 * Begins the editing process
	 * @param task the specified task to edit
	 * @throws IllegalValueException if the parameters provided were incorrect
	 */
	public void edit(ReadOnlyTask task) throws IllegalValueException{
		copy = (Task) selectedTask;
		iterateParams(params);
		editedTask = copy;
	}
	
	/**
	 * Iterates through the parameters
	 * @param params array of parameters
	 * @throws IllegalValueException if the parameters provided were incorrect
	 */
	public void iterateParams(String[] params) throws IllegalValueException{
		if (paramLength % 2 != 0) {
			throw new IllegalValueException("Incorrect parameters.");
		} else {
			for (int i = 0; i < paramLength; i += 2) {
				if (params[i].equals("-n")) {
					changeName(params[i+1]);
				}
			}
		}
		
	}
	
	/**
	 * Changes the name in a task if specified in the parameters
	 * @param name the new name value
	 * @throws IllegalValueException if the name value is invalid
	 */
	public void changeName(String name) throws IllegalValueException {
		Name newName = new Name(name);
		copy = new Task(newName, copy.getPhone(), copy.getEmail(), copy.getAddress(), copy.getTags());
	}

}
