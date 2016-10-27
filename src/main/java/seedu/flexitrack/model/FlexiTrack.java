package seedu.flexitrack.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.tag.Tag;
import seedu.flexitrack.model.tag.UniqueTagList;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.IllegalEditException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Wraps all data at the task-tracker level Duplicates are not allowed (by
 * .equals comparison)
 */
public class FlexiTrack implements ReadOnlyFlexiTrack {

    private final UniqueTaskList task;
    private final UniqueTagList tags;
    private UniqueTaskList blockList = new UniqueTaskList();
    
    {
        task = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public FlexiTrack() {
    }

    /**
     * Tasks are copied into this taskstracker
     */
    public FlexiTrack(ReadOnlyFlexiTrack toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks are copied into this taskstracker
     */
    public FlexiTrack(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyFlexiTrack getEmptyFlexiTrack() {
        return new FlexiTrack();
    }

    //// list overwrite operations

    public ObservableList<Task> getTasks() {
        return task.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.task.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyFlexiTrack newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

    //// task-level operations

    /**
     * Adds a task to the tasks tracker. Also checks the new task's tags and
     * updates {@link #tags} with any new tags found, and updates the Tag
     * objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(Task p) throws DuplicateTaskException {
        syncTagsWithMasterList(p);
        task.add(p);
    }

    //@@author A0127855W
    /**
     * Edits a Task in the tasks tracker.
     * 
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     * @throws TaskNotFoundException if specified task is not found.
     */
    public Task editTask(int taskToEdit, String[] args)
            throws TaskNotFoundException, IllegalEditException, IllegalValueException {
        return task.edit(taskToEdit, args);
    }
  //@@author

    /**
     * Ensures that every tag in this task: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
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
        if (task.remove(key)) {
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
        return task.getInternalList().size() + " tasks, " + tags.getInternalList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(task.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.task;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlexiTrack // instanceof handles nulls
                        && this.task.equals(((FlexiTrack) other).task) && this.tags.equals(((FlexiTrack) other).tags));
    }
  //@@author A0127855W
    /**
     * Sorts the flexitrack according to the ReadOnlyTask comparator
     */
    public void sort(){
    	task.sort();
    }
  //@@author

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(task, tags);
    }
    
    //@@author A0138455Y
    public void markTask(ReadOnlyTask targetIndex) throws IllegalValueException {
        task.mark(targetIndex, Boolean.TRUE);
    }

    public void unmarkTask(ReadOnlyTask targetIndex) throws IllegalValueException {
        task.mark(targetIndex, Boolean.FALSE);
    }
    
    public boolean checkBlock(Task toCheck) throws DuplicateTaskException {
        setBlockList();

        if(blockList.getInternalList().size()==0) {
            //System.out.println("block list equal to 0");
            return false;
        }
        for(Task forCheck: blockList) {
            if(compareDate(toCheck,forCheck)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean compareDate(Task toCheck, Task blockDate) {
        Date start1 = toCheck.getStartTime().getTimeInfo().getTimingInfo().getDates().get(0);
        Date start2 = blockDate.getStartTime().getTimeInfo().getTimingInfo().getDates().get(0);
        Date end1 = toCheck.getEndTime().getTimeInfo().getTimingInfo().getDates().get(0);
        Date end2 = blockDate.getEndTime().getTimeInfo().getTimingInfo().getDates().get(0);

        if((start1.compareTo(start2)>=0 && start1.compareTo(end2)<=0) || 
                (end1.compareTo(start2)>=0 && end1.compareTo(end2)<=0)) {
            return true;
        }
        return false;
    }
    
    private void setBlockList() throws DuplicateTaskException {
        for(Task toAdd: task) {
            if(toAdd.getName().toString().contains("(Blocked) ")) {
                blockList.add(toAdd);
            }
        }
    }
  //@@author
}
