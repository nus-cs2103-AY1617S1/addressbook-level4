package seedu.tasklist.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tasklist.commons.exceptions.DuplicateDataException;
import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.commons.util.RecurringUtil;

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
     * @throws DuplicateTaskException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    
    public boolean isOverlapping(Task toAdd) {
    	assert toAdd != null;
    	for (Task i: internalList) {
    		if (toAdd.getEndTime().toCardString().equals("-")) {
    			if (!toAdd.equals(i)
            		&& !toAdd.getStartTime().toCardString().equals("-")
            		//&& !task.getEndTime().toCardString().equals("-")
    				&& !i.getStartTime().toCardString().equals("-")
    				&& !i.getEndTime().toCardString().equals("-")
    				&& !toAdd.getStartTime().getAsCalendar().after(i.getEndTime().getAsCalendar())
    				&& !toAdd.getStartTime().getAsCalendar().before(i.getStartTime().getAsCalendar())) {
    				return true;
    			}
    		}
    		
    		else if (!toAdd.equals(i)
    				&& !toAdd.getStartTime().toCardString().equals("-")
    				//&& !toAdd.getEndTime().toCardString().equals("-")
    				&& !i.getStartTime().toCardString().equals("-")
    				&& !i.getEndTime().toCardString().equals("-")
    				&& !toAdd.getStartTime().getAsCalendar().after(i.getEndTime().getAsCalendar())
    				&& !i.getStartTime().getAsCalendar().after(toAdd.getEndTime().getAsCalendar())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Adds a person to the list.
     *
     * @throws TaskNotFoundException if no such person could be found in the list.
     */
    public boolean setComplete(ReadOnlyTask toComplete) throws TaskNotFoundException {
        assert toComplete != null;
        for (Task i: internalList){
        	if(i.getUniqueID()==toComplete.getUniqueID()){
        		i.markAsComplete();
        		i.setRecurringTime();
        		return true;
        	}
        }
        throw new TaskNotFoundException();
    }
    
    public boolean setIncomplete(ReadOnlyTask task) throws TaskNotFoundException {
        assert task != null;
        for (Task i: internalList){
            if(i.getUniqueID()==task.getUniqueID()){
                i.markAsIncomplete();
                RecurringUtil.updateRecurringDate(i.getStartTime().time, i.getRecurringFrequency(), -1);
                RecurringUtil.updateRecurringDate(i.getEndTime().time, i.getRecurringFrequency(), -1);
                return true;
            }
        }
        throw new TaskNotFoundException();
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws TaskNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        for (Task i: internalList){
        	if(i.getUniqueID()==toRemove.getUniqueID()){
        		internalList.remove(i);
        		return true;
        	}
        }
        throw new TaskNotFoundException();
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }
    
    public boolean isEmpty(){
    	return internalList.isEmpty();
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
