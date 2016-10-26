package seedu.forgetmenot.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.forgetmenot.commons.exceptions.DuplicateDataException;
import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.commons.util.CollectionUtil;

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
    
//    public static class StartTimeAfterEndTime extends IllegalValueException{
//    	protected  StartTimeAfterEndTime() {
//			super("Start time cannot be after End time");
//		}
//    }

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
     * Sorts a list according to timing
     * @@author A0147619W
     */
    public void sortList() {
        if (internalList.size() <= 1)
            return;
        
        Collections.sort(internalList, new Comparator<Task>() {
        	@Override
        	public int compare(Task task1, Task task2) {

    			Time start1 = task1.getStartTime();
    			Time end1 = task1.getEndTime();
    			Time start2 = task2.getStartTime();
    			Time end2 = task2.getEndTime();
    			boolean start1IsMissing = start1.isMissing();
    			boolean end1IsMissing = end1.isMissing();
    			boolean start2IsMissing = start2.isMissing();
    			boolean end2IsMissing = end2.isMissing();
    			
    			if((start1IsMissing && end1IsMissing && (!start2IsMissing || !end2IsMissing)))
    				return 1;
    			
    			if (start2IsMissing && end2IsMissing && (!start1IsMissing || !end1IsMissing))
    				return -1;
    			
    			if(!start1IsMissing && !end1IsMissing) {
    				if(!start2IsMissing && !end2IsMissing) {
    					return start1.time.compareTo(start2.time) == 0?
    							end1.time.compareTo(end2.time):
    							start1.time.compareTo(start2.time);
    				}
    							
    				if(!start2IsMissing) {
    					return start1.time.compareTo(start2.time) == 0?
    							-1: start1.time.compareTo(start2.time);
    				}
    				else {
    					return start1.time.compareTo(end2.time) == 0?
    							1: start1.time.compareTo(end2.time);
    				}
    			}
    			else if(!start1IsMissing) {
    				if(!start2IsMissing && !end2IsMissing) {
    					return start1.time.compareTo(start2.time) == 0?
    							1: start1.time.compareTo(start2.time);
    				}
    				
    				if(!start2IsMissing) {
    					return start1.time.compareTo(start2.time);
    				}
    				else {
    					return start1.time.compareTo(end2.time);
    				}
    			}
    			else if(!end1IsMissing) {
    				if(!start2IsMissing && !end2IsMissing) {
    					return end1.time.compareTo(start2.time) == 0?
    							-1: end1.time.compareTo(start2.time);
    				}
    				
    				if(!end1IsMissing) {
    					return end1.time.compareTo(start2.time);
    				}
    				else {
    					return end1.time.compareTo(end2.time);
    				}
    			}	
    			return 0;
    	    }
		});
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

    //@@author A0139671X
    public void editTaskName(ReadOnlyTask toEdit, Name newName) throws TaskNotFoundException {
        assert toEdit != null;
        if(!internalList.contains(toEdit))
            throw new TaskNotFoundException();
        int taskIndex = internalList.indexOf(toEdit);
        Task taskFound = internalList.get(taskIndex);
        taskFound.setName(newName);
        internalList.set(taskIndex, taskFound);
    }
    
    //@@author A0139671X
    public void editStartTime(ReadOnlyTask toEdit, Time newTiming) throws TaskNotFoundException {
        assert toEdit != null;
        if(!internalList.contains(toEdit))
            throw new TaskNotFoundException();
        int taskIndex = internalList.indexOf(toEdit);
        Task taskFound = internalList.get(taskIndex);
        taskFound.setStartTime(newTiming);
        internalList.set(taskIndex, taskFound);
    }
    
    //@@author A0139671X
    public void editEndTime(ReadOnlyTask toEdit, Time newTiming) throws TaskNotFoundException {
        assert toEdit != null;
        if(!internalList.contains(toEdit))
            throw new TaskNotFoundException();
        int taskIndex = internalList.indexOf(toEdit);
        Task taskFound = internalList.get(taskIndex);
        taskFound.setEndTime(newTiming);
        internalList.set(taskIndex, taskFound);
    }
    
    //@@author A0139671X
    public void editRecurFreq(ReadOnlyTask toEdit, Recurrence newRec) throws TaskNotFoundException {
        assert toEdit != null;
        if(!internalList.contains(toEdit))
            throw new TaskNotFoundException();
        int taskIndex = internalList.indexOf(toEdit);
        Task taskFound = internalList.get(taskIndex);
        taskFound.setRecurrence(newRec);
        internalList.set(taskIndex, taskFound);        
    }
    
    /**
     * Mark a task as done from the list.
     * @return 
     */
    public void done(ReadOnlyTask toDone) throws TaskNotFoundException {
    	assert toDone != null;
        if(!internalList.contains(toDone))
            throw new TaskNotFoundException();
        Task taskFound = internalList.get(internalList.indexOf(toDone));
        try {
            taskFound.setDone(new Done(true));
        } catch (IllegalValueException e) {
            assert false : "Illegal value input for the done status";
        }
        internalList.set(internalList.indexOf(toDone), taskFound);
    }
    
    /**
     * Mark a task as undone from the list.
     * @return 
     */
    
    public void undone(ReadOnlyTask toUndone) throws TaskNotFoundException {
    	assert toUndone != null;
        if(!internalList.contains(toUndone))
            throw new TaskNotFoundException();
        Task taskFound = internalList.get(internalList.indexOf(toUndone));
        try {
            taskFound.setDone(new Done(false));
        } catch (IllegalValueException e) {
            assert false: "Illegal value input for the done status";
        }
        internalList.set(internalList.indexOf(toUndone), taskFound);
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
