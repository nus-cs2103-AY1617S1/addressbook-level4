package seedu.malitio.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.malitio.commons.exceptions.DuplicateDataException;
import seedu.malitio.commons.util.CollectionUtil;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */

public class UniqueDeadlineList implements Iterable<Deadline> {

    //@@author A0129595N
    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateDeadlineException extends DuplicateDataException {
        protected DuplicateDeadlineException() {
            super("Operation would result in duplicate deadlines");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class DeadlineNotFoundException extends Exception {}
    
    public static class DeadlineCompletedException extends Exception {}
    
    public static class DeadlineMarkedException extends Exception {}
    
    public static class DeadlineUnmarkedException extends Exception {}

    private final ObservableList<Deadline> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueDeadlineList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyDeadline toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }
    
    /**
     * Returns true if the list contains an equivalent deadline as the given argument as well as identical tag(s).
     */
    public boolean containsWithTags(ReadOnlyDeadline toCheck) {
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
     * Adds a task to the list.
     *
     * @throws DuplicateFloatingTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Deadline toAdd) throws DuplicateDeadlineException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateDeadlineException();
        }
        internalList.add(toAdd);
    }
    
    public void edit(Deadline edited, ReadOnlyDeadline beforeEdit) throws DuplicateDeadlineException, DeadlineNotFoundException {
        assert edited!=null;
        assert beforeEdit!=null;
        if (containsWithTags(edited)) {
            throw new DuplicateDeadlineException();
        }
        
        if (!contains(beforeEdit)) {
            throw new DeadlineNotFoundException();
        }
 
        internalList.remove(beforeEdit);
        internalList.add(edited);
    }
    
	public void complete(ReadOnlyDeadline deadlineToComplete) throws DeadlineCompletedException, DeadlineNotFoundException {
        assert deadlineToComplete!=null;
        
        if (deadlineToComplete.getCompleted()) {
        	throw new DeadlineCompletedException();
        }

        if (!contains(deadlineToComplete)) {
            throw new DeadlineNotFoundException();
        }
        
        deadlineToComplete.setCompleted();
        updateDeadlineList(deadlineToComplete);
	}
	
	/**
     * Marks the deadline in the list.
     *
     * @throws DuplicateDeadlineException if the task to add is a duplicate of an existing task in the list.
     * @throws DeadlineMarkedException if the deadline is already marked.
     * @throws DeadlineUnmarkedException if the deadline is already unmarked.
     */
	public void mark(ReadOnlyDeadline deadlineToMark, boolean marked)
	        throws DeadlineNotFoundException, DeadlineMarkedException, DeadlineUnmarkedException {
	    if (deadlineToMark.isMarked() && marked) {
            throw new DeadlineMarkedException();
	    } else if (!deadlineToMark.isMarked() && !marked) {
	        throw new DeadlineUnmarkedException();
	    }

        if (!contains(deadlineToMark)) {
            throw new DeadlineNotFoundException();
        }
        
        deadlineToMark.setMarked(marked);
        updateDeadlineList(deadlineToMark);
	}

	private void updateDeadlineList(ReadOnlyDeadline deadlineToComplete) {
		int indexToReplace = internalList.indexOf(deadlineToComplete);
        internalList.remove(deadlineToComplete);
        internalList.add(indexToReplace, (Deadline) deadlineToComplete);
	}

    /**
     * Removes the equivalent schedule from the list.
     *
     * @throws DeadlineNotFoundException if no such deadline could be found in the list.
     */
    public boolean remove(ReadOnlyDeadline toRemove) throws DeadlineNotFoundException {
        assert toRemove != null;
        final boolean deadlineFoundAndDeleted = internalList.remove(toRemove);
        if (!deadlineFoundAndDeleted) {
            throw new DeadlineNotFoundException();
        }
        return deadlineFoundAndDeleted;
    }

    public ObservableList<Deadline> getInternalList() {
        return internalList;
    }
    
    //@@author
    public void sort() {
    	Collections.sort(internalList, new Comparator<Deadline>() {
        	  public int compare(Deadline e1, Deadline e2) {
        	      if (e1.getDue() == null || e2.getDue() == null)
        	        return 0;
        	      return e1.getDue().compareTo(e2.getDue());
        	  }
        	});
	}
    
    //@@author A0129595N
    @Override
    public Iterator<Deadline> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDeadlineList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueDeadlineList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}