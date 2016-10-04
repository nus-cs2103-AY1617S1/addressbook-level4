package seedu.address.model.task;

import seedu.address.commons.collections.UniqueItemCollection;

/*
 * Represents an in-memory task list
 */
public interface InMemoryTaskList {

	/*
	 * Adds a task to the current in-memory representation of the Task List
	 */
	void addTask(Task toAdd) throws UniqueItemCollection.DuplicateItemException;
	
	/*
	 * Removes a task from the current in-memory representation of the Task List
	 */
	void deleteTask(Task toRemove) throws UniqueItemCollection.ItemNotFoundException;
	
}
