package seedu.cmdo.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.cmdo.commons.util.CollectionUtil;
import seedu.cmdo.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {}
    
    /**
     * Signals that the task is already done and cannot be done again.
     * 
     * @@author A0139661Y
     */
    public static class TaskAlreadyDoneException extends Exception {}

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
    public void add(Task toAdd) {
        assert toAdd != null;
        internalList.add(toAdd);
    }

    /**
     * Retrieves the equivalent task in the list for editing.
     *
     * @throws TaskNotFoundException
     * @author A0139661Y
     */
    public void edit(ReadOnlyTask toEdit, Task toEditWith) throws TaskNotFoundException {
    	assert (toEdit != null && toEditWith != null);
    	int index = internalList.indexOf(toEdit);
    	if (index == -1) {
    		throw new TaskNotFoundException();
    	}
    	internalList.set(index, toEditWith);
//    	internalList.remove(toEdit);
//    	internalList.add(toEditWith);
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

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

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
