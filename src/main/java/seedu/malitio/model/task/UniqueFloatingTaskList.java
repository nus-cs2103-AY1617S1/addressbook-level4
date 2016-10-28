package seedu.malitio.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.malitio.commons.exceptions.DuplicateDataException;
import seedu.malitio.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */

public class UniqueFloatingTaskList implements Iterable<FloatingTask> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateFloatingTaskException extends DuplicateDataException {
        protected DuplicateFloatingTaskException() {
            super("Operation would result in duplicate floating tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class FloatingTaskNotFoundException extends Exception {}
    
    public static class FloatingTaskCompletedException extends Exception {}
    
    public static class FloatingTaskUncompletedException extends Exception {}
    
    public static class FloatingTaskMarkedException extends Exception {}
    
    public static class FloatingTaskUnmarkedException extends Exception {}

    private final ObservableList<FloatingTask> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueFloatingTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyFloatingTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }
    //@@author A0129595N    
    /**
     * Returns true if the list contains an equivalent task as the given argument as well as identical tag(s).
     */
    public boolean containsWithTags(ReadOnlyFloatingTask toCheck) {
        assert toCheck!=null;
        if (!internalList.contains(toCheck)) {
            return false;
        }
        else {
            int index = internalList.indexOf(toCheck);
            return internalList.get(index).getTags().getInternalList().containsAll(toCheck.getTags().getInternalList());
        }
    }

    /**
     * Adds a floating task to the list.
     *
     * @throws DuplicateFloatingTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(FloatingTask toAdd) throws DuplicateFloatingTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateFloatingTaskException();
        }
        internalList.add(toAdd);
    }
    
    
    /**
     * Adds a floating task to the list at the given index
     * 
     * @param toAdd
     * @param index
     * @throws DuplicateFloatingTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(FloatingTask toAdd, int index) throws DuplicateFloatingTaskException{
        assert toAdd != null;
        assert index>=0;
        if (contains(toAdd)) {
            throw new DuplicateFloatingTaskException();
        }
        internalList.add(index, toAdd);        
    }
    
    public void edit(FloatingTask edited, ReadOnlyFloatingTask beforeEdit) throws DuplicateFloatingTaskException, FloatingTaskNotFoundException {
        assert edited!=null;
        assert beforeEdit!=null;
        if (containsWithTags(edited)) {
            throw new DuplicateFloatingTaskException();
        }
        
        if (!contains(beforeEdit)) {
            throw new FloatingTaskNotFoundException();
        }
        
        int indexToReplace = internalList.indexOf(beforeEdit);
        internalList.remove(beforeEdit);
        internalList.add(indexToReplace, edited);
    }
    
    //@@author A0122460W
    /**
     * Completes the task in the list.
     *
     * @throws FloatingTaskCompletedException if the task to add is a duplicate of an existing task in the list.
     * @throws FloatingTaskNotFoundException if the deadline is already marked.
     * @throws FloatingTaskUnmarkedException if the deadline is already unmarked.
     */
    public void complete(ReadOnlyFloatingTask toComplete) throws FloatingTaskCompletedException, FloatingTaskNotFoundException {
        assert toComplete != null;
        if (toComplete.getCompleted()) {
            throw new FloatingTaskCompletedException();
        }
        
        if (!contains(toComplete)) {
            throw new FloatingTaskNotFoundException();
        }
        toComplete.setCompleted(true);
        updateFloatingTaskList(toComplete);
    }
    
    /**
     * Marks the task in the list.
     *
     * @throws DuplicateFloatingTaskException if the task to add is a duplicate of an existing task in the list.
     * @throws FloatingTaskMarkedException if the deadline is already marked.
     * @throws FloatingTaskUnmarkedException if the deadline is already unmarked.
     */
    public void uncomplete(ReadOnlyFloatingTask toUncomplete) throws FloatingTaskUncompletedException, FloatingTaskNotFoundException {
        assert toUncomplete != null;
        if (!toUncomplete.getCompleted()) {
            throw new FloatingTaskUncompletedException();
        }
        
        if (!contains(toUncomplete)) {
            throw new FloatingTaskNotFoundException();
        }
        toUncomplete.setCompleted(false);
        updateFloatingTaskList(toUncomplete);
    }
    
    //@@author 
    /**
     * Marks the task in the list.
     *
     * @throws DuplicateFloatingTaskException if the task to add is a duplicate of an existing task in the list.
     * @throws FloatingTaskMarkedException if the deadline is already marked.
     * @throws FloatingTaskUnmarkedException if the deadline is already unmarked.
     */
    public void mark(ReadOnlyFloatingTask taskToMark, boolean marked)
            throws FloatingTaskNotFoundException, FloatingTaskMarkedException, FloatingTaskUnmarkedException {
        if (taskToMark.isMarked() && marked) {
            throw new FloatingTaskMarkedException();
        } else if (!taskToMark.isMarked() && !marked) {
            throw new FloatingTaskUnmarkedException();
        }
        
        if (!contains(taskToMark)) {
            throw new FloatingTaskNotFoundException();
        }
        taskToMark.setMarked(marked);
        updateFloatingTaskList(taskToMark);
    }

	private void updateFloatingTaskList(ReadOnlyFloatingTask toComplete) {
		int indexToReplace = internalList.indexOf(toComplete);
        internalList.remove(toComplete);
        internalList.add(indexToReplace, (FloatingTask) toComplete);
	}
	
    /**
     * Removes the equivalent task from the list.
     *
     * @throws FloatingTaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyFloatingTask toRemove) throws FloatingTaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new FloatingTaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<FloatingTask> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<FloatingTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFloatingTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueFloatingTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
