package seedu.address.model;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.model.task.Task;

/**
 * Manages the tasks and aliases stored in order to support the undo & redo command
 */
public class UndoManager {
	// Collection to store the tasks / aliases after each command that changes tasks or aliases. ALlows user to undo.
	private UniqueItemCollection<Task> oldTasks;
	private UniqueItemCollection<Alias> oldAliases;
	 
	// Collection to store the current tasks / aliases before an undo command. Allows user to redo.
	private UniqueItemCollection<Task> undoneTasks;
	private UniqueItemCollection<Alias> undoneAliases;
	
	public UndoManager() {}
	
	/**
	 * Stores the list of old tasks so that undo can be called later.
	 */
	public void storeOldTasks(UniqueItemCollection<Task> oldCopy) {
		oldTasks = oldCopy;
		oldAliases = null; // Null to signal that aliases have not been changed
		
		// No tasks and aliases have been undone
		undoneTasks = null;
		undoneAliases = null;
	}
	
	/**
	 * If oldTasks exist, it means that there are tasks to be undone
	 */
	public boolean canUndoTasks() {
		return oldTasks != null;
	}
	
	/**
	 * Stores the list of old aliases so that undo can be called later.
	 */
	public void storeOldAliases(UniqueItemCollection<Alias> oldCopy) {
		oldAliases = oldCopy;
		oldTasks = null; // Null to signal that tasks have not been changed
		
		// No tasks and aliases have been undone
		undoneTasks = null;
		undoneAliases = null;
	}
	
	/**
	 * If oldAliases exist, it means that there are aliases to be undone
	 */
	public boolean canUndoAliases() {
		return oldAliases != null;
	}
	
	/**
	 * Clear old copies of aliases and tasks after undo
	 */
	public void clearOldCopies() {
		// Can only undo once. Hence, oldTasks & oldAliases are set to null after one undo
		oldAliases = null;
		oldTasks = null;
	}
	
	/**
	 * Returns oldTasks which is the old state of the task list,
	 * Stores current value in undoneTasks in case user wants to reverse the undo (redo),
	 * Resets oldTasks since only 1 undo is supported.
	 */
	public UniqueItemCollection<Task> undoTasks(UniqueItemCollection<Task> currentTasks) {
		// Before undoing task, undoneTasks is assigned the current list of tasks to allow user to redo
		undoneTasks = currentTasks;
		// Since no alias have been undone, undoneAliases set to null
		undoneAliases = null;
		
		// Return the old copy of tasks
		return oldTasks;
	}
	
	/**
	 * Returns oldAliases which is the old state of the alias list,
	 * Stores current value in undoneAliases in case user wants to reverse the undo (redo),
	 * Resets oldAliases since only 1 undo is supported.
	 */
	public UniqueItemCollection<Alias> undoAliases(UniqueItemCollection<Alias> currentAliases) {
		// Before undoing task, undoneAliases is assigned the current list of tasks to allow user to redo
		undoneAliases = currentAliases;
		// Since no alias have been undone, undoneTasks set to null
		undoneTasks = null;
		
		// Return the old copy of Aliases
		return oldAliases;
	}
	
	
}
