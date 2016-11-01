package w15c2.tusk.model.task;

import java.util.Set;

import javafx.collections.ObservableList;
import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.collections.UniqueItemCollection.DuplicateItemException;
import w15c2.tusk.commons.collections.UniqueItemCollection.ItemNotFoundException;
import w15c2.tusk.commons.core.UnmodifiableObservableList;
import w15c2.tusk.model.Alias;

/*
 * Represents an in-memory task list
 */
public interface InMemoryTaskList {

	/*
	 * Gets collection of tasks
	 */
	UniqueItemCollection<Task> getTasks();
	
	/*
	 * Adds a task to the current in-memory representation of the Task List
	 */
	void addTask(Task toAdd) throws DuplicateItemException;
	
	/*
	 * Updates a task with a new task from the current in-memory representation of the Task List
	 */
	void updateTask(Task toAdd, Task newTask) throws ItemNotFoundException, DuplicateItemException;
	
	/*
	 * Removes a task from the current in-memory representation of the Task List
	 */
	void deleteTask(Task toRemove) throws ItemNotFoundException;
	
	/*
	 * Deletes all the tasks that are currently listed and returns number of tasks successfully deleted
	 */
	int clearTasks();
	
	/*
	 * Pins a task in the current in-memory representation of the Task List
	 */
	void pinTask(Task toPin);
	
	/*
	 * Unpins a task in the current in-memory representation of the Task List
	 */
	void unpinTask(Task toPin);
	
	/*
	 * Completes a task in the current in-memory representation of the Task List
	 */
	void completeTask(Task toComplete);
	
	/*
	 * Uncompletes a task in the current in-memory representation of the Task List
	 */
	void uncompleteTask(Task toUncomplete);
	
	/*
	 * Adds a set of keywords to filter the task list by
	 */
	void filterTasks(Set<String> keywords);
	
	/*
	 * Filters completed tasks out of list
	 */
	void filterUncompletedTasks();
	
	/*
	 * Filters completed tasks from task list
	 */
	void filterCompletedTasks();

	
	/*
	 * Clears the filtering keywords applied to the tasks
	 */
	void clearTasksFilter();
	
	/*
	 * Reapplies the same filter as before - refreshes observable list for display
	 */
	void refreshTasksFilter();
	
	/*
	 * Gets the current list of tasks with the filtering words applied
	 */
	UnmodifiableObservableList<Task> getCurrentFilteredTasks();
	
	/*
	 * Adds a one-word alias for any sentence to be used as a command.
	 */
	void addAlias(Alias toAdd) throws DuplicateItemException;
	
	/*
	 * Removes an alias from the current in-memory representation of the Alias List
	 */
	void deleteAlias(Alias toRemove) throws ItemNotFoundException;
	
	/*
	 * Undoes the previous command that has to do with tasks or aliases
	 * (Can only be called if the previous successful command was a successful task/alias command)
	 */
	void undo() throws IllegalStateException;
	
	/*
	 * Gets help list to fill up help overlay
	 */
	ObservableList<String> getHelpList();

	/*
	 * Redoes the command that has been undone
	 * (Can only be called if the previous successful command was a successful undo command)
	 */
	void redo() throws IllegalStateException;
	
	/*
	 * Gets the list of alias
	 */
	UnmodifiableObservableList<Alias> getAlias();
}
