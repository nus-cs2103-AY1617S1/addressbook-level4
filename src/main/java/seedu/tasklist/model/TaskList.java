package seedu.tasklist.model;

import javafx.collections.ObservableList;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-list level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskList implements ReadOnlyTaskList{


	private final UniqueTaskList tasks;
	private final UniqueTagList tags;

	{
		tasks = new UniqueTaskList();
		tags = new UniqueTagList();
	}

	public TaskList() {

	}


	/**
	 * tasks and Tags are copied into this task list
	 */
	public TaskList(ReadOnlyTaskList toBeCopied) {
		this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
	}

	/**
	 * tasks and Tags are copied into this task list
	 */
	public TaskList(UniqueTaskList tasks, UniqueTagList tags) {
		resetData(tasks.getInternalList(), tags.getInternalList());
	}

	public static ReadOnlyTaskList getEmptyTaskList() {
		return new TaskList();
	}

	
	//// list overwrite operations

	public ObservableList<Task> getTasks() {
		return tasks.getInternalList();
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks.getInternalList().setAll(tasks);
	}

	public void setTags(Collection<Tag> tags) {
		this.tags.getInternalList().setAll(tags);
	}

	public void resetData(Collection<? extends ReadOnlyTask> newtasks, Collection<Tag> newTags) {
		setTasks(newtasks.stream().map(Task::new).collect(Collectors.toList()));
		setTags(newTags);
	}

	public void resetData(ReadOnlyTaskList newData) {
		resetData(newData.getTaskList(), newData.getTagList());
	}

	//// person-level operations

	/**
	 * Adds a task to the to-do list.
	 *
	 * @throws UniqueTaskList.DuplicateTaskException if an equivalent person already exists.
	 */
	public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
		syncTagsWithMasterList(p);
		tasks.add(p);
	}
	
	public boolean isOverlapping(Task p) {
		return tasks.isOverlapping(p);
	}

	/**
	 * Ensures that every tag in this person:
	 *  - exists in the master list {@link #tags}
	 *  - points to a Tag object in the master list
	 */
	private void syncTagsWithMasterList(Task task) {
		final UniqueTagList personTags = task.getTags();
		tags.mergeFrom(personTags);

		// Create map with values = tag object references in the master list
		final Map<Tag, Tag> masterTagObjects = new HashMap<>();
		for (Tag tag : tags) {
			masterTagObjects.put(tag, tag);
		}

		// Rebuild the list of person tags using references from the master list
		final Set<Tag> commonTagReferences = new HashSet<>();
		for (Tag tag : personTags) {
			commonTagReferences.add(masterTagObjects.get(tag));
		}
		task.setTags(new UniqueTagList(commonTagReferences));
	}

	public void removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
		tasks.remove(key);
	}

	public void markTaskAsComplete(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
		tasks.setComplete(key);
	}

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
	
	public List<Task> getListOfTasks() {
        return tasks.getInternalList();
    }

	@Override
	public List<Tag> getTagList() {
		return Collections.unmodifiableList(tags.getInternalList());
	}

	@Override
	public UniqueTaskList getUniqueTaskList() {
		return this.tasks;
	}


	public boolean isEmpty(){
		return tasks.isEmpty();
	}
	@Override
	public UniqueTagList getUniqueTagList() {
		return this.tags;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof TaskList // instanceof handles nulls
						&& this.tasks.equals(((TaskList) other).tasks)
						&& this.tags.equals(((TaskList) other).tags));
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing your own
		return Objects.hash(tasks, tags);
	}

    public void updateTask(Task taskToUpdate, TaskDetails taskDetails, String startTime, String endTime,
            Priority priority, String recurringFrequency) throws IllegalValueException {
        if (taskDetails != null) 
            taskToUpdate.setTaskDetails(taskDetails); 
        if (startTime != null) 
            taskToUpdate.getStartTime().updateTime(startTime); 
        if (endTime != null) 
            taskToUpdate.getEndTime().updateTime(endTime);
        if (priority != null)
            taskToUpdate.setPriority(priority);
        if (recurringFrequency != null)
            taskToUpdate.setRecurringFrequency(recurringFrequency);
    }
    
    public void updateTask(Task taskToUpdate, TaskDetails taskDetails, StartTime startTime, EndTime endTime,
            Priority priority, String recurringFrequency) throws IllegalValueException {
        if (taskDetails != null) 
            taskToUpdate.setTaskDetails(taskDetails); 
        if (startTime != null) 
            taskToUpdate.setStartTime(startTime); 
        if (endTime != null) 
            taskToUpdate.setEndTime(endTime);
        if (priority != null)
            taskToUpdate.setPriority(priority);
        if (recurringFrequency != null)
            taskToUpdate.setRecurringFrequency(recurringFrequency);
    }

    public void markTaskAsIncomplete(ReadOnlyTask task) {
        try {
            tasks.setIncomplete(task);
        } 
        catch (TaskNotFoundException e) {
            e.printStackTrace();
        }        
    }
}

