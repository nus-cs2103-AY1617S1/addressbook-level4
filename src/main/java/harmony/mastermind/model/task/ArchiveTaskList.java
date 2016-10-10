package harmony.mastermind.model.task;

import java.util.Iterator;

import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of completed tasks that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
//@@author A0124797R
public class ArchiveTaskList implements Iterable<ReadOnlyTask> {

    private final ObservableList<ReadOnlyTask> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ArchiveTaskList.
     */
    public ArchiveTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds an archived task to the list.
     */
    public void add(ReadOnlyTask toAdd) {
        assert toAdd != null;
        
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

    public ObservableList<ReadOnlyTask> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<ReadOnlyTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((ArchiveTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
