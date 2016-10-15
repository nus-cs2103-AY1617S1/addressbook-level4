package seedu.address.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * The tasks in this list get filtered to different individual lists.
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
    private final FilteredTaskLists filteredTaskLists = new FilteredTaskLists(internalList);


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
        //filterTaskToAdd(toAdd);
    }
    
//    /**
//     * Filter the task being added to the overall internal list
//     * so as to add the task to corresponding individual internal lists.
//     * 
//     */
//    public void filterTaskToAdd(Task toAdd) {
//    	assert toAdd != null;
//    	if (toAdd.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
//    		subInternalSomedayList.add(toAdd);
//    		// TO-DO: Sort the toAdd Task according to date(startDate for events and dueDate for deadlines).
//    	}
//    }

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
        //filterTaskToRemove(toRemove);
        return taskFoundAndDeleted;
    }
    
//    /**
//     * Filter the task being removed from the overall internal list
//     * so as to remove the task from corresponding individual internal lists.
//     * 
//     */
//    public void filterTaskToRemove(ReadOnlyTask toRemove) {
//    	assert toRemove != null;
//    	if (toRemove.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
//    		subInternalSomedayList.remove(toRemove);
//    		// TO-DO: Sort the toRemove Task according to date(startDate for events and dueDate for deadlines).
//    	}
//    }
    
    /**
     * set the equivalent task to the specified index of the list
     * @throws TaskNotFoundException if no such task could be found in the list.
     */ 			
    public boolean set(int key, Task toSet) throws TaskNotFoundException {
        assert toSet != null;
        boolean isFound = false;
        if (internalList.size() < key) {
            throw new TaskNotFoundException();
        } else {
//        	Task beforeSet = internalList.get(key-1);
        	internalList.set(key-1, toSet);
//        	filterTaskToSet(beforeSet, toSet);
        	isFound = true;
        }
        return isFound;
    }

//    /**
//     * Filter the task being set from the overall internal list
//     * so as to edit the task in corresponding individual internal lists.
//     * 
//     */
//    private void filterTaskToSet(Task beforeSet, Task toSet) {
//		assert toSet != null;
//		if (toSet.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
//			editTaskInSubInternalList(beforeSet, toSet, subInternalSomedayList);		    
//		    // TO-DO: Sort the toSet Task according to date(startDate for events and dueDate for deadlines).
//		}
//	}
//    
//    /**
//     * Edit the task in the different sub internal lists according to user
//     * 
//     */
//	private void editTaskInSubInternalList(Task beforeSet, Task toSet, ObservableList<Task> List) {
//		int toSetTaskIndex;
//		toSetTaskIndex = List.indexOf(beforeSet);
//		List.remove(toSetTaskIndex);
//		List.add(toSetTaskIndex, toSet);
//	}

//	/**
//	 * Update the different sub internal lists according to local time (or system time?)
//	 * when Amethyst is opened for the first time every day.
//	 */
//	public void updateSubInternalListsByLocalTime () {
//		//TO-DO
//	}
	
	public ObservableList<Task> getInternalList() {
        return internalList;
    }

    public ObservableList<Task> getSubInternalTodayTaskList() {
        return filteredTaskLists.getTodayTaskList();
    }

    public ObservableList<Task> getSubInternalTomorrowTaskList() {
        return filteredTaskLists.getTomorrowTaskList();
    }

    public ObservableList<Task> getSubInternalIn7DaysTaskList() {
        return filteredTaskLists.getIn7DaysTaskList();
    }

    public ObservableList<Task> getSubInternalIn30DaysTaskList() {
        return filteredTaskLists.getIn30DaysTaskList();
    }

    public ObservableList<Task> getSubInternalSomedayTaskList() {
        return filteredTaskLists.getSomedayTaskList();
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
