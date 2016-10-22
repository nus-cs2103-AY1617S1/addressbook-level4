package seedu.unburden.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
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
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    public boolean edit(ReadOnlyTask key, String args) throws TaskNotFoundException, IllegalValueException {
        String[] newArgs = args.split(" ");
        
        int taskIndex = internalList.indexOf(key);
        
        Task newTask = internalList.get(taskIndex);
        for (int i = 0; i < newArgs.length; i++) {
            switch (newArgs[i].substring(0, 2)) {
                case ("d/") : newTask.setDate(new Date(newArgs[i].substring(2, newArgs[i].length())));
                              internalList.set(taskIndex, newTask);
                              break;
                case ("s/") : newTask.setStartTime(new Time(newArgs[i].substring(2, newArgs[i].length())));
                              internalList.set(taskIndex, newTask);
                              break;
                case ("e/") : newTask.setEndTime(new Time(newArgs[i].substring(2, newArgs[i].length())));
                              internalList.set(taskIndex, newTask);
                              break;
            }
        }
        return true;
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

	public void done(ReadOnlyTask key, boolean isDone) {
		assert key != null;
		int targetIndex = internalList.indexOf(key);
		internalList.get(targetIndex).setDone(isDone);
	}
}
