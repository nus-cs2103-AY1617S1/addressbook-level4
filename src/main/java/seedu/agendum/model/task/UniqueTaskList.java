package seedu.agendum.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.agendum.commons.util.CollectionUtil;
import seedu.agendum.commons.exceptions.DuplicateDataException;

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
     * Renames the equivalent task in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws DuplicateTaskException if the renamed task is a duplicate of an existing task in the list.
     */
    public boolean rename(ReadOnlyTask toRename, Name newTaskName)
            throws TaskNotFoundException, DuplicateTaskException {
        assert toRename != null;
        assert newTaskName != null;
        final int taskIndex = internalList.indexOf(toRename);
        final boolean taskFoundAndRenamed = (taskIndex != -1);
        if (!taskFoundAndRenamed) {
            throw new TaskNotFoundException();
        }
        Task toCheck = new Task(toRename);
        toCheck.setName(newTaskName);
        if (contains(toCheck)) {
            throw new DuplicateTaskException();
        }
        internalList.set(taskIndex, toCheck);
        return taskFoundAndRenamed;
    }

    /**
     * Schedules the equivalent task in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
    */
    public boolean schedule(ReadOnlyTask toSchedule, Optional<LocalDateTime> startDateTime,
            Optional<LocalDateTime> endDateTime) throws TaskNotFoundException {
        assert toSchedule != null;
        final int taskIndex = internalList.indexOf(toSchedule);
        final boolean taskFoundAndScheduled = (taskIndex != -1);
        if (!taskFoundAndScheduled) {
            throw new TaskNotFoundException();
        }
        Task scheduledTask = new Task(toSchedule);
        scheduledTask.setStartDateTime(startDateTime);
        scheduledTask.setEndDateTime(endDateTime);
        internalList.set(taskIndex, scheduledTask);
        return taskFoundAndScheduled;
    }

    /**
     * Marks the equivalent task in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean mark(ReadOnlyTask toMark) throws TaskNotFoundException {
        assert toMark != null;
        final int taskIndex = internalList.indexOf(toMark);
        final boolean taskFoundAndMarked = (taskIndex != -1);
        if (!taskFoundAndMarked) {
            throw new TaskNotFoundException();
        }
        Task markedTask = new Task(toMark);
        markedTask.markAsCompleted();
        internalList.set(taskIndex, markedTask);
        return taskFoundAndMarked;
    }
    
    /**
     * Unmarks the equivalent task in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean unmark(ReadOnlyTask toUnmark) throws TaskNotFoundException {
        assert toUnmark != null;
        final int taskIndex = internalList.indexOf(toUnmark);
        final boolean taskFoundAndUnmarked = (taskIndex != -1);
        if (!taskFoundAndUnmarked) {
            throw new TaskNotFoundException();
        }
        Task unmarkedTask = new Task(toUnmark);
        unmarkedTask.markAsUncompleted();
        internalList.set(taskIndex, unmarkedTask);
        return taskFoundAndUnmarked;
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
