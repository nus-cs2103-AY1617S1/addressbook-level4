package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList undoneTasks;
    private final UniqueTaskList doneTasks;

    {
        undoneTasks = new UniqueTaskList();
        doneTasks = new UniqueTaskList();
    }

    public TaskManager() {}

    /**
     * FloatingTasks and Tags are copied into this addressbook
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueUndoneTaskList(), toBeCopied.getUniqueDoneTaskList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskManager(UniqueTaskList undoneTasks, UniqueTaskList doneTasks) {
        resetData(undoneTasks.getInternalList(), doneTasks.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getUndoneTasks() {
        return undoneTasks.getInternalList();
    }
    
    public ObservableList<Task> getDoneTasks() {
        return doneTasks.getInternalList();
    }

    public void setUndoneTasks(List<Task> undoneTasks) {
        this.undoneTasks.getInternalList().setAll(undoneTasks);
    }

    public void setDoneTasks(List<Task> doneTasks){
        this.doneTasks.getInternalList().setAll(doneTasks);
    }


    public void resetData(Collection<? extends ReadOnlyTask> newUndoneTasks, Collection<? extends ReadOnlyTask> newDoneTasks) {
        setUndoneTasks(newUndoneTasks.stream().map(Task::new).collect(Collectors.toList()));
        setDoneTasks(newDoneTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getUndoneTaskList(), newData.getDoneTaskList());
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     */
    public void addTask(Task f) {
        undoneTasks.add(f);
    }
    
    public void addDoneTask(Task f) {
        doneTasks.add(f);
    }
    
    public boolean removeFloatingTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (undoneTasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean removeDoneTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (doneTasks.remove(key)){
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    //@@author A0139552B
    /*
     * Edit the parameters in the current task
     * Sort the list afterwards
     */
	public void editFloatingTask(ReadOnlyTask floatingTask, Name name, Date startDate, Date endDate, Priority priority,
			RecurrenceRate recurrenceRate) {		
		Task currTask = undoneTasks.getTask(floatingTask);
		setCurrentTask(name, startDate, endDate, priority, recurrenceRate, currTask);
		undoneTasks.set(undoneTasks.getIndex(currTask), currTask);
		undoneTasks.sort();
	}
	
	/*
	 * Assign parameters to the current task
	 */
    private void setCurrentTask(Name name, Date startDate, Date endDate, Priority priority,
            RecurrenceRate recurrenceRate, Task currTask) {
        currTask.setName(name);
		currTask.setStartDate(startDate);
		currTask.setEndDate(endDate);
		currTask.setPriority(priority);
		currTask.setRecurrence(recurrenceRate);
    }
    
    //@@author 
    
//// util methods

	@Override
    public String toString() {
        return undoneTasks.getInternalList().size() + " floating tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getUndoneTaskList() {
        return Collections.unmodifiableList(undoneTasks.getInternalList());
    }
    
    @Override
    public List<ReadOnlyTask> getDoneTaskList() {
        return Collections.unmodifiableList(doneTasks.getInternalList());
    }


    @Override
    public UniqueTaskList getUniqueUndoneTaskList() {
        return this.undoneTasks;
    }
    
    @Override
    public UniqueTaskList getUniqueDoneTaskList() {
        return this.doneTasks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.undoneTasks.equals(((TaskManager) other).undoneTasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(undoneTasks);
    }

}
