package seedu.whatnow.model;

import javafx.collections.ObservableList;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the WhatNow level
 * Duplicates are not allowed (by .equals comparison)
 */
public class WhatNow implements ReadOnlyWhatNow {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    private UniqueTaskList backUpTasks;
    private UniqueTagList backUpTags;
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public WhatNow() {}

    /**
     * Tasks and Tags are copied into this whatnow
     */
    public WhatNow(ReadOnlyWhatNow toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this whatnow
     */
    public WhatNow(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyWhatNow getEmptyWhatNow() {
        return new WhatNow();
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

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        
    	setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyWhatNow newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }
	public void revertEmptyWhatNow(ReadOnlyWhatNow backUp) {
		resetData(backUp.getTaskList(),backUp.getTagList());
	}

//// task-level operations

    /**
     * Adds a task to WhatNow.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
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

    /**
     * Remove a task from WhatNow.
     *
     * @throws UniqueTaskList.TaskNotFoundException if the task does not exist.
     */
    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean changeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        } 
    }
    /**
     * Updates a task on WhatNow.
     * 
     * @throws UniqueTaskList.TaskNotFoundException
     */
    public boolean updateTask(ReadOnlyTask old, Task toUpdate) throws TaskNotFoundException, DuplicateTaskException {
    	if (tasks.update(old, toUpdate)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    /**
     * Marks a task on WhatNow as completed.
     * 
     * @throws UniqueTaskList.TaskNotFoundException
     */
    public boolean markTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.mark(target)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

	public boolean unMarkTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
		if(tasks.unmark(target)) {
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
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
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
                || (other instanceof WhatNow // instanceof handles nulls
                && this.tasks.equals(((WhatNow) other).tasks)
                && this.tags.equals(((WhatNow) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
