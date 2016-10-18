package seedu.address.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
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
    
    /**
     * Signals that an operation adding/blocking a time slot in the list would fail because
     * the timeslot is already occupied.
     */
    
    public static class TimeslotOverlapException extends DuplicateDataException {

		public TimeslotOverlapException() {
			super("Operation cannot be done due to overlapping with blocked slots.");
		}
	}

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
     * Returns true if the given task requests to use a blocked time slot.
     */
    public boolean overlaps(ReadOnlyTask toCheck) {
        assert toCheck != null;
        //If to check is floating or deadline tasks, ignored.
        if(toCheck.getStartDate().getDate() == TaskDate.DATE_NOT_PRESENT)
        	return false;
        //Only compare tasks with certain time slots.
        for(Task t: internalList){
        	if(t.getType().equals(TaskType.NON_FLOATING)){
        		if(t.getStartDate().getDate()!=TaskDate.DATE_NOT_PRESENT){
        			if(!(t.getEndDate().getParsedDate().before(toCheck.getStartDate().getParsedDate())||
        	        	t.getStartDate().getParsedDate().after(toCheck.getEndDate().getParsedDate())))
        	        		return true;
        		}
        	}
        }
        return false;
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     * @throws TimeslotOverlapException 
     */
    public void add(Task toAdd) throws DuplicateTaskException, TimeslotOverlapException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        if(overlaps(toAdd)){
        	throw new TimeslotOverlapException();
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

	public boolean archive(ReadOnlyTask target) {
		assert target != null;
        boolean taskFoundAndArchived = false;
        
        for(ReadOnlyTask t : internalList){
        	if(t.equals(target)){
        		t.setType(TaskType.COMPLETED);
        		taskFoundAndArchived = true;
        	}
        }
        return taskFoundAndArchived;
	}
	
	private boolean checkUpdateOverlapping(ReadOnlyTask target, TaskDate startDate,
			TaskDate endDate) {
		if(startDate != null && endDate != null) {
			for(Task t: internalList){
				if(!t.equals(target)) {
					if(t.getType().equals(TaskType.NON_FLOATING)){
		        		if(t.getStartDate().getDate()!=TaskDate.DATE_NOT_PRESENT){
		        			if(!(t.getEndDate().getParsedDate().before(startDate.getParsedDate())||
		        	        	t.getStartDate().getParsedDate().after(endDate.getParsedDate())))
		        	        		return true;
		        		}
		        	}
				}
	        }
		}
		return false;
	}

	public boolean updateTask(Task target, Name name, UniqueTagList tags, TaskDate startDate,
			TaskDate endDate) throws TimeslotOverlapException {
		assert target != null;

		boolean taskFoundAndUpdated = false;
		for(Task t : internalList) {
        	if(t.equals(target)) {
        		if(checkUpdateOverlapping(target, startDate, endDate))
        			throw new TimeslotOverlapException();
        		
        		t.updateTask(name, tags, startDate, endDate);
        		taskFoundAndUpdated = true;
        	}
        }
		return taskFoundAndUpdated;
	}
}
