package jym.manager.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDateTime;
import java.util.*;

import javax.swing.event.InternalFrameListener;

import jym.manager.model.task.Task;
import jym.manager.commons.exceptions.DuplicateDataException;
import jym.manager.commons.util.CollectionUtil;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
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
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) {
        assert toAdd != null;
//        if (contains(toAdd)) {
//            throw new DuplicateTaskException();
//        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
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
    
    /**
     * Marks the equivalent task(s) in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean complete(ReadOnlyTask task) throws TaskNotFoundException {
        assert task != null;
        boolean taskFound = false;
        taskFound = internalList.indexOf(task) != -1;

        Task taskMarked = new Task(task.getDescription(), task.getLocation(), task.getDate(), task.getPriority(), new Status(true));
    	internalList.set(internalList.indexOf(task), taskMarked);
//        Collections.sort(internalList);
        return taskFound;
    }
       
	/**
	 * 
	 * @param toUpdate
	 * @param updatedTask
	 * @return
	 * @throws TaskNotFoundException
	 */
    public void update(ReadOnlyTask toUpdate, Task updatedTask) throws TaskNotFoundException {
    	assert toUpdate != null;
    	int index = internalList.indexOf(toUpdate);
    	internalList.get(index).update(updatedTask);
    	internalList.set(index, (Task) toUpdate);
    }
    
    public ObservableList<Task> getInternalList() {
        return internalList;
    }
    
    public FilteredList<Task> getFilteredTaskList(String filter) {
        return internalList.filtered(p -> p.getStatus().toString().equals(filter));
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

    /**
     * Sorting the task
     *
     */
    
    public void sort(String sortType) {
		System.out.println("size1: " + internalList.size());
		if (internalList != null && internalList.size() > 0) {
			if (sortType.equals("ans")) {
				internalList.sort(new SortAns());
			} else {
				internalList.sort(new SortDesc());
			}
		}
		System.out.println("size2: " + internalList.size());
	}
        
    class SortAns implements Comparator<Task> {
		@Override
		public int compare(Task o1, Task o2) {
			int ret = 0;
			LocalDateTime date1 = null;
			LocalDateTime date2 = null;
			if (o1.getDate() == null || o1.getDate().getDate() == null) {
				date1= LocalDateTime.now();
			}else{
				date1= o1.getDate().getDate();
			}
			if (o2.getDate() == null || o2.getDate().getDate() == null) {
				date2= LocalDateTime.now();
			}else{
				date2= o2.getDate().getDate();
			}
			ret = date1.compareTo(date2);
			if (ret == 0) {
				ret = o1.getDescription().toString().compareTo(o2.getDescription().toString());
			}
			return ret;

		}
	}
    
	class SortDesc implements Comparator<Task> {
		@Override
		public int compare(Task o1, Task o2) {
			int ret = 0;
			LocalDateTime date1 = null;
			LocalDateTime date2 = null;
			if (o1.getDate() == null || o1.getDate().getDate() == null) {
				date1= LocalDateTime.now();
			}else{
				date1= o1.getDate().getDate();
			}
			if (o2.getDate() == null || o2.getDate().getDate() == null) {
				date2= LocalDateTime.now();
			}else{
				date2= o2.getDate().getDate();
			}
			ret = date2.compareTo(date1);
			if (ret == 0) {
				ret = o2.getDescription().toString().compareTo(o1.getDescription().toString());
			}
			return ret;

		}
	}
}
