package seedu.address.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

/**
 * Wraps all data at the task-list level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskMaster implements ReadOnlyTaskMaster {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskMaster() {}

    /**
     * Tasks and Tags are copied into this task list
     */
    public TaskMaster(ReadOnlyTaskMaster toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this task list
     */
    public TaskMaster(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalTaskList(), tasks.getInternalComponentList(), tags.getInternalList());
    }

    public static ReadOnlyTaskMaster getEmptyTaskList() {
        return new TaskMaster();
    }

//// list overwrite operations

    public List<Task> getTasks() {
        return tasks.getInternalTaskList();
    }

    @Override
    public ObservableList<TaskComponent> getTaskComponentList() {
        return tasks.getInternalComponentList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalTaskList().clear();
        this.tasks.getInternalTaskList().addAll(tasks);
    }
    
    public void setComponents(List<TaskComponent> components) {
    	this.tasks.getInternalComponentList().clear();
    	this.tasks.getInternalComponentList().addAll(components);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<? extends TaskComponent> newComponents, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setComponents(newComponents.stream().map(TaskComponent::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskMaster newData) {
        resetData(newData.getTaskList(), newData.getTaskComponentList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the task list.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     * @throws TimeslotOverlapException 
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException, TimeslotOverlapException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalTaskList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalTaskList());
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
                || (other instanceof TaskMaster // instanceof handles nulls
                && this.tasks.equals(((TaskMaster) other).tasks)
                && this.tags.equals(((TaskMaster) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }

    //@@author A0147967J
	public boolean archiveTask(TaskComponent target) throws TaskNotFoundException {
		// TODO Auto-generated method stub
		if (tasks.archive(target)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
	}
	//@@author
	
	public boolean updateTask(Task target, Name name, UniqueTagList tags,
    		TaskDate startDate, TaskDate endDate) throws TaskNotFoundException, TimeslotOverlapException {
		if (tasks.updateTask(target, name, tags, startDate, endDate)) {
			if(tags != null) {
				this.tags.mergeFrom(tags);

		        // Create map with values = tag object references in the master list
		        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
		        for (Tag tag : this.tags) {
		            masterTagObjects.put(tag, tag);
		        }

		        // Rebuild the list of task tags using references from the master list
		        final Set<Tag> commonTagReferences = new HashSet<>();
		        for (Tag tag : tags) {
		            commonTagReferences.add(masterTagObjects.get(tag));
		        }
		        target.setTags(new UniqueTagList(commonTagReferences));
			}

			return true;
		} else {
			throw new UniqueTaskList.TaskNotFoundException();
		}
	}
}
