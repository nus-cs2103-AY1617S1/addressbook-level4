package seedu.address.model.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.model.tag.UniqueTagList;

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
    
    //@@author A0147967J
    /**
     * Signals that an operation adding/blocking a time slot in the list would fail because
     * the timeslot is already occupied.
     */
    
    public static class TimeslotOverlapException extends DuplicateDataException {

		public TimeslotOverlapException() {
			super("Operation cannot be done due to overlapping with blocked slots.");
		}
	}
    //@@author
    
    private final List<Task> internalList = new ArrayList<Task>();
    private final ObservableList<TaskComponent> internalComponentList = FXCollections.observableArrayList();
    
    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return !toCheck.getName().fullName.equals(BlockCommand.DUMMY_NAME) //Ignore blocked slot case
        		&& internalList.contains(toCheck);
    }
    
    //@@author A0147967J
    /**
     * Returns true if the given task requests to use a blocked time slot.
     */
    public boolean overlaps(ReadOnlyTask toCheck) {
        assert toCheck != null;
        //ignore floating and deadline tasks
        if(toCheck.getComponentForNonRecurringType().getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT)
        	return false;
        //Only compare tasks with blocked time slots.
        for(Task t: internalList){
        	if(t.getName().fullName.equals(BlockCommand.DUMMY_NAME)){
        		if(!(!t.getComponentForNonRecurringType().getEndDate().getDate().after(toCheck.getComponentForNonRecurringType().getStartDate().getDate())||
        	        !t.getComponentForNonRecurringType().getStartDate().getDate().before(toCheck.getComponentForNonRecurringType().getEndDate().getDate())))
        	        	return true;        		
        	}
        }
        //Or if it is block command, check with existing tasks
        if(toCheck.getName().fullName.equals(BlockCommand.DUMMY_NAME)){
        	for(Task t: internalList){
        	if(t.getTaskType() == TaskType.NON_FLOATING && t.getComponentForNonRecurringType().getStartDate().getDateInLong() != TaskDate.DATE_NOT_PRESENT){
        		if(!(!t.getComponentForNonRecurringType().getEndDate().getDate().after(toCheck.getComponentForNonRecurringType().getStartDate().getDate())||
        	        !t.getComponentForNonRecurringType().getStartDate().getDate().before(toCheck.getComponentForNonRecurringType().getEndDate().getDate())))
        	        	return true;        		
        		}
        	}
        }
        return false;
    }
    //@@author
    
    //@@author A0135782Y
    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     * @throws TimeslotOverlapException 
     */
    public void add(Task toAdd) throws DuplicateTaskException, TimeslotOverlapException {
        assert toAdd != null;
        if (contains(toAdd)) {
            if (!toAdd.getRecurringType().equals(RecurringType.NONE)) {
                // append this "task" as date component to the task
                appendDuplicateRecurringDatesToTask(toAdd);
                return;
            }
            throw new DuplicateTaskException();
        }
        if(overlaps(toAdd)){
        	throw new TimeslotOverlapException();
        }
        internalList.add(toAdd);
        internalComponentList.addAll(toAdd.getTaskDateComponent());
    }

    private void appendDuplicateRecurringDatesToTask(Task toAdd) {
        int idx = internalList.indexOf(toAdd);
        Task toBeAppendedOn = internalList.get(idx);
        internalComponentList.add(toAdd.getComponentForNonRecurringType());
        toBeAppendedOn.appendRecurringDate(toAdd.getComponentForNonRecurringType());
    }
    //@@author
    
    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        internalComponentList.removeAll(toRemove.getTaskDateComponent());
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    public List<Task> getInternalTaskList() {
        return internalList;
    }

    public ObservableList<TaskComponent> getInternalComponentList() {
        return internalComponentList;
    }

    public void appendTaskComponent(TaskComponent component) {
        internalComponentList.add(component);
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
    
    //@@author A0147967J
    /** Returns true if the specified task component is successfully archived.*/
	public boolean archive(TaskComponent target) {
		assert target != null;
        boolean taskFoundAndArchived = false;
        System.out.println(internalComponentList.contains(target));
        for(TaskComponent t : internalComponentList){
        	if(t.equals(target)){
        		t.archive();
        		taskFoundAndArchived = true;
        		t.getTaskReference().completeTaskWhenAllComponentArchived();
        	}
        }
        return taskFoundAndArchived;
	}
	//@@author
	
	public boolean updateTask(Task target, Name name, UniqueTagList tags, TaskDate startDate,
			TaskDate endDate, RecurringType recurringType) throws TimeslotOverlapException {
		assert target != null;

		boolean taskFoundAndUpdated = false;	
		for(Task t : internalList) {
		    if(t.equals(target)) {
		    	TaskDate realStartDate = startDate == null ? new TaskDate(TaskDate.DATE_NOT_PRESENT) : startDate;
		    	TaskDate realEndDate = endDate == null ? new TaskDate(TaskDate.DATE_NOT_PRESENT) : endDate;
		    	Task checkTask = new Task(target.getName(), target.getTags(), realStartDate, realEndDate, recurringType);
        		if(overlaps(checkTask))
        			throw new TimeslotOverlapException();
        		
        		int componentToChange = internalComponentList.indexOf(t.getLastAppendedComponent());
        		t.updateTask(name, tags, startDate, endDate, recurringType);
        		internalComponentList.clear();
        		for(Task h: internalList) {
        			System.out.println(h.getName().fullName);
        			System.out.println(h.getTaskDateComponent().get(0).getTaskReference().getName().fullName);
        			internalComponentList.addAll(h.getTaskDateComponent());
        		}

        		taskFoundAndUpdated = true;
        	}
        }
		return taskFoundAndUpdated;
	}
}
