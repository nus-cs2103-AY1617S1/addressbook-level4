package seedu.address.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.commons.exceptions.DuplicateDataException;

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
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return personFoundAndDeleted;
    }

    /**
     * Completes the equivalent task from the list.
     * (Set status to DONE)
     * @throws TaskNotFoundException if no such person could be found in the list.
     */
    public boolean complete(ReadOnlyTask toComplete) throws TaskNotFoundException {
        assert toComplete != null;
        if (!internalList.contains(toComplete)) {
            throw new TaskNotFoundException();
        }
        Task taskFoundAndCompleted = internalList.get(internalList.indexOf(toComplete));
        return taskFoundAndCompleted.setAsDone();
    }
    
    /**
     * Uncompletes the equivalent task from the list.
     * (Set status to NONE)
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean uncomplete(ReadOnlyTask toUncomplete) throws TaskNotFoundException {
        assert toUncomplete != null;
        if (!internalList.contains(toUncomplete)) {
            throw new TaskNotFoundException();
        }
        Task taskFoundAndCompleted = internalList.get(internalList.indexOf(toUncomplete));
        return taskFoundAndCompleted.setAsNorm();
    }
    
    ////@@author A0139024M
    /**
     * Overdues the equivalent task from the list.
     *(Set status to OVERDUE)
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean overdue(ReadOnlyTask target) throws TaskNotFoundException {
        if (!internalList.contains(target)) {
            throw new TaskNotFoundException();
        }
        Task taskFoundAndSetAsOverdue = internalList.get(internalList.indexOf(target));
        return taskFoundAndSetAsOverdue.setAsOverdue();
    }
    
    /**
     * Postpone the equivalent task from the list.
     * (Set status to NONE)
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean postponed(Task target) throws TaskNotFoundException {
        if (!internalList.contains(target)) {
            throw new TaskNotFoundException();
        }
        Task taskFoundAndSetAsPostponed = internalList.get(internalList.indexOf(target));
        return taskFoundAndSetAsPostponed.setAsNorm();       
    }

    /**
     * Expire the equivalent task from the list.
     * (Set status to EXPIRE)
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean expire(Task target) throws TaskNotFoundException {
        if(!internalList.contains(target)){
            throw new TaskNotFoundException();
        }
        Task taskFoundAndSetAsExpired = internalList.get(internalList.indexOf(target));
        return taskFoundAndSetAsExpired.setAsExpire();
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
        if (other == this){ // short circuit if same object
            return true;
        }
        if (!(other instanceof UniqueTaskList)) { // other is null
            return false;
        }
        else { //compare data in list 
            final Set<Task> s1 = new HashSet<>(this.internalList);
            final Set<Task> s2 = new HashSet<>(((UniqueTaskList) other).internalList);
            return s1.equals(s2);
        }
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
    
    public void sort(Comparator<Task> c){
    	internalList.sort(c);
    }

}
