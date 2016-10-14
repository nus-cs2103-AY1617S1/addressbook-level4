package seedu.address.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;

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

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
	private final ObservableList<Task> internalTodayList = FXCollections.observableArrayList();
	private final ObservableList<Task> internalTomorrowList = FXCollections.observableArrayList();
	private final ObservableList<Task> internalIn7DaysList = FXCollections.observableArrayList();
	private final ObservableList<Task> internalIn30DaysList = FXCollections.observableArrayList();
	private final ObservableList<Task> internalSomedayList = FXCollections.observableArrayList();

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
        internalList.add(toAdd);
        filterTaskToAdd(toAdd);
    }
    
    /**
     * Filter the task being added to the list so as to assign the task to different internal lists.
     * 
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void filterTaskToAdd(Task toAdd) {
    	if (toAdd.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
    		internalSomedayList.add(toAdd);
    		// TO-DO: Sort the toAdd Task according to date(startDate for events and dueDate for deadlines).
    	}
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
    
    /**
     * set the equivalent task to the specified index of the list
     * @throws TaskNotFoundException if no such task could be found in the list.
     */ 			
    public boolean set(int key, Task Task) throws TaskNotFoundException {
        assert Task != null;
        boolean isFound = false;
        if (internalList.size() < key) {
            throw new TaskNotFoundException();
        } else {
        	internalList.set(key-1, Task);
        	isFound = true;
        }
        return isFound;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    public List getInternalTodayTaskList() {
        return internalTodayList;
    }

    public List getInternalTomorrowTaskList() {
        return internalTomorrowList;
    }

    public List getInternalIn7DaysTaskList() {
        return internalIn7DaysList;
    }

    public List getInternalIn30DaysTaskList() {
        return internalIn30DaysList;
    }

    public List getInternalSomedayTaskList() {
        return internalSomedayList;
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
