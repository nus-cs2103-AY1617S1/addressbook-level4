package seedu.lifekeeper.model.activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.lifekeeper.commons.exceptions.DuplicateDataException;
import seedu.lifekeeper.commons.util.CollectionUtil;
import seedu.lifekeeper.model.activity.task.Task;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Activity#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueActivityList implements Iterable<Activity> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified activity in the list would fail because
     * there is no such matching activity in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ActivityList.
     */
    public UniqueActivityList() {}

    /**
     * Returns true if the list contains an equivalent activity as the given argument.
     */
    public boolean contains(ReadOnlyActivity toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a activity to the list.
     *
     * @throws DuplicateTaskException if the activity to add is a duplicate of an existing activity in the list.
     */
    public void add(Activity toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    
    public void addTo(Activity toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(0, toAdd);
    }

	public void addAt(int index, Activity toAdd) throws DuplicateTaskException{
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(index - 1, toAdd);
	}
	
	public void addToEnd(Activity toAdd) throws DuplicateTaskException{
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
	}
    
    /**
     * Removes the equivalent Task from the list.
     *
     * @throws TaskNotFoundException if no such activity could be found in the list.
     */
    public boolean remove(ReadOnlyActivity toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return personFoundAndDeleted;
    }
    
    //@@author A0125680H
    /**
     * Edits the equivalent Task from the list.
     *
     * @throws TaskNotFoundException if no such activity could be found in the list.
     */
    public void edit(Activity task, Activity newTask) throws TaskNotFoundException, DuplicateTaskException {
        assert task != null;
        assert newTask != null;
        
        if (contains(newTask) && (task.getTags().equals(newTask.getTags()))) {
            throw new DuplicateTaskException();
        }
        
        int index = internalList.indexOf(task);
        if(index == -1) {
            throw new TaskNotFoundException();
        }

        internalList.set(index, newTask);
    }
    
    //@@author A0125680H
    public void mark(Activity task, boolean isComplete) throws TaskNotFoundException {
        int index = internalList.indexOf(task);
        
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        
        ActivityManager.markTask(task, isComplete);
        
        internalList.set(index, task);
    }
    
    public ObservableList<Activity> getInternalList() {
        return internalList;
    }

    /**
     * Returns task if the list contains an equivalent task as the given argument.
     */
    public Activity get(Activity toGet) {
        assert toGet != null;
        return internalList.get(internalList.indexOf(toGet));
    }

    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


}
