package jym.manager.model;

import javafx.collections.ObservableList;

import java.util.*;
import java.util.stream.Collectors;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.model.tag.Tag;
import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.model.task.Status;
import jym.manager.model.task.Task;
import jym.manager.model.task.UniqueTaskList;
import jym.manager.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueTaskList completedTasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        completedTasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskManager() {}
    
    /**
     * Tasks and Tags are copied into this taskmanager
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this taskmanager
     */
    public TaskManager(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<Task> getCompletedTasks() {
        return tasks.getFilteredTaskList(Status.STATUS_COMPLETE);
    }
    
    public ObservableList<Task> getIncompleteTasks() {
        return tasks.getFilteredTaskList(Status.STATUS_INCOMPLETE);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(arg0 -> {
			return new Task(arg0);
		}).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }
    
    public void sortTask(String sortType) throws UniqueTaskList.DuplicateTaskException {
    	tasks.sort(sortType);

    }

//// task-level operations

    /**
     * Adds a task to the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
 //       syncTagsWithMasterList(p);
        tasks.add(p);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
//@@author A0153440R

    public void updateTask(ReadOnlyTask oldTask, Task updatedTask) throws UniqueTaskList.TaskNotFoundException {
    	tasks.update(oldTask, updatedTask);
    }

    public boolean completeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.complete(key)) {
        	completedTasks.add(new Task(key));
        	//for now, may change. 
       // 	tasks.remove(key);
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
//@@author

    
//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equals(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
