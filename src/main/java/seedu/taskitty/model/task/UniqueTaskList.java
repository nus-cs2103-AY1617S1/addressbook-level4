package seedu.taskitty.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.taskitty.commons.exceptions.DuplicateDataException;
import seedu.taskitty.commons.util.CollectionUtil;
import seedu.taskitty.commons.util.TimeUtil;
import seedu.taskitty.ui.ResultDisplay;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}
    
	//@@author A0130853L
    /**
     * Signals that the done operation targeting a specified task in the list is a duplicate operation if the task has already been previously
     * marked as done.
     */
    public static class DuplicateMarkAsDoneException extends Exception {}

	//@@author
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        for (int i = 0; i < internalList.size(); i++) {
            if (toAdd.compareTo(internalList.get(i)) < 0) {
                internalList.add(i, toAdd);
                return;
            }
        }
        internalList.add(toAdd);
    }
    //@@author A0130853L
    
    /** Marks the given task as done from the list.
     * 
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws DuplicateMarkAsDoneException if specified task in list had already been marked as done previously.
     */
    public void mark(ReadOnlyTask toMark) throws TaskNotFoundException, DuplicateMarkAsDoneException {
    	assert toMark != null;
    	if (toMark.getIsDone()) {
    		throw new DuplicateMarkAsDoneException();
    	}
    	final boolean taskFoundAndMarkedAsDone = internalList.remove(toMark);
    	Task editableToMark = (Task) toMark;
    	editableToMark.markAsDone();
    	internalList.add(editableToMark);
    	if (!taskFoundAndMarkedAsDone) {
    		throw new TaskNotFoundException();
    	}
    }   
    
    //@@author
    /**
     * Sorts the task list according to compareTo method in Task
     */
    public void sort() {
        internalList.sort(null);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    //@@author A0130853L

    public ObservableList<Task> getInternalList() {
    	checkAndSetOverdue();
    	checkAndSetIsOverToday();
        return internalList;
    }
    
    /**
     * Returns the internal list, filtered to view only the specified type of Task
     * 
     * @param filter according to Task.
     */
    private void checkAndSetOverdue() {
    	boolean hasOverdue = false;
    	LocalDateTime currentTime = TimeUtil.createCurrentTime();
    	for (Task t: internalList) {
    		if (t.isDeadline() && !t.getIsDone()) {
    			if (isOverdue(t, currentTime)) {
    				t.markAsOverdue();
    				hasOverdue = true;
    			}
    		}
    	}
    	if (hasOverdue) {
    		ResultDisplay.setOverdue();
    	}
    }
    
    private void checkAndSetIsOverToday() {
    	LocalDateTime currentTime = TimeUtil.createCurrentTime();
    	for (Task t: internalList) {
    		if (t.isEvent() && isOverdue(t, currentTime)) {
    			t.markAsIsOver();
    		}
    	}
    }
    
    private boolean isOverdue(Task t, LocalDateTime currentTime) {
    	LocalDateTime taskTime = t.getPeriod().getEndDate().getDate().atTime(t.getPeriod().getEndTime().getTime());
    	if (currentTime.isAfter(taskTime)) {
    		return true;
    	}
    	return false;
    }
    
    //@@author A0139930B
    public FilteredList<Task> getFilteredTaskList(int filter) {
        return internalList.filtered(p -> p.getPeriod().getNumArgs() == filter);
    }
    
    //@@author
    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }
    
    //@@author A0130853L
    public FilteredList<Task> getFilteredTaskList() {
        return internalList.filtered(null);
    }
    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
