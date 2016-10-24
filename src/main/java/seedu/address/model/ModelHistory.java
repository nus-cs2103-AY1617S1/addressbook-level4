package seedu.address.model;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.model.task.Task;

/**
 * Manages the tasks and aliases stored in order to support the undo & redo command.
 * 
 * Note:
 * 1) Undo & Redo commands cannot be stacked consecutively. 
 * Eg. calling 'undo' repeatedly is equivalent to calling 'undo' once.
 * 
 * 2) However, undo & redo commands can be interleaved. 
 * Eg. calling 'undo' then 'redo' does not change anything.
 */
//@@author A0139817U
public class ModelHistory {
	// Collection to store the tasks / aliases after each command that changes tasks or aliases. Allows user to undo.
	private UniqueItemCollection<Task> oldTasks;
	private UniqueItemCollection<Alias> oldAliases;
	 
	// Collection to store the current tasks / aliases before an undo command. Allows user to redo.
	private UniqueItemCollection<Task> undoneTasks;
	private UniqueItemCollection<Alias> undoneAliases;
	
	public ModelHistory() {}
	
	
	// ============================ Supporting Undo ============================
	
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
	 * Stores current value in undoneTasks in case user wants to reverse the undo (redo)
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
	 * Stores current value in undoneAliases in case user wants to reverse the undo (redo)
	 */
	public UniqueItemCollection<Alias> undoAliases(UniqueItemCollection<Alias> currentAliases) {
		// Before undoing task, undoneAliases is assigned the current list of tasks to allow user to redo
		undoneAliases = currentAliases;
		// Since no alias have been undone, undoneTasks set to null
		undoneTasks = null;
		
		// Return the old copy of Aliases
		return oldAliases;
	}
	
	// ============================ Supporting Redo ============================
	
	/**
	 * Stores the list of tasks that have been undone (so that redo can be called later).
	 */
	public void storeUndoneTasks(UniqueItemCollection<Task> currentCopy) {
		undoneTasks = currentCopy;
		undoneAliases = null; // Null to signal that aliases have not been undone
		
		// No tasks and aliases have been changed
		oldTasks = null;
		oldAliases = null;
	}
	
	/**
	 * If undoneTasks exist, it means that there are tasks to be redone
	 */
	public boolean canRedoTasks() {
		return undoneTasks != null;
	}
	
	/**
	 * Stores the list of aliases that have been undone (so that redo can be called later).
	 */
	public void storeUndoneAliases(UniqueItemCollection<Alias> currentCopy) {
		undoneAliases = currentCopy; 
		undoneTasks = null; // Null to signal that task have not been undone
		
		// No tasks and aliases have been changed
		oldTasks = null;
		oldAliases = null;
	}
	
	/**
	 * If undoneAliases exist, it means that there are aliases to be redone
	 */
	public boolean canRedoAliases() {
		return undoneAliases != null;
	}
	
	/**
	 * Clear copies of undone aliases and tasks after redo
	 */
	public void clearUndoneCopies() {
		// Can only redo once. Hence, undoneTasks & undoneAliases are set to null after one redo
		undoneAliases = null;
		undoneTasks = null;
	}
	
	/**
	 * Returns undoneTasks which is the state of the task list before 'undo' has been called,
	 * Stores current value in oldTasks in case user wants to reverse the redo (undo)
	 */
	public UniqueItemCollection<Task> redoTasks(UniqueItemCollection<Task> currentTasks) {
		// Before redoing task, oldTasks is assigned the current list of tasks to allow user to undo
		oldTasks = currentTasks;
		// Since no alias have been redone, oldAliases set to null
		oldAliases = null;
		
		// Return the tasks before undo has been called
		return undoneTasks;
	}
	
	/**
	 * Returns oldAliases which is the old state of the alias list,
	 * Stores current value in undoneAliases in case user wants to reverse the undo (redo)
	 */
	public UniqueItemCollection<Alias> redoAliases(UniqueItemCollection<Alias> currentAliases) {
		// Before undoing task, undoneAliases is assigned the current list of tasks to allow user to redo
		oldAliases = currentAliases;
		// Since no alias have been redone, undoneTasks set to null
		oldTasks = null;
		
		// Return the aliases before undo has been called
		return undoneAliases;
	}
	
}
