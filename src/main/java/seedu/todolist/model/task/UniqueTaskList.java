package seedu.todolist.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.todolist.commons.exceptions.DuplicateDataException;
import seedu.todolist.commons.util.CollectionUtil;

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
        Collections.sort(internalList);
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
    
    //@@author A0138601M
    /**
     * Marks the equivalent task in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean mark(ReadOnlyTask toMark) throws TaskNotFoundException {
        assert toMark != null;
        final boolean taskFound = (internalList.indexOf(toMark) != -1);
        toMark.getStatus().setStatus(true);
        internalList.remove(toMark);
        internalList.add((Task)toMark);
        Collections.sort(internalList);
        if (!taskFound) {
            throw new TaskNotFoundException();
        }
        return taskFound;
    }
    //@@author
    
    /**
     * Edits the equivalent task in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean edit(ReadOnlyTask toEdit, Task replacement) throws TaskNotFoundException {
        assert toEdit != null;
        final boolean taskFound = (internalList.indexOf(toEdit) != -1);
        internalList.set(internalList.indexOf(toEdit), replacement);
        if (!taskFound) {
            throw new TaskNotFoundException();
        }
        return taskFound;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }
    
    public FilteredList<Task> getFilteredTaskList(String filter) {
        return internalList.filtered(p -> p.getStatus().toString().equals(filter));
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
