package seedu.agendum.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.agendum.commons.util.CollectionUtil;
import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.commons.exceptions.DuplicateDataException;

import java.util.*;
import java.util.logging.Logger;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {
    private static final Logger logger = LogsCenter.getLogger(UniqueTaskList.class);

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

    //@@author A0133367E
    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
 
        if (contains(toAdd)) {
            logger.fine("[TASK LIST] --- Duplicate Task: " + toAdd.getDetailedText());
            throw new DuplicateTaskException();
        }

        internalList.add(toAdd);
        logger.fine("[TASK LIST] --- Added a Task: " + toAdd.getDetailedText());
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
            logger.fine("[TASK LIST] --- Missing Task: " + toRemove.getDetailedText());
            throw new TaskNotFoundException();
        }

        logger.fine("[TASK LIST] --- Deleted a Task: " + toRemove.getDetailedText());

        return taskFoundAndDeleted;
    }
    
    /**
     * Replaces the equivalent task (to toUpdate) in the list with a new task (updatedTask).
     *
     * @throws TaskNotFoundException if no such task (toUpdate) could be found in the list.
     * @throws DuplicateTaskException if the updated task is a duplicate of an existing task in the list.
     */
    public boolean update(ReadOnlyTask toUpdate, Task updatedTask)
            throws TaskNotFoundException, DuplicateTaskException {
        assert toUpdate != null;
        assert updatedTask != null;

        final int taskIndex = internalList.indexOf(toUpdate);
        final boolean taskFoundAndUpdated = (taskIndex != -1);

        if (!taskFoundAndUpdated) {
            logger.fine("[TASK LIST] --- Missing Task: " + toUpdate.getDetailedText());
            throw new TaskNotFoundException();
        }

        if (contains(updatedTask)) {
            logger.fine("[TASK LIST] --- Duplicate Task: " + toUpdate.getDetailedText());
            throw new DuplicateTaskException();
        }

        internalList.set(taskIndex, updatedTask);
        logger.fine("[TASK LIST] --- Updated Task: " + toUpdate.getDetailedText()
                + " updated to " + updatedTask.getDetailedText());

        return taskFoundAndUpdated;
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
            logger.fine("[TASK LIST] --- Missing Task: " + toMark.getDetailedText());
            throw new TaskNotFoundException();
        }

        Task markedTask = new Task(toMark);
        markedTask.markAsCompleted();
        internalList.set(taskIndex, markedTask);

        logger.fine("[TASK LIST] --- Marked Task: " + markedTask.getDetailedText());

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
            logger.fine("[TASK LIST] --- Missing Task: " + toUnmark.getDetailedText());
            throw new TaskNotFoundException();
        }

        Task unmarkedTask = new Task(toUnmark);
        unmarkedTask.markAsUncompleted();
        internalList.set(taskIndex, unmarkedTask);

        logger.fine("[TASK LIST] --- Unmarked Task: " + unmarkedTask.getDetailedText());

        return taskFoundAndUnmarked;
    }

    //@@author
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
